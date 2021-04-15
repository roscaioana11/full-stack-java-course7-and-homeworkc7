package ro.fasttrackit.curs7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.curs7.domain.Product;
import ro.fasttrackit.curs7.exception.ValidationException;
import ro.fasttrackit.curs7.repository.ProductRepository;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository repo;

    public void validateNewThrow(Product newProduct) {
        validate(newProduct, true)
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    public void validateReplaceThrow(long productId, Product newProduct) {
        exists(productId)
                .or(() -> validate(newProduct, false))
                .ifPresent(ex -> {
                    throw ex;
                });
    }


    private Optional<ValidationException> validate(Product product, boolean newEntity) {
        if (product.getName() == null) {
            return Optional.of(new ValidationException("Name cannot be null"));
        } else if (newEntity && repo.existsByName(product.getName())) {
            return Optional.of(new ValidationException("Name cannot be duplicate"));
        } else if (!newEntity && repo.existsByNameAndIdNot(product.getName(), product.getId())) {
            return Optional.of(new ValidationException("Name cannot be duplicate"));
        } else {
            return empty();
        }
    }

    private Optional<ValidationException> exists(long productId) {
        return repo.existsById(productId)
                ? empty()
                : Optional.of(new ValidationException("Product with id " + productId + " doesn't exist"));
    }

    public void validateExistsOrThrow(long productId) {
        exists(productId).ifPresent(ex -> {
            throw ex;
        });
    }
}
