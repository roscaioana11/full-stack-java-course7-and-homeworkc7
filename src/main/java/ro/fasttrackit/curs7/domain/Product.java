package ro.fasttrackit.curs7.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String shopId;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
        this.shopId = UUID.randomUUID().toString();
    }
}
