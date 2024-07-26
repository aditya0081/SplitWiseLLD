import Balance.*;
import Expense.*;
import Group.Group;
import Group.GroupManager;
import User.User;
import User.*;

import java.util.ArrayList;
import java.util.Arrays;

public class SplitWise {
        public static void runSplitWise(){
            UserManager userManager = UserManager.getUserManager();
            ExpenseManager expenseManager = ExpenseManager.getExpenseManager();
            BalanceManager balanceManager = BalanceManager.getBalanceManager();
            GroupManager groupManager = GroupManager.getGroupManager();

            System.out.println("Creating Users");
            User user1 = userManager.createUser("Aditya");
            User user2 = userManager.createUser("SuajMukhi");
            User user3 = userManager.createUser("Soumya");

            System.out.println("creating Expenses");
            Expense expense1 = expenseManager
                    .createExpense(100, user1.userId, new ArrayList<>(Arrays.asList(user2.userId, user3.userId)), "for lunch" );

            Expense expense2 = expenseManager.createExpense(100, user2.userId, new ArrayList<>(Arrays.asList(user1.userId, user3.userId)), "for beer");


            System.out.println("creating Balances");
            balanceManager.createOrUpdate(expense1);
            balanceManager.createOrUpdate(expense2);

            System.out.println("Expense");
            expenseManager.showAllExpenseAddedByUser(user1.userId).forEach(expense -> expense.printExpense());
            expenseManager.showAllExpenseAddedByUser(user2.userId).forEach(expense -> expense.printExpense());
            expenseManager.showAllExpenseAddedByUser(user3.userId).forEach(expense -> expense.printExpense());

            System.out.println("Balances for each user");
            balanceManager.showAllBalancesForUser(user1.userId).forEach(balance -> balance.printBalance());
            balanceManager.showAllBalancesForUser(user2.userId).forEach(balance -> balance.printBalance());
            balanceManager.showAllBalancesForUser(user3.userId).forEach(balance -> balance.printBalance());

            System.out.println("Adding one more balance");
            Expense expense3 = expenseManager.createExpense(100, user3.userId, new ArrayList<>(Arrays.asList(user1.userId, user2.userId)), "for dinner");
            balanceManager.createOrUpdate(expense3);

            System.out.println("expense and balance after that");
            System.out.println("Expense");
            expenseManager.showAllExpenseAddedByUser(user1.userId).forEach(expense -> expense.printExpense());
            expenseManager.showAllExpenseAddedByUser(user2.userId).forEach(expense -> expense.printExpense());
            expenseManager.showAllExpenseAddedByUser(user3.userId).forEach(expense -> expense.printExpense());

            System.out.println("Balances for each user");
            balanceManager.showAllBalancesForUser(user1.userId).forEach(balance -> balance.printBalance());
            balanceManager.showAllBalancesForUser(user2.userId).forEach(balance -> balance.printBalance());
            balanceManager.showAllBalancesForUser(user3.userId).forEach(balance -> balance.printBalance());


            System.out.println("Creating group and more users");
            User user4 = userManager.createUser("Nishant");
            User user5 = userManager.createUser("Ayan");
            User user6 = userManager.createUser("Rohit");

            System.out.println("Creating group");
            Group group1 = groupManager.createGroup("final_sem_party", "enjoying past memory");
            group1.addUser(user1.userId);
            group1.addUser(user2.userId);
            group1.addUser(user3.userId);
            group1.addUser(user4.userId);
            group1.addUser(user5.userId);
            group1.addUser(user6.userId);

            System.out.println("Adding expense in group: " + group1.groupId);
            group1.addExpense(user1.userId, 1500, "BreakFast");
            group1.addExpense(user2.userId, 2500, "Lunch");
            group1.addExpense(user3.userId, 3000, "Beer");

            System.out.println("Creating group");
            Group group2 = groupManager.createGroup("journey_to_goa", "Spiritual Journey");
            group2.addUser(user1.userId);
            group2.addUser(user2.userId);
            group2.addUser(user3.userId);
            group2.addUser(user4.userId);
            group2.addUser(user5.userId);
            group2.addUser(user6.userId);

            System.out.println("Adding expense in group: " + group1.groupId);
            group2.addExpense(user4.userId, 1500, "BreakFast");
            group2.addExpense(user5.userId, 2500, "Lunch");
            group2.addExpense(user6.userId, 3000, "Beer");

            System.out.println("Printing expenses");
            groupManager.showAllExpenses(group1.groupId);
            groupManager.showAllBalances(group1.groupId);

            System.out.println("Printing expenses");
            groupManager.showAllExpenses(group2.groupId);
            groupManager.showAllBalances(group2.groupId);


        }
}
