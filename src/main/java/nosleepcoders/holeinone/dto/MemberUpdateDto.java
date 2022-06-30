package nosleepcoders.holeinone.dto;

import lombok.*;
import nosleepcoders.holeinone.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {

    private String name;

    private String phone;

    private String address;
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .build();
    }
}
