package dto;

/**
 * Class product with all its properties
 */
public class Product {
    private String name;
    private GroupOfProducts group;
    private String producer;
    private String description;
    private double price;

    /**
     * Create product with all parameters
     * @param name - product name
     * @param producer - product producer
     * @param description - product description
     * @param price - product price
     */
    public Product(String name, String producer, String description, double price) {
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
    }

    /**
     * Getters and setters
     * */
    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GroupOfProducts getGroup() {
        return group;
    }

    public void setGroup(GroupOfProducts group) {
        this.group = group;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name;
    }
}

