package ro.fasttrackit.restaurantapphomeworkc7.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Restaurant;
import ro.fasttrackit.restaurantapphomeworkc7.exception.ResourceNotFoundException;
import ro.fasttrackit.restaurantapphomeworkc7.model.RestaurantFilters;
import ro.fasttrackit.restaurantapphomeworkc7.repository.RestaurantRepository;

import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantValidator validator;
    private final ObjectMapper mapper;

    public Page<Restaurant> getAll(RestaurantFilters filters, Pageable pageable){
        if(!isEmpty(filters.getStars()) || hasText(filters.getCity())){
            return repository.findByStarsInAndCity(filters.getStars(), filters.getCity(), pageable);
        }else {
            return repository.findAll(pageable);
        }
    }

    public Optional<Restaurant> findById(long restaurantId){
        return repository.findById(restaurantId);
    }

    public Restaurant addRestaurant(Restaurant newRestaurant){
        validator.validateNewThrow(newRestaurant);
        return repository.save(newRestaurant);
    }

    public Restaurant replaceRestaurant(long restaurantId, Restaurant newRestaurant){
        newRestaurant.setId(restaurantId);
        validator.validateReplaceThrow(restaurantId, newRestaurant);

        Restaurant dbRestaurant = repository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find restaurant with id " + restaurantId));
        copyRestaurant(newRestaurant, dbRestaurant);
        return repository.save(dbRestaurant);
    }

    private void copyRestaurant(Restaurant newProduct, Restaurant dbProduct) {
        dbProduct.setName(newProduct.getName());
        dbProduct.setStars(newProduct.getStars());
        dbProduct.setCity(newProduct.getCity());
        dbProduct.setSince(newProduct.getSince());
    }

    @SneakyThrows
    public Restaurant patchRestaurant(long restaurantId, JsonPatch patch){
        validator.validateExistsOrThrow(restaurantId);
        Restaurant dbRestaurant = repository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find restaurant with id " + restaurantId));

        JsonNode patchedRestaurantJson = patch.apply(mapper.valueToTree(dbRestaurant));
        Restaurant patchedRestaurant = mapper.treeToValue(patchedRestaurantJson, Restaurant.class);
        return replaceRestaurant(restaurantId, patchedRestaurant);
    }

    public void deleteRestaurant(Long restaurantId){
        validator.validateExistsOrThrow(restaurantId);
        repository.deleteById(restaurantId);
    }
}
