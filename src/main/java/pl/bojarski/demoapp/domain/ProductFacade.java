package pl.bojarski.demoapp.domain;

public interface ProductFacade {

    ProductResponseDto create(ProductRequestDto productRequest);

    ProductResponseDto findById(String id);

    ProductsResponseDto findAll();

    ProductResponseDto update(ProductUpdateRequestDto productUpdateRequestDto);

    void delete(String id);
}
