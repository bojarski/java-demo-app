package pl.bojarski.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateRequestDto {

    private final String id;
    private final String name;

    @JsonCreator
    public ProductUpdateRequestDto(@JsonProperty("id") String id,
                                   @JsonProperty("name") String name) {

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return !Strings.isNullOrEmpty(id) && !Strings.isNullOrEmpty(name);
    }

    @Override
    public String toString() {
        return "ProductUpdateRequestDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}


