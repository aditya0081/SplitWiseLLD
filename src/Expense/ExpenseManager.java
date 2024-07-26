package Expense;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpenseManager {
    Map<Integer, Expense> expenseMap;
    private static volatile ExpenseManager instance;
    private static final AtomicInteger expenseIdCounter = new AtomicInteger(0);
    Map <Integer, List<Integer>> groupIdToExpenseIdMap;
    private ExpenseManager(){
        expenseMap = new HashMap<>();
        groupIdToExpenseIdMap = new HashMap<>();
    }

    public static ExpenseManager getExpenseManager(){
        if(instance == null){
            synchronized (ExpenseManager.class){
                if(instance == null)
                    instance = new ExpenseManager();
                return instance;
            }
        }
        return instance;
    }

    public Expense getExpense(int expenseId) throws Exception{
        if(expenseMap.containsKey(expenseId))
            return expenseMap.get(expenseId);
        throw new Exception("Expense not present");
    }


    public Expense createExpense(int amount, int fromUser, List<Integer> toUser, String description){
        int expenseId = expenseIdCounter.incrementAndGet();
        Expense expense = new Expense(amount, fromUser, toUser, expenseId, description);
        expenseMap.put(expenseId, expense);
        return expense;
    }

    public Expense createExpenseForGroup(int amount, int fromUser, List<Integer> toUser, String description, int groupId){
        int expenseId = expenseIdCounter.incrementAndGet();
        Expense expense = new Expense(amount, fromUser, toUser, expenseId, description, groupId);
        expenseMap.put(expenseId, expense);
        List<Integer> expenseIds = groupIdToExpenseIdMap.getOrDefault(groupId, new ArrayList<>());
        expenseIds.add(expenseId);
        groupIdToExpenseIdMap.put(groupId, expenseIds);
        return expense;
    }

    public List<Expense> showAllExpenseAddedByUser(int userId){
        Collection<Expense> expenses = expenseMap.values();
        List<Expense> expenseList = new ArrayList<>();
        for(Expense expense : expenses){
              if(expense.fromUser == userId)
                  expenseList.add(expense);
        }
        return expenseList;
    }

    public List<Expense> showAllExpenseAddedForUser(int userId){
        Collection<Expense> expenses = expenseMap.values();
        List<Expense> expenseList = new ArrayList<>();
        for(Expense expense : expenses){
            if(expense.toUser.contains(userId))
                expenseList.add(expense);
        }
        return expenseList;
    }

    public List<Expense> showAllExpensesByGroupId(int groupId){
        List<Expense> expenseList = new ArrayList<>();
        for(int expenseId : groupIdToExpenseIdMap.get(groupId))
            if(expenseMap.containsKey(expenseId))
                expenseList.add(expenseMap.get(expenseId));
        return expenseList;
    }
}
