package dto;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerGroupOfProducts {
    private List<GroupOfProducts> groups;

    public HandlerGroupOfProducts(){
        groups = new LinkedList<>();
    }

    /**
     * Add group to list of groups
     * @param group - new group
     * @return true if adding was successful
     * */
    public boolean addGroup(GroupOfProducts group){
        for(GroupOfProducts group1: groups){
            if(group.getNameOfGroup().equals(group1.getNameOfGroup()))
                return false;
        }
        groups.add(group);
        return true;
    }

    /**
     * Delete group from a list of groups
     * @param group - group to delete
     * */
    public void deleteGroup(GroupOfProducts group){
        groups.remove(group);
    }

    /**
     * Find products that matches string pattern
     * @param pat - string pattern
     * @return list of products
     * */
    public List<Product> findProduct(String pat){
        Pattern pattern = Pattern.compile(pat);
        List<Product> resList = new LinkedList<>();
        for(GroupOfProducts group: groups){
            for(Product product: group.getListOfProducts()){
                Matcher mch = pattern.matcher(product.getName());
                if(mch.matches()||matcherStrings(product.getName(), pat))
                    resList.add(product);

            }
        }
        return resList;
    }

    private boolean matcherStrings(String s1, String pat){
        int j = 0;
        for(int i = 0; i < pat.length(); i++){
           if(pat.charAt(i) == '?') {
               j++;
               continue;
           }
           if(pat.charAt(i) != s1.charAt(j))
               return false;
           j++;
        }
        return true;
    }

    /**
     * @return list of groups
     * */
    public List<GroupOfProducts> getListOfGroups(){
        return groups;
    }


    public String getAllInfo(){
        List<GroupOfProducts> groupsOfProducts = groups;
        if(groups.size() == 0)
            return "";
        groupsOfProducts.sort(Comparator.comparing(GroupOfProducts::getNameOfGroup));
        StringBuilder result = new StringBuilder();
        for(GroupOfProducts group: groupsOfProducts){
            result.append("Group: ").append(group.getNameOfGroup()).append("\n");
            result.append("Description: ").append(group.getDescription()).append("\n");
            List<Product> products = group.getListOfProducts();
            products.sort(Comparator.comparing(Product::getName));
            for(Product product: products){
                result.append("\t\tProduct name: ").append(product.getName()).append('\n');
            }
        }
        return result.substring(0,result.length()-1);
    }

    public String getAllProducts(){
        StringBuilder result = new StringBuilder();
        List<Product> products = new LinkedList<>();
        for(GroupOfProducts group: groups){
            products.addAll(group.getListOfProducts());
        }
        products.sort(Comparator.comparing(Product::getName));
        for(Product product: products){
            result.append("\t\tProduct name: ").append(product.getName()).append("; Value: ").append(product.getValue());
        }
        return result.substring(0,result.length()-1);
    }

}
