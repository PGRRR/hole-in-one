package nosleepcoders.holeinone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 회원 엔티티 객체
 */
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
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
    private Long level;

    @Builder
    public Member(Long member_id, String email, String password, String name, String phone, String address, Long level) {
        this.member_id = member_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.level = level;
    }
}
