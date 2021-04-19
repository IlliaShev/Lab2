package dto;

/**
 * Class product with all its properties
 */
public class Product {
    /**
     * name
     */
    private String name;
    /**
     * group
     */
    private GroupOfProducts group;
    /**
     * producer
     */
    private String producer;
    /**
     * description
     */
    private String description;
    /**
     * value
     */
    private int value;
    /**
     * price
     */
    private double price;

    /**
     * Create product with all parameters
     *
     * @param name        - product name
     * @param producer    - product producer
     * @param description - product description
     * @param price       - product price
     */
    public Product(String name, String producer, String description, double price) {
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.price = (double)((int)(price*100))/100;
    }

    /**
     * setName
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getProducer
     *
     * @return producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * setProducer
     *
     * @param producer producer
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * setDescription
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getGroup
     *
     * @return GroupOfProducts
     */
    public GroupOfProducts getGroup() {
        return group;
    }

    /**
     * setGroup
     *
     * @param group group
     */
    public void setGroup(GroupOfProducts group) {
        this.group = group;
    }

    /**
     * setPrice
     *
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getName
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getDescription
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getPrice
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * getValue
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * setValue
     *
     * @param value value
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}

