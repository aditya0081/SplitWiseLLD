package Balance;

import User.User;
import User.UserManager;

public class Balance {
    int fromUserId;
    int toUserId;
    int dueAmount;
    int id;
    private final UserManager userManager;
    int groupId;

    Balance(int fromUserId, int toUserId, int dueAmount, int balanceId){
        id = balanceId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.dueAmount = dueAmount;
        userManager = UserManager.getUserManager();
    }
    Balance(int fromUserId, int toUserId, int dueAmount, int balanceId, int groupId){
        id = balanceId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.dueAmount = dueAmount;
        userManager = UserManager.getUserManager();
        this.groupId = groupId;
    }

    public void updateAmount(int fromUserId, int toUserId, int dueAmount){
        if(this.fromUserId == fromUserId && this.toUserId == toUserId){
            this.dueAmount += dueAmount;
            return;
        }
        else if(this.dueAmount > dueAmount){
            this.dueAmount -= dueAmount;
        }
        else {
            this.dueAmount = dueAmount - this.dueAmount;
            this.fromUserId = toUserId;
            this.toUserId = fromUserId;
        }
    }

    public void printBalance(){
        System.out.print("Balance between users, fromUser " + userManager.getUserName(fromUserId) + "; to user: " + userManager.getUserName(toUserId) );
        System.out.println("; amount due : " + dueAmount);
    }

}
