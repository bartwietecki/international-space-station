package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpSatteliteResponseDto {
    private String name;
    private String id;
    private String latitude;
    private String longitude;
    private double altitude;
    private double velocity;
    private String visibility;
    private double footprint;
    private double timestamp;
    private double daynum;
    private double solar_lat;
    private double solar_lon;
    private String units;
}
