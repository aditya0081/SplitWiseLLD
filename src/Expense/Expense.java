package Expense;

import User.User;
import User.UserManager;

import java.util.List;

public class Expense {
    public int id;
    public int fromUser;
    public List<Integer> toUser;
    public int amount;
    public String description;
    private final UserManager userManager;
    int groupId; // this can be divided into user level and group level, but as of now keeping the same

    Expense(int amount, int fromUser, List<Integer> toUser, int expenseId, String description){
        id = expenseId;
        this.amount = amount;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.description = description;
        this.userManager = UserManager.getUserManager();
    }
    Expense(int amount, int fromUser, List<Integer> toUser, int expenseId, String description, int groupId){
        id = expenseId;
        this.amount = amount;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.description = description;
        this.userManager = UserManager.getUserManager();
        this.groupId = groupId;
    }

    public void setToUser(List<Integer> toUser){
        this.toUser = toUser;
    }

    public void printExpense(){
        System.out.print("expense from user " + userManager.getUserName(this.fromUser) + "; expense to users: ");
        for(int id : this.toUser)
            System.out.print(userManager.getUserName(id) + ", ");

        System.out.println("; description " + this.description + "; amount: " + this.amount);
    }
//    ....
}
