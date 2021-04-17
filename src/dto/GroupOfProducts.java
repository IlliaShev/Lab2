package dto;

import java.util.*;

public class GroupOfProducts {

    private String nameOfGroup;
    private String description;
    private Map<Product, Integer> products;


    public GroupOfProducts(String nameOfGroup, String description){
        this.nameOfGroup = nameOfGroup;
        this.description = description;
        products = new HashMap<>();
    }

    /**
     * Add new product to map of products
     * @param product - product to add
     * @return true if adding was successful
     * */
    public boolean addProduct(Product product){
        for(Product product1: products.keySet()){
            if(product.getName().equals(product1.getName()))
                return false;
        }
        product.setGroup(this);
        product.setValue(0);
        products.put(product, 0);
        return true;
    }

    /**
     * Add new product and set value of it or just add value of product
     * @param product - product to add
     * @param value - value of product
     * @return true if adding was successful
     * */
    public boolean addProduct(Product product, int value){
        boolean res = true;
        if(!products.containsKey(product))
            res = addProduct(product);
        if(!res)
            return false;
        products.put(product, products.get(product) + value);
        product.setValue(product.getValue() +value);
        return true;
    }

    /**
     * Reduce value of product
     * @param product - product
     * @param remove - remove value
     * @return true if removing was successful
     * */
    public boolean discardProduct(Product product, int remove){
        if(remove > products.get(product))
            return false;
        products.put(product, products.get(product) - remove);
        return true;
    }

    /**
     * Delete product from a list of products
     * @param product - product to delete
     * */
    public void deleteProduct(Product product){
        products.remove(product);
    }

    /**
     * @param product - product
     * @return value of product
     * */
    public int getValue(Product product){
        return products.get(product);
    }

    /**
     * @return list of products
     * */
    public List<Product> getListOfProducts(){
        return new LinkedList<>(products.keySet());
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupInfo(){
        StringBuilder result = new StringBuilder();
        List<Product> products = getListOfProducts();
        products.sort(Comparator.comparing(Product::getName));
        result.append("Name: ").append(nameOfGroup).append("\n");
        result.append("Description: ").append(description).append("\n").append("Products:\n");
        double totalValue = 0;
        for(Product product: products){
            int value = this.products.get(product);
            totalValue+=product.getPrice() * value;
            result.append("\t\t").append(product.getName()).append(": ").append(product.getPrice()).append("$; ").append("Value ").append(value).append("\n");
        }
        result.append("Total cost ").append(totalValue).append(".");
        return result.toString();
    }

    @Override
    public String toString() {
        return nameOfGroup;
    }
}
