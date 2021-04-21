package ro.fasttrackit.restaurantapphomeworkc7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Countries;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Restaurant;
import ro.fasttrackit.restaurantapphomeworkc7.exception.ValidationException;
import ro.fasttrackit.restaurantapphomeworkc7.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {
    private final RestaurantRepository repository;
    private final Countries countries;

    public void validateNewThrow(Restaurant newRestaurant) {
        validate(newRestaurant, true)
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    public void validateReplaceThrow(long productId, Restaurant newRestaurant) {
        exists(productId)
                .or(() -> validate(newRestaurant, false))
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    private Optional<ValidationException> validate(Restaurant restaurant, boolean newEntity) {
        List<String> errors = new ArrayList<>();
        if (restaurant.getName() == null) {
            errors.add("Name cannot be null");
        }
        if (newEntity && repository.existsByName(restaurant.getName())) {
            errors.add("Name cannot be duplicate");
        }
        if (!newEntity && repository.existsByNameAndIdNot(restaurant.getName(), restaurant.getId())) {
            errors.add("Name cannot be duplicate");
        }
        if(!countries.readCity().contains(restaurant.getCity())){
            errors.add("Restaurant not found in the provided city");
        }
        if(LocalDate.now().isBefore(restaurant.getSince())) {
            errors.add("Open since cannot be in the future");
        }

        return errors.isEmpty() ? empty() : Optional.of(new ValidationException(errors));
    }

    private Optional<ValidationException> exists(Long restaurantId) {
        return repository.existsById(restaurantId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Restaurant with id " + restaurantId + " doesn't exist")));
    }

    public void validateExistsOrThrow(Long restaurantId) {
        exists(restaurantId).ifPresent(ex -> {
            throw ex;
        });
    }
}
