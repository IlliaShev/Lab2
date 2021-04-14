package dto;

import java.util.*;

public class GroupOfProducts {

    private String nameOfGroup;
    private Map<Product, Integer> products;


    public GroupOfProducts(String nameOfGroup){
        this.nameOfGroup = nameOfGroup;
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

    @Override
    public String toString() {
        return nameOfGroup;
    }
}
