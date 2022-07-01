package nosleepcoders.holeinone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nosleepcoders.holeinone.domain.Member;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String email;
    private String password;
    private String phone;
    private String address;

    @Builder
    public MemberSaveRequestDto(String email, String password, String phone, String address) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    // request DTO 로 받은 Member 객체를 Entity 화하여 저장하는 용도
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .phone(phone)
                .address(address)
                .build();
    }
}

