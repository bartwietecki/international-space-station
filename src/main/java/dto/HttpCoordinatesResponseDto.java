package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpCoordinatesResponseDto {

    private String latitude;
    private String longitude;
    private String timezone_id;
    private double offset;
    private String country_code;
    private String map_url;

}
