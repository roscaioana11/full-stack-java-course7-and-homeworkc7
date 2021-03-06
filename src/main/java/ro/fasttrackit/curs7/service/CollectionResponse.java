package ro.fasttrackit.curs7.service;

import lombok.Builder;
import lombok.Value;
import ro.fasttrackit.curs7.service.PageInfo;

import java.util.List;

@Value
@Builder
public class CollectionResponse<T> {
    List<T> content;
    PageInfo pageInfo;
}

