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

    // repository 를 통해 조회한 Entity 를 DTO 로 변환 용도
    @Builder
    public MemberUpdateDto(Member member) {
        this.name = member.getName();
        this.phone = member.getPhone();
        this.address = member.getAddress();
    }
    // DTO 로 받은 Member 객체를 Entity 화하여 저장하는 용도
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .build();
    }
}
