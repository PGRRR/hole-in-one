package nosleepcoders.holeinone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto {

    @Email
    private String email;

    private String name;

    private String level;

    private Long article_id;

    private String article_title;

    private String article_text;

    private String article_like;

    private String comment_text;
}
