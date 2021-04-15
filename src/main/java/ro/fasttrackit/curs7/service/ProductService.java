package ro.fasttrackit.curs7.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs7.domain.Product;
import ro.fasttrackit.curs7.exception.ResourceNotFoundException;
import ro.fasttrackit.curs7.model.ProductFilters;
import ro.fasttrackit.curs7.repository.ProductRepository;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;
    private final ProductValidator validator;
    private final ObjectMapper mapper;

    public Page<Product> getAll(ProductFilters filters, Pageable pageable) {
        if (!isEmpty(filters.getName())) {
            return repo.findByNameIn(filters.getName(), pageable);
        } else {
            return repo.findAll(pageable);
        }
    }

    public Product addProduct(Product newProduct) {
        validator.validateNewThrow(newProduct);

        return repo.save(newProduct);
    }

    public Product replaceProduct(long productId, Product newProduct) {
        newProduct.setId(productId);
        validator.validateReplaceThrow(productId, newProduct);

        Product dbProduct = repo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find product with id " + productId));
        copyProduct(newProduct, dbProduct);
        return repo.save(dbProduct);
    }

    private void copyProduct(Product newProduct, Product dbProduct) {
        dbProduct.setName(newProduct.getName());
        dbProduct.setDescription(newProduct.getDescription());
    }

    @SneakyThrows
    public Product patchProduct(long productId, JsonPatch patch) {
        validator.validateExistsOrThrow(productId);
        Product dbProduct = repo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find product with id " + productId));

        JsonNode patchedProductJson = patch.apply(mapper.valueToTree(dbProduct));
        Product patchedProduct = mapper.treeToValue(patchedProductJson, Product.class);
        return replaceProduct(productId, patchedProduct);
    }
}
