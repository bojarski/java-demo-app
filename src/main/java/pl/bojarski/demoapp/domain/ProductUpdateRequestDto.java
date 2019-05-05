package pl.bojarski.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import java.util.List;

import static java.util.Objects.nonNull;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateRequestDto {

    private final String id;
    private final String name;
    private final PriceDto price;
    private final ImageDto imageDto;
    private final DescriptionDto descriptionDto;
    private final List<TagDto> tags;

    @JsonCreator
    public ProductUpdateRequestDto(@JsonProperty("id") String id,
                                   @JsonProperty("name") String name,
                                   @JsonProperty("price") PriceDto price,
                                   @JsonProperty("image") ImageDto imageDto,
                                   @JsonProperty("descriptionDto") DescriptionDto descriptionDto,
                                   @JsonProperty("tags") List<TagDto> tags) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.imageDto = imageDto;
        this.descriptionDto = descriptionDto;
        this.tags = tags;
    }

    @JsonIgnore
    public boolean isValid() {
        return !Strings.isNullOrEmpty(id)
                && !Strings.isNullOrEmpty(name)
                && nonNull(price) && price.isValid()
                && nonNull(descriptionDto) && descriptionDto.isValid()
                && nonNull(tags) && tags.size() > 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PriceDto getPrice() {
        return price;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }

    public DescriptionDto getDescriptionDto() {
        return descriptionDto;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", imageDto=" + imageDto +
                ", descriptionDto=" + descriptionDto +
                ", tags=" + tags +
                '}';
    }
}