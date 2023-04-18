package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HttpPeopleDto {
    private String name;
    private String craft;

    public HttpPeopleDto(String name, String craft) {
        this.name = name;
        this.craft = craft;
    }
}
