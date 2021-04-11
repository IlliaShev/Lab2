package dto;

/**
 * Class product with all its properties
 */
public class Product {
    private final String group;
    private final String name;
    private final String description;
    private final String producer;
    private final int value;
    private final double price;

    /**
     * Create product with all parameters
     * @param group - product group
     * @param name - product name
     * @param description - product description
     * @param producer - product producer
     * @param value - product value
     * @param price - product price
     */
    public Product(String group, String name, String description, String producer, int value, double price) {
        this.group = group;
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.value = value;
        this.price = price;
    }

    /**
     * Create product with all parameters without value(default 1)
     * @param group - product group
     * @param name - product name
     * @param description - product description
     * @param producer - product producer
     * @param price - product price
     */
    public Product(String group, String name, String description, String producer, double price) {
        this.group = group;
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.value = 1;
        this.price = price;
    }

    public String getGroup() { return group; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getProducer() { return producer; }

    public int getValue() { return value; }

    public double getPrice() { return price; }
}

