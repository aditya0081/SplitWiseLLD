package Group;


import User.UserManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Group {
    String groupName;
    public int groupId;
    String groupDescription;
    Set<Integer> userIds;
    private static final UserManager userManager = UserManager.getUserManager();
    private static final GroupManager groupManager = GroupManager.getGroupManager();

    Group(int groupId, String groupName, String groupDescription){
        this.groupDescription = groupDescription;
        this.groupId = groupId;
        this.groupName = groupName;
        this.userIds = new HashSet<>();
    }

    public void addUser(int userId){
        userIds.add(userId);
    }

    public void removeUser(int userId){
        userIds.remove(userId);
    }

    public void print(){
        System.out.println("Group details; groupName: " + groupName + "; groupDescription: " + groupDescription +
                "; users: ");
        for(int id : userIds){
            System.out.print(userManager.getUserName(id) + ", ");
        }
        System.out.println();
    }

    public void addExpense(int fromUserId, List<Integer> toUserIds, int amount, String description ){
        groupManager.addExpense(fromUserId, toUserIds, amount, description, groupId);
    }

    public void addExpense(int fromUserId, int amount, String description ){
        List<Integer> toUserIds = new ArrayList<>();
        for(int id : this.userIds)
            if(id != fromUserId)
                toUserIds.add(id);
        groupManager.addExpense(fromUserId, toUserIds, amount, description, groupId);
    }

}
