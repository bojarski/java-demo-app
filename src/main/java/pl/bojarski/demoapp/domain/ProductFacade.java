package pl.bojarski.demoapp.domain;

public interface ProductFacade {

    ProductResponseDto findById(String id);

    ProductResponseDto create(ProductRequestDto productRequest);
    // update
    // delete
}
