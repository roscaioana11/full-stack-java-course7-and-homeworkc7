package ro.fasttrackit.restaurantapphomeworkc7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Page<Restaurant> findByStarsInAndCity(List<Integer> stars, String city, Pageable pageable);
    Page<Restaurant> findAll(Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, long id);
}
