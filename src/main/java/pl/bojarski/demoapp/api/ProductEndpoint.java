package pl.bojarski.demoapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bojarski.demoapp.domain.ProductFacade;
import pl.bojarski.demoapp.domain.ProductRequestDto;
import pl.bojarski.demoapp.domain.ProductResponseDto;

@RestController
@RequestMapping("/products")
class ProductEndpoint {

    private final ProductFacade productFacade;

    @Autowired
    public ProductEndpoint(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping
    ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productFacade.create(productRequestDto);
    }
}
