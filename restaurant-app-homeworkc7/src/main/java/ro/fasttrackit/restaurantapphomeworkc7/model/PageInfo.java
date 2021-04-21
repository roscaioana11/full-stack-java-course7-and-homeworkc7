package ro.fasttrackit.restaurantapphomeworkc7.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PageInfo {
    int totalPages;
    int totalElements;
    int cntPage;
    int pageSize;
}
