package dto;

import java.util.*;

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
     * @return list of groups
     * */
    public List<GroupOfProducts> getListOfGroups(){
        return groups;
    }


}
