package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpLocationResponseDto {
    private String message;
    private String timestamp;
    private Iss_position iss_position;
}
