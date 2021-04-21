package ro.fasttrackit.restaurantapphomeworkc7.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Restaurant;
import ro.fasttrackit.restaurantapphomeworkc7.exception.ResourceNotFoundException;
import ro.fasttrackit.restaurantapphomeworkc7.model.RestaurantFilters;
import ro.fasttrackit.restaurantapphomeworkc7.model.CollectionResponse;
import ro.fasttrackit.restaurantapphomeworkc7.model.PageInfo;
import ro.fasttrackit.restaurantapphomeworkc7.service.RestaurantService;

@RestController
@RequestMapping("restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping
    CollectionResponse<Restaurant> getAll(
            RestaurantFilters filters,
            Pageable pageable){
        System.out.println(filters);
        Page<Restaurant> restaurantPage = service.getAll(filters, pageable);
        return CollectionResponse.<Restaurant>builder()
                .content(restaurantPage.getContent())
                .pageInfo(PageInfo.builder()
                        .totalPages(restaurantPage.getTotalPages())
                        .totalElements(restaurantPage.getNumberOfElements())
                        .cntPage(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .build())
                .build();
    }

    @GetMapping("/{restaurantId}")
    Restaurant findById(@PathVariable long restaurantId){
        return service.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find product with id " + restaurantId));
    }

    @PostMapping
    Restaurant addRestaurant(@RequestBody Restaurant newRestaurant){
        return service.addRestaurant(newRestaurant);
    }

    @PutMapping("/{restaurantId}")
    Restaurant replaceRestaurant(@RequestBody Restaurant newRestaurant,
                                 @PathVariable long restaurantId){
        return service.replaceRestaurant(restaurantId,newRestaurant);
    }

    @PatchMapping(path = "/{restaurantId}")
    Restaurant patchRestaurant(@RequestBody JsonPatch patch,
                               @PathVariable ("restaurantId") long restaurantId){
        return service.patchRestaurant(restaurantId,patch);
    }

    @DeleteMapping("/{restaurantId}")
    void deleteRestaurant(@PathVariable ("restaurantId")long restaurantId){
        service.deleteRestaurant(restaurantId);
    }
}
