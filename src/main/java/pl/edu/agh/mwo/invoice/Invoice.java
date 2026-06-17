package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private static int nextNumber = 1;
    private final int number;
    private final Map<Product, Integer> products = new LinkedHashMap<Product, Integer>();

    public Invoice() {
        number = nextNumber++;
    }

    public int getNumber() {
        return number;
    }

    public String getPrintout() {
        StringBuilder printout = new StringBuilder();
        String separator = System.lineSeparator();

        printout.append("Numer faktury: ")
                .append(number)
                .append(separator);

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            printout.append(product.getName())
                    .append(", ")
                    .append(quantity)
                    .append(" szt., ")
                    .append(product.getPrice())
                    .append(separator);
        }

        printout.append("Liczba pozycji: ")
                .append(products.size());

        return printout.toString();
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        Integer currentQuantity = products.get(product);

        if (currentQuantity == null) {
            products.put(product, quantity);
        } else {
            products.put(product, currentQuantity + quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }
}
