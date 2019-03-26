package pl.bojarski.demoapp.domain;

public interface ProductFacade {

    // get
    ProductResponseDto findById(String id);
    // create

    ProductResponseDto create(ProductRequestDto productRequest);
    // update
    // delete
}
