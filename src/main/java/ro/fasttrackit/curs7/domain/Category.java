package ro.fasttrackit.curs7.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String category;

    public Category(String category) {
        this.category = category;
    }
}
