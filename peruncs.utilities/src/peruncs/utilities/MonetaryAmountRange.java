package peruncs.utilities;

import java.util.Currency;
import java.util.Objects;

public record MonetaryAmountRange(Currency currency, long low, long high)
        implements Range<Long>, FormattedMonetaryAmount{

    public MonetaryAmountRange{
        if(low>=high)
            throw new IllegalArgumentException(STR."Invalid values for low:\{low} and high:\{high}");
    }

    @Override
    public  MonetaryAmountRange of(Long start, Long end) {
        Objects.requireNonNull(start,"start");
        Objects.requireNonNull(end,"end");
        return new MonetaryAmountRange(currency,start,end);
    }

    @Override
    public Long start(){
        return low;
    }

    @Override
    public Long end(){
        return high;
    }

    @Override
    public int compare(Long o1, Long o2) {
        return Long.compare(o1,o2);
    }


    public MonetaryAmountRange convertTo(Currency targetCurrency, ECBExchangeRates ecbExchangeRates) {
        return new MonetaryAmountRange(targetCurrency,
                Math.round(ecbExchangeRates.convertTo(targetCurrency, currency, low)),
                Math.round(ecbExchangeRates.convertTo(targetCurrency, currency, high)));
    }

    @Override
    public String formatted() {
        return STR."\{currency}: \{formattedDecimal()}";
    }

    @Override
    public String formattedDecimal() {
        return STR."\{MonetaryAmount.formatted(low)} - \{MonetaryAmount.formatted(high)}";
    }
}
