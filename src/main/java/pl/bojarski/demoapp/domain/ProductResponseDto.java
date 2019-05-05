package pl.bojarski.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ProductResponseDto {

    private final String id;
    private final String name;
    private final PriceDto price;
    private final ImageDto image;
    private final DescriptionDto description;
    private final List<TagDto> tags;

    @JsonCreator
    public ProductResponseDto(@JsonProperty("id") String id,
                              @JsonProperty("name") String name,
                              @JsonProperty("price") PriceDto price,
                              @JsonProperty("image") ImageDto image,
                              @JsonProperty("description") DescriptionDto description,
                              @JsonProperty("tags") List<TagDto> tags) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.tags = tags;
    }

    public ProductResponseDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.image = builder.image;
        this.description = builder.description;
        this.tags = builder.tags;
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

    public ImageDto getImage() {
        return image;
    }

    public DescriptionDto getDescription() {
        return description;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponseDto that = (ProductResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(image, that.image) &&
                Objects.equals(description, that.description) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image, description, tags);
    }

    @Override
    public String toString() {
        return "ProductResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + image +
                ", description=" + description +
                ", tags=" + tags +
                '}';
    }

    public static final class Builder {

        private String id;
        private String name;
        private PriceDto price;
        private ImageDto image;
        private DescriptionDto description;
        private List<TagDto> tags;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPrice(Price price) {
            if (nonNull(price)) {
                this.price = new PriceDto(price.getAmount(), price.getCurrency());
            }
            return this;
        }

        public Builder withImage(Image image) {
            if (nonNull(image)) {
                this.image = new ImageDto(image.getUrl());
            }
            return this;
        }

        public Builder withDescription(Description description) {
            if (nonNull(description)) {
                this.description = new DescriptionDto(description.getText());
            }
            return this;
        }

        public Builder withTags(List<Tag> tags) {
            if (nonNull(tags)) {
                this.tags = tags.stream()
                        .map(Tag::getName)
                        .map(TagDto::new)
                        .collect(Collectors.toList());
            }
            return this;
        }

        public ProductResponseDto build() {
            return new ProductResponseDto(this);
        }
    }
}
