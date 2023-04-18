package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "location")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    public Location(LocalDateTime date, String longitude, String latitude) {
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Location(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
