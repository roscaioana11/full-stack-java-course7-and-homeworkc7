package ro.fasttrackit.curs7.model;

import lombok.Value;

import java.util.List;

@Value
public class ProductFilters {
    List<String> name;
    List<String> description;
    List<String> shopId;
    List<String> id;
}
