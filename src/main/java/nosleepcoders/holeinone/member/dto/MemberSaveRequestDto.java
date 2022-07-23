package nosleepcoders.holeinone.member.dto;

import lombok.*;
import nosleepcoders.holeinone.member.domain.Member;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    @Builder
    public MemberSaveRequestDto(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // request DTO 로 받은 Member 객체를 Entity 화하여 저장하는 용도
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(address)
                .build();
    }
}

