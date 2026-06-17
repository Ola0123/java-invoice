package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class FuelCanister extends TaxFreeProduct {

    private static final BigDecimal EXCISE_DUTY =
            new BigDecimal("5.56");

    public FuelCanister(String name, BigDecimal price) {
        super(name, price);
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(EXCISE_DUTY);
    }
}