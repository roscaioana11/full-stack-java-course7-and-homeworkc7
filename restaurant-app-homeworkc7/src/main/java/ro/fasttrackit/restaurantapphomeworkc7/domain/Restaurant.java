package ro.fasttrackit.restaurantapphomeworkc7.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int stars;
    private String city;
    private LocalDate since; //since is the date the restaurant opened. You should not be able to change it.

    public Restaurant(String name, int stars, String city, LocalDate since) {
        this.name = name;
        this.stars = stars;
        this.city = city;
        this.since = since;
    }
}
