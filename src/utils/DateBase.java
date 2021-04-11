package utils;

import dto.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Convert csv to java class
 */
public class DateBase {

    public void addProduct(TreeMap<String, TreeMap<String, Product>> product, Product productItem){
        product.putIfAbsent(productItem.getGroup(), new TreeMap<>());
        product.get(productItem.getGroup()).putIfAbsent(productItem.getName(), productItem);
    }

    public void editProduct(TreeMap<String, TreeMap<String, Product>> product, Product productItem){
        product.get(productItem.getGroup()).put(productItem.getName(), productItem);
    }

    public void deleteProduct(TreeMap<String, TreeMap<String, Product>> product, Product productItem){
        product.get(productItem.getGroup()).remove(productItem.getName(), productItem);
    }

    public void addGroupProduct(TreeMap<String, TreeMap<String, Product>> product, String group){
        product.putIfAbsent(group, new TreeMap<>());
    }

    public void editGroupProduct(TreeMap<String, TreeMap<String, Product>> product,  String group){
        product.put(group, new TreeMap<>());
    }

    public void deleteGroupProduct(TreeMap<String, TreeMap<String, Product>> product, String group){
        product.remove(group);
    }

    public boolean checkGroup(TreeMap<String, TreeMap<String, Product>> product, String group){
        boolean checkable = false;
        for(Map.Entry<String, TreeMap<String, Product>> productItemGroup : product.entrySet()){
            if(productItemGroup.getKey().equals(group)){
                checkable = true;
                break;
            }
        }
        return checkable;
    }

    public boolean checkNameProduct(TreeMap<String, TreeMap<String, Product>> product, String name){
        boolean checkable = false;
        for(Map.Entry<String, TreeMap<String, Product>> productItemGroup : product.entrySet()){
            for(Map.Entry<String, Product> productItemName : productItemGroup.getValue().entrySet()){
                if (productItemName.getKey().equals(name)){
                    checkable = true;
                    break;
                }
            }
        }
        return checkable;
    }

}

