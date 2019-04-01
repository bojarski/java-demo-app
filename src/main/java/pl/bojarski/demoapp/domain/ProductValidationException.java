package pl.bojarski.demoapp.domain;

public class ProductValidationException extends RuntimeException {

    public ProductValidationException(String message) {
        super(message);
    }
}
