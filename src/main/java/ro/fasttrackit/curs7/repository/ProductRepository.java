package ro.fasttrackit.curs7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.curs7.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, long id);

    Page<Product> findByNameIn(List<String> names, Pageable pageable);

    Product findByShopIdAndDescription(String shopId, String description);

}
