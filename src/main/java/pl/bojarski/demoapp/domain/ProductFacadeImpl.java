package pl.bojarski.demoapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bojarski.demoapp.infrastucture.ProductRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    @Autowired
    public ProductFacadeImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequest) {

        if (!productRequest.isValid()) {
            throw new RuntimeException("Product name cannot be empty!");
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product(id, productRequest.getName(), createdAt);

        productRepository.save(product);

        ProductResponseDto productResponse = new ProductResponseDto(
                product.getId(),
                product.getName()
        );

        return productResponse;
    }
}
