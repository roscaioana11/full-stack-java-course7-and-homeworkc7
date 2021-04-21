package ro.fasttrackit.restaurantapphomeworkc7.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ro.fasttrackit.restaurantapphomeworkc7.domain.Countries;

import java.util.List;

@Component
@Profile("Romania")
public class Romania implements Countries {
    @Override
    public List<String> readCity() {
        return List.of(
                "Cluj-Napoca",
                "Bucharest",
                "Brasov",
                "Sibiu",
                "Fagaras",
                "Timisoara",
                "Arad"
        );
    }
}
