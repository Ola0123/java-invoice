package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        products.put(product, quantity);
    }

    public BigDecimal getSubtotal() {

        BigDecimal subtotal = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            subtotal = subtotal.add(
                    product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        return subtotal;
    }

    public BigDecimal getTax() {
        BigDecimal tax = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            BigDecimal productTax =
                    product.getPrice()
                            .multiply(product.getTaxPercent())
                            .multiply(BigDecimal.valueOf(quantity));
            tax = tax.add(productTax);
        }
        return tax;
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getTax());
    }
}
