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
                if(mch.matches())
                    resList.add(product);

            }
        }
        return resList;
    }

    /**
     * @return list of groups
     * */
    public List<GroupOfProducts> getListOfGroups(){
        return groups;
    }


}
