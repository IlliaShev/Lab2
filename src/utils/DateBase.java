package utils;

import dto.GroupOfProducts;
import dto.HandlerGroupOfProducts;
import dto.Product;

/**
 * Convert csv to java class
 */
public class DateBase {
    /**
     * Create object Product
     */
    private final Product[] products = new Product[20];
    /**
     * Create object GroupOfProducts
     */
    private final GroupOfProducts[] group = new GroupOfProducts[4];
    /**
     * Create object HandlerGroupOfProducts
     */
    private final HandlerGroupOfProducts groups = new HandlerGroupOfProducts();

    {
        group[0] = new GroupOfProducts("dairy", "-");
        group[1] = new GroupOfProducts("butcher`s", "-");
        group[2] = new GroupOfProducts("greengrocery", "-");
        group[3] = new GroupOfProducts("bakery", "-");

        products[0] = new Product("Milk", "Nestle", "Low fat milk", 2.99);
        products[1] = new Product("Yogurt", "Producers", "Low fat yogurt with blended strawberry", 4.99);
        products[2] = new Product("Butter", "Country Life", "Salted butter", 3.49);
        products[3] = new Product("Cheese", "Komo", "Gauda", 7.69);
        products[4] = new Product("Ice Cream", "ASDA", "vanilla", 1.99);
        group[0].addProduct(products[0], 10);
        group[0].addProduct(products[1], 10);
        group[0].addProduct(products[2], 10);
        group[0].addProduct(products[3], 10);
        group[0].addProduct(products[4], 10);

        products[5] = new Product("Sausages", "Richmond", "8 thick pork sausages", 9.99);
        products[6] = new Product("Meat balls", "FarmRich", "737g ~52 balls", 8.29);
        products[7] = new Product("Gourmet ham", "HALL`S", "-", 19.99);
        products[8] = new Product("Fillet steak", "Morrisons", "-", 13.55);
        products[9] = new Product("Blood sausages", "Booths", "-", 12.99);
        group[1].addProduct(products[5], 10);
        group[1].addProduct(products[6], 10);
        group[1].addProduct(products[7], 10);
        group[1].addProduct(products[8], 10);
        group[1].addProduct(products[9], 10);


        products[10] = new Product("Apples", "Braeburn", "red apples", 3.99);
        products[11] = new Product("Avocados", "Hass", "green avocados", 2.99);
        products[12] = new Product("Bananas", "Organic", "yellow bananas", 3.95);
        products[13] = new Product("Beetroot", "Land", "-", 4.99);
        products[14] = new Product("Carrots", "Loose", "-", 1.95);
        group[2].addProduct(products[10], 10);
        group[2].addProduct(products[11], 10);
        group[2].addProduct(products[12], 10);
        group[2].addProduct(products[13], 10);
        group[2].addProduct(products[14], 10);

        products[15] = new Product("Cookies", "Maryland", "-", 4.25);
        products[16] = new Product("Bars", "Twix", "-", 1.44);
        products[17] = new Product("Muffins", "Bunic", "-", 1.98);
        products[18] = new Product("Pizza", "Di Goirno", "-", 8.99);
        products[19] = new Product("Cakes", "Tunnoks`s", "-", 13.95);
        group[3].addProduct(products[15], 10);
        group[3].addProduct(products[16], 10);
        group[3].addProduct(products[17], 10);
        group[3].addProduct(products[18], 10);
        group[3].addProduct(products[19], 10);

        groups.addGroup(group[0]);
        groups.addGroup(group[1]);
        groups.addGroup(group[2]);
        groups.addGroup(group[3]);

    }

    /**
     * Getter HandlerGroupOfProducts
     *
     * @return handler of product
     */
    public HandlerGroupOfProducts getGroups() {
        return groups;
    }

}

