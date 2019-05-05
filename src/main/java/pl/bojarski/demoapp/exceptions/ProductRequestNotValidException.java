package pl.bojarski.demoapp.exceptions;

import pl.bojarski.demoapp.domain.ProductRequestDto;
import pl.bojarski.demoapp.domain.ProductUpdateRequestDto;

public class ProductRequestNotValidException extends RuntimeException {
    public ProductRequestNotValidException(ProductRequestDto productRequest) {
        super("Product request is not valid: " + productRequest);
    }

    public ProductRequestNotValidException(ProductUpdateRequestDto productUpdateRequest) {
        super("Product update request is not valid: " + productUpdateRequest);
    }
}
