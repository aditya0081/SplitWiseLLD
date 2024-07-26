package Balance;

import Expense.Expense;

import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;

public class BalanceManager {
    private static volatile BalanceManager instance;
    Map<Integer, Balance> balanceMap;
//    Map<Integer, List<Integer>> groupIdToBalanceIdMap;
    private static final AtomicInteger balanceCounter = new AtomicInteger(0);

    private BalanceManager(){
        balanceMap = new HashMap<Integer, Balance>();
//        groupIdToBalanceIdMap = new HashMap<>();
    }

    public static BalanceManager getBalanceManager(){
        if(instance == null){
            synchronized (BalanceManager.class){
                if(instance == null)
                    instance = new BalanceManager();
                return instance;
            }
        }
        return instance;
    }

    public void createOrUpdate(int fromUser, int toUser, int amount){
        Collection<Balance> values = balanceMap.values();

//      if balance exit then update
        for(Balance balance : values){
            if((balance.fromUserId == fromUser && balance.toUserId == toUser)
                    || (balance.fromUserId == toUser && balance.toUserId == fromUser)) {
                balance.updateAmount(fromUser, toUser, amount);
                return;
            }
        }
//        create one balance
        int balanceId = balanceCounter.incrementAndGet();
        Balance balance = new Balance(fromUser, toUser, amount, balanceId);
        balanceMap.put(balanceId, balance);
        return;
    }

    public void createOrUpdate(int fromUser, int toUser, int amount, int groupId){
        Collection<Balance> values = balanceMap.values();

//      if balance exit then update
        for(Balance balance : values){
            if(balance.groupId == groupId && ((balance.fromUserId == fromUser && balance.toUserId == toUser)
                    || (balance.fromUserId == toUser && balance.toUserId == fromUser))) {
                balance.updateAmount(fromUser, toUser, amount);
                return;
            }
        }
//        create one balance
        int balanceId = balanceCounter.incrementAndGet();
        Balance balance = new Balance(fromUser, toUser, amount, balanceId, groupId);
        balanceMap.put(balanceId, balance);
        return;
    }

    public void createOrUpdate(Expense expense){
//        only going with equal amount
        int netAmount = expense.amount / (expense.toUser.size() + 1);

        for(int toUser : expense.toUser){
            createOrUpdate(expense.fromUser, toUser, netAmount);
        }
    }

    public void createOrUpdate(Expense expense, int groupId){
//        only going with equal amount
        int netAmount = expense.amount / (expense.toUser.size() + 1);

        for(int toUser : expense.toUser){
            createOrUpdate(expense.fromUser, toUser, netAmount, groupId);
        }
    }


    public List<Balance> showAllBalancesForUser(int userId){
        Collection<Balance> balances = balanceMap.values();
        List<Balance> balanceList = new ArrayList<>();
        for(Balance balance : balances){
            if(balance.fromUserId == userId || balance.toUserId == userId)
                balanceList.add(balance);
        }

        return balanceList;
    }

    public List<Balance> showAllBalancesForGroup(int groupId){
        Collection<Balance> balances = balanceMap.values();
        List<Balance> balanceList = new ArrayList<>();
        for(Balance balance : balances){
            if(balance.groupId == groupId)
                balanceList.add(balance);
        }
        return balanceList;
    }
}
