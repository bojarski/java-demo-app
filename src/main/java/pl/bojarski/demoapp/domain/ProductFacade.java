package pl.bojarski.demoapp.domain;

public interface ProductFacade {

    ProductResponseDto create(ProductRequestDto productRequest);

    ProductResponseDto findById(String id);

    ProductResponseDto update(String id, ProductRequestDto productRequest);

    ProductResponseDto delete(String id);
}
