package pl.bojarski.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import static java.util.Objects.nonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
class PriceDto {

    private final BigDecimal amount;
    private final Currency currency;

    @JsonCreator
    public PriceDto(@JsonProperty("amount") BigDecimal amount,
                    @JsonProperty("currency") Currency currency) {

        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @JsonIgnore
    public boolean isValid() {
        return nonNull(amount) && nonNull(currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDto price = (PriceDto) o;
        return Objects.equals(amount, price.amount) &&
                currency == price.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}