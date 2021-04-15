package ro.fasttrackit.curs7.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs7.domain.Product;
import ro.fasttrackit.curs7.model.ProductFilters;
import ro.fasttrackit.curs7.service.CollectionResponse;
import ro.fasttrackit.curs7.service.PageInfo;
import ro.fasttrackit.curs7.service.ProductService;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    CollectionResponse<Product> getAll(
            ProductFilters filters,
            Pageable pageable) {
        System.out.println(filters);
        Page<Product> productPage = service.getAll(filters, pageable);
        return CollectionResponse.<Product>builder()
                .content(productPage.getContent())
                .pageInfo(PageInfo.builder()
                        .totalPages(productPage.getTotalPages())
                        .totalElements(productPage.getNumberOfElements())
                        .crtPage(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .build())
                .build();
    }

    @PostMapping
    Product addProduct(@RequestBody Product newProduct) {
        return service.addProduct(newProduct);
    }

    @PutMapping("/{productId}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable long productId) {
        return service.replaceProduct(productId, newProduct);
    }

    @PatchMapping("{productId}")
    Product patchProduct(@RequestBody JsonPatch patch, @PathVariable long productId) {
        return service.patchProduct(productId, patch);
    }
}
