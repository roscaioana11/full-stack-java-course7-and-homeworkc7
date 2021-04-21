package ro.fasttrackit.restaurantapphomeworkc7.model;

import lombok.Value;
import java.util.List;

@Value
public class RestaurantFilters {
    List<Integer> stars;
    String city;
}
