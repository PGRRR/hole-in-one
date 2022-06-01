package nosleepcoders.holeinone.domain;

import javax.persistence.*;

@Table(name = "member")
@Entity
public class Member {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String address;
    private String phoneNumber;
    @Column(nullable = false)
    private String name;
    @Column(name="level")
    private String level = "0";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void update(Member updateMember) {
        this.address = updateMember.address;
        this.phoneNumber = updateMember.phoneNumber;
    }
}
