package com.game.microbook.catalog.domain;

import com.game.microbook.catalog.CatalogApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CatalogApplicationProperties catalogApplicationProperties;

    public PagedResult<Product> getProducts(int pageNo) {
        int page = pageNo > 0 ? pageNo - 1 : 0;

        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        Pageable pageable = PageRequest.of(page, catalogApplicationProperties.pageSize(), sort);

        Page<Product> entitiesPage = productRepository.findAll(pageable).map(ProductMapper::toProduct);

        return PagedResult.of(entitiesPage);
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
