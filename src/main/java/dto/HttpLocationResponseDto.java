package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpLocationResponseDto {
    String message;
    String timestamp;
    Iss_position iss_position;
}
