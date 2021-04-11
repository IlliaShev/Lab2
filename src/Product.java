/**
 * Class product with all its properties
 */
public class Product {
    private String name;
    private String description;
    private String producer;
    private int value;
    private double price;

    /**
     * Create product with all parameters
     * @param name - product name
     * @param description - product description
     * @param producer - product producer
     * @param value - product value
     * @param price - product price
     */
    public Product(String name, String description, String producer, int value, double price) {
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.value = value;
        this.price = price;
    }

    /**
     * Create product with all parameters without value(default 1)
     * @param name - product name
     * @param description - product description
     * @param producer - product producer
     * @param price - product price
     */
    public Product(String name, String description, String producer, double price) {
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.value = 1;
        this.price = price;
    }
}

