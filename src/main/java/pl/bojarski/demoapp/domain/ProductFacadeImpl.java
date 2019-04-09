package pl.bojarski.demoapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bojarski.demoapp.infrastucture.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
class ProductFacadeImpl implements ProductFacade {

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

    @Override
    public ProductsResponseDto findAll() {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDto> productsReponse = products.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());

        return new ProductsResponseDto(productsReponse);
    }

    @Override
    public ProductResponseDto findById(String id) {
        Product product = findProductById(id);

        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductResponseDto update(ProductUpdateRequestDto productUpdateRequestDto) {

        if (!productUpdateRequestDto.isValid()) {
            throw new RuntimeException("Product id or name cannot be empty!");
        }

        Product product = findProductById(productUpdateRequestDto.getId());
        Product updatedProduct = product.name(productUpdateRequestDto.getName());

        productRepository.save(updatedProduct);

        return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName());
    }

    @Override
    public void delete(String id) {
        productRepository.remove(id);
    }

    private Product findProductById(String id) {
        Product product = productRepository.findById(id);

        if (isNull(product)) {
            throw new ProductValidationException("Product does not exist where id = " + id);
        }

        return product;
    }
}
