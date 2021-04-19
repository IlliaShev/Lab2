package dto;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class handler of group Product
 */
public class HandlerGroupOfProducts {
    /**
     * List GroupOfProducts
     */
    private final List<GroupOfProducts> groups;

    /**
     * Constructor HandlerGroupOfProducts use group
     */
    public HandlerGroupOfProducts() {
        groups = new LinkedList<>();
    }

    /**
     * Add group to list of groups
     *
     * @param group - new group
     */
    public boolean addGroup(GroupOfProducts group) {
        for (GroupOfProducts group1 : groups) {
            if (group.getNameOfGroup().equals(group1.getNameOfGroup()))
                return false;
        }
        groups.add(group);
        return true;
    }

    /**
     * Delete group from a list of groups
     *
     * @param group - group to delete
     */
    public void deleteGroup(GroupOfProducts group) {
        groups.remove(group);
    }

    /**
     * Find products that matches string pattern
     *
     * @param pat - string pattern
     * @return list of products
     */
    public List<Product> findProduct(String pat) {
        Pattern pattern = Pattern.compile(pat);
        List<Product> resList = new LinkedList<>();
        for (GroupOfProducts group : groups) {
            for (Product product : group.getListOfProducts()) {
                Matcher mch = pattern.matcher(product.getName());
                if (mch.matches() || matcherStrings(product.getName(), pat))
                    resList.add(product);

            }
        }
        return resList;
    }

    /**
     * Match String
     *
     * @param s1  string
     * @param pat string
     * @return boolean
     */
    private boolean matcherStrings(String s1, String pat) {
        int j = 0;
        if(pat.length() > s1.length())
            return false;
        for (int i = 0; i < pat.length(); i++) {
            if (pat.charAt(i) == '?') {
                j++;
                continue;
            }
            if (pat.charAt(i) != s1.charAt(j))
                return false;
            j++;
        }
        return true;
    }

    /**
     * @return list of groups
     **/
    public List<GroupOfProducts> getListOfGroups() {
        return groups;
    }


    /**
     * getAllInfo
     *
     * @return info
     */
    public String getAllInfo() {
        List<GroupOfProducts> groupsOfProducts = groups;
        if (groups.size() == 0)
            return "";
        groupsOfProducts.sort(Comparator.comparing(GroupOfProducts::getNameOfGroup));
        StringBuilder result = new StringBuilder();
        for (GroupOfProducts group : groupsOfProducts) {
            result.append("Group: ").append(group.getNameOfGroup()).append("\n");
            result.append("Description: ").append(group.getDescription()).append("\n");
            List<Product> products = group.getListOfProducts();
            products.sort(Comparator.comparing(Product::getName));
            for (Product product : products) {
                result.append("\tProduct name: ").append(product.getName()).append('\n');
            }
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * getAllProducts
     *
     * @return AllProducts
     */
    public String getAllProducts() {
        StringBuilder result = new StringBuilder();
        List<Product> products = new LinkedList<>();
        for (GroupOfProducts group : groups) {
            products.addAll(group.getListOfProducts());
        }
        products.sort(Comparator.comparing(Product::getName));
        for (Product product : products) {
            result.append("Product name: ").append(product.getName()).append("; Value: ").append(product.getValue()).append("\n");
        }
        return result.substring(0, result.length() - 1);
    }

}
