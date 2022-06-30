package nosleepcoders.holeinone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {
    private String password;

    private String name;

    private String phone;

    private String address;
}
