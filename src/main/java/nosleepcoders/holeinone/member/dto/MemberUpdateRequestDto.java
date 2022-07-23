package nosleepcoders.holeinone.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    private String name;

    private String phone;

    private String address;

    @Builder
    public MemberUpdateRequestDto(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
