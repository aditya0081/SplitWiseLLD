package Group;

import Balance.*;
import Expense.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupManager {
    private static volatile GroupManager instance;
    private final Map<Integer, Group> groupMap;
    private final static AtomicInteger groupIdCounter = new AtomicInteger(0);
    private final static ExpenseManager expenseManager = ExpenseManager.getExpenseManager();
    private final static BalanceManager balanceManager = BalanceManager.getBalanceManager();
    private GroupManager(){
        groupMap = new HashMap<Integer, Group>();
    }

    public static GroupManager getGroupManager(){
        if(instance == null){
            synchronized (GroupManager.class){
                if(instance == null)
                    instance = new GroupManager();
                return instance;
            }
        }
        return instance;
    }

    public Group createGroup(String groupName, String groupDescription){
        Group group = new Group(groupIdCounter.incrementAndGet(), groupName, groupDescription);
        groupMap.put(group.groupId, group);
        return group;
    }
    public Group getGroup(int groupId){
        return groupMap.get(groupId);
    }

    public void addExpense(int fromUserId, List<Integer> toUserIds, int amount, String description, int groupId) {
        Expense expense = expenseManager.createExpenseForGroup(amount, fromUserId, toUserIds, description, groupId);
        balanceManager.createOrUpdate(expense, groupId);
    }

    public void showAllExpenses(int groupId){
        System.out.println("Printing expenses for: " + groupMap.get(groupId).groupName);
        expenseManager.showAllExpensesByGroupId(groupId).forEach(expense -> expense.printExpense());
    }

    public void showAllBalances(int groupId){
        System.out.println("Printing balances for: " + groupMap.get(groupId).groupName);
        balanceManager.showAllBalancesForGroup(groupId).forEach(balance -> balance.printBalance());
    }

    }
