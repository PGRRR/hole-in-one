package nosleepcoders.holeinone.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.dto.MemberResponseDto;
import nosleepcoders.holeinone.dto.MemberSaveRequestDto;
import nosleepcoders.holeinone.dto.MemberUpdateRequestDto;
import nosleepcoders.holeinone.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 회원 서비스 개발
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(MemberSaveRequestDto requestDto) {
        memberRepository.findByEmail(requestDto.getEmail())
                .ifPresent((m) -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다. email=" + requestDto.getEmail());
                });
        System.out.println("SAVE MEMBER");
        return memberRepository.save(requestDto.toEntity()).getMember_id();
    }

    /**
     * email 존재 여부 확인
     */
    @Transactional
    public Member emailCheck(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    /**
     * 로그인
     */
    @Transactional
    public MemberResponseDto login(String email, String password) {
        Member member = emailCheck(email);
        MemberResponseDto responseDto = new MemberResponseDto(member);
        if (!password.equals(member.getPassword())) {
            System.out.println("PASSWORD FAILED");
            throw new IllegalStateException("틀린 비밀번호입니다.");
        }
        return responseDto;
    }

    /**
     * 개인 정보 접근 검증
     */
    @Transactional
    public MemberResponseDto access(Long id, HttpSession session) {
        Object sessionAttribute = session.getAttribute("members");
        if (sessionAttribute == null) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        MemberResponseDto sessionMember = (MemberResponseDto) sessionAttribute;
        if (!id.equals(sessionMember.getMember_id())) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        Member member = memberRepository.findByEmail(sessionMember.getEmail())
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return new MemberResponseDto(member);
    }

    /**
     * 개인 정보 수정
     */
    @Transactional
    public Member edit(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다. id=" + id));
        member.update(requestDto.getName(), requestDto.getPhone(), requestDto.getAddress());
        System.out.println("UPDATE MEMBER");
        return member;
    }

    @Transactional
    public String getAccessToken(String authorize_code) {
        String access_token = "";
        String refresh_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=5859443def9b6affce697eeb663074dc");
            sb.append("&redirect_uri=http://localhost:3080/members/kakao");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON 파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_token);
            System.out.println("refresh_token : " + refresh_token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    @Transactional
    public HashMap<String, Object> getUserInfo(String access_Token) {

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap 타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header 에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            long id = element.getAsJsonObject().get("id").getAsLong();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String age = kakao_account.getAsJsonObject().get("age_range").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("age", age);
            userInfo.put("id", id);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
    @Transactional
    public void withdraw(MemberSaveRequestDto requestDto){memberRepository.delete(requestDto.toEntity());}
    @Transactional
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
