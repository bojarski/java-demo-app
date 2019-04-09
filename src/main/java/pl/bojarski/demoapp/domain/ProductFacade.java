package pl.bojarski.demoapp.domain;

public interface ProductFacade {

    ProductResponseDto create(ProductRequestDto productRequest);

    ProductResponseDto findById(String id);

    ProductResponseDto update(ProductUpdateRequestDto productUpdateRequestDto);

    void delete(String id);
}
