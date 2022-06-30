package nosleepcoders.holeinone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nosleepcoders.holeinone.dto.MemberUpdateDto;

import javax.persistence.*;

/**
 * 회원 도메인 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "members")
public class Member {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String phone;

    private String address;

    @Column(nullable = false)
    private Long level = 0L;

    public void updateMemberInfo(MemberUpdateDto memberUpdateDto) {
        this.name = memberUpdateDto.getName();
        this.password = memberUpdateDto.getPassword();
        this.address = memberUpdateDto.getAddress();
        this.phone = memberUpdateDto.getPhone();
    }
}
