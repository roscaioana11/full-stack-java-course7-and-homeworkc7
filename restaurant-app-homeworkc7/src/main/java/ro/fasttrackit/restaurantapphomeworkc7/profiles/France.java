package ro.fasttrackit.restaurantapphomeworkc7.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Countries;

import java.util.List;

@Component
@Profile("France")
public class France implements Countries {
    @Override
    public List<String> readCity() {
        return List.of(
                "Paris",
                "Lyon",
                "Toulouse",
                "Lille",
                "Versailles",
                "Orleans",
                "Strasbourg"
        );
    }
}
