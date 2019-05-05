package pl.bojarski.demoapp.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public final class Product {

    private final String id;
    private final String name;
    private final Price price;
    private final Image image;
    private final Description description;
    private final List<Tag> tags;
    private final LocalDateTime createdAt;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.image = builder.image;
        this.description = builder.description;
        this.tags = builder.tags;
        this.createdAt = builder.createdAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public Description getDescription() {
        return description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static final class Builder {

        private String id;
        private String name;
        private Price price;
        private Image image;
        private Description description;
        private List<Tag> tags;
        private LocalDateTime createdAt;

        public Builder(String id,
                       String name,
                       PriceDto price,
                       DescriptionDto descriptionDto,
                       LocalDateTime createdAt) {

            this.id = id;
            this.name = name;
            this.price = new Price(price.getAmount(), price.getCurrency());
            this.description = new Description(descriptionDto.getText());
            this.createdAt = createdAt;
        }

        public Builder withImage(ImageDto image) {
            if (nonNull(image)) {
                this.image = new Image(image.getUrl());
            }
            return this;
        }

        public Builder withTags(List<TagDto> tags) {
            if (nonNull(tags)) {
                this.tags = tags.stream()
                        .map(TagDto::getName)
                        .map(Tag::new)
                        .collect(Collectors.toList());
            }
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
