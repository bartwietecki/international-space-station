package dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HttpAstronautsResponseDto {

    private String message;
    private int number;
    private HttpPeopleDto[] people;

    public HttpAstronautsResponseDto(String message, int number, HttpPeopleDto[] people) {
        this.message = message;
        this.number = number;
        this.people = people;
    }
}
