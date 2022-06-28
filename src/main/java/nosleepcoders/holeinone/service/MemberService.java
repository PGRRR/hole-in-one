package nosleepcoders.holeinone.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        memberRepository.findByEmail(member.getEmail()) // 중복 회원 검증
                .ifPresent(m -> {
                    throw new IllegalStateException("중복 회원입니다.");
                });
        memberRepository.save(member);
        return member.getMember_id();
    }

    /**
     * email 존재 여부 확인
     */
    public void emailCheck(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) { // email 확인 검증
            System.out.println("EMAIL FAIL");
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }
        System.out.println("EMAIL PASS");
    }

    /**
     * 로그인
     */
    public Optional<Member> login(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) { // email 확인 검증
            System.out.println("EMAIL FAIL");
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }
        if (!password.equals(member.get().getPassword())) { // 비밀번호 검증
            System.out.println("PASSWORD FAIL");
            throw new NullPointerException("비밀번호가 틀렸습니다.");
        }
        return member;
    }

    /**
     * 개인 정보 접근 검증
     */
    public Optional<Member> access(Long id, HttpSession session) {
        Object sessionAttribute = session.getAttribute("member");
        if (sessionAttribute == null) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        Member sessionMember = (Member) sessionAttribute;
        if (!id.equals(sessionMember.getMember_id())) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        return memberRepository.findByEmail(sessionMember.getEmail());
    }

    /**
     * 개인 정보 수정
     */
    public void edit(Member updateMember){
        Optional<Member> member = memberRepository.findByEmail(updateMember.getEmail());
        memberRepository.update(updateMember);
        System.out.println("UPDATE MEMBER");
    }

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

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
