package service;

import dto.SimpleLocationDto;
import entity.Location;
import repository.LocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addLocation(LocalDateTime date, String longitude, String latitude) throws IllegalArgumentException {
        if (date != null) {
            Location location = new Location(date, longitude, latitude);

            locationRepository.insert(location);
        } else {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }

    public List<SimpleLocationDto> findAll() {
        Set<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(location -> new SimpleLocationDto(location.getId(),
                        location.getDate() + ", " + location.getLongitude() + ", " + location.getLatitude()))
                .toList();
    }

}
