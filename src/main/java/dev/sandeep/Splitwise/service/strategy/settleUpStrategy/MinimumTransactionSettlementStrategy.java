package dev.sandeep.Splitwise.service.strategy.settleUpStrategy;

import dev.sandeep.Splitwise.dto.UserAmount;
import dev.sandeep.Splitwise.entity.*;

import java.util.*;

public class MinimumTransactionSettlementStrategy implements SettleUpStrategy{
    @Override
    /*
    2. All borrowers will go to min heap
  3. All lenders will go to max heap
     */
    public List<SettlementTransaction> getSettlementTransactions(List<Expense> expenses) {
        HashMap<User,Double> map = getOutStandingBalance(expenses);
        //Comparator for min heap (ascending order)
        Comparator<UserAmount> minHeapComparator = Comparator.comparingDouble(UserAmount::getAmount);
        //Comparator for max heap (ascending order)
        Comparator<UserAmount> maxHeapComparator = Comparator.comparingDouble(UserAmount::getAmount);

        // Max Heap
        PriorityQueue<UserAmount> maxHeap = new PriorityQueue<>(maxHeapComparator);
        //Min Heap
        PriorityQueue<UserAmount> minHeap = new PriorityQueue<>(minHeapComparator);

        for(Map.Entry<User,Double> entry : map.entrySet()){
            if(entry.getValue() <0){
                minHeap.add(new UserAmount(entry.getKey(), entry.getValue()));
            } else if (entry.getValue() >0) {
                minHeap.add(new UserAmount(entry.getKey(), entry.getValue()));
            }else{
                System.out.println("user does not need to participate in settle up");
            }
        }

        // Pull min from minHeap and max from maxHeap and create the transaction
        // Update the balances , put them back in heap
        // Keep doing until the heap is empty
        List<SettlementTransaction> settlementTransactions = new ArrayList<>();
        while (!minHeap.isEmpty() && !maxHeap.isEmpty()){
            UserAmount borrower = minHeap.poll();
            UserAmount lendor = maxHeap.poll();

            if(Math.abs(borrower.getAmount()) > lendor.getAmount()){
                //lendor = 500 , borrower = -1000
                borrower.setAmount(borrower.getAmount() + lendor.getAmount());
                minHeap.add(borrower);

                //Transaction amount will be smaller amount i.e lendor
                SettlementTransaction settlementTransaction = new SettlementTransaction
                        (borrower.getUser(), lendor.getUser(),lendor.getAmount());
                settlementTransactions.add(settlementTransaction);

            } else if (Math.abs(borrower.getAmount()) < lendor.getAmount()) {
                lendor.setAmount(borrower.getAmount()+ lendor.getAmount());
                maxHeap.add(lendor);

                //lendor = 1000 , borrower = -500
                //Transaction amount of smaller i.e borrower will be added
                SettlementTransaction settlementTransaction = new SettlementTransaction
                        (borrower.getUser(), lendor.getUser(), borrower.getAmount());
                settlementTransactions.add(settlementTransaction);
            }else{ //(Math.abs(borrower.getAmount()) == lendor.getAmount())
                //lendor = 500 , borrower = -500
                //Transaction -> borrower to lendor 500, and both are free from settle up

                System.out.println("Do nothing both are equal");
                SettlementTransaction settlementTransaction = new SettlementTransaction
                        (borrower.getUser(), lendor.getUser(), lendor.getAmount());
            }

        }



        //to Do
        return null;
    }

    public HashMap<User,Double> getOutStandingBalance(List<Expense> expenses){
        HashMap<User,Double> expensesMap = new HashMap<>();
        for(Expense expense: expenses){
            for(UserExpense userExpense: expense.getUserExpenses()){
                User participant = userExpense.getUser();
                double amount = userExpense.getAmount();

                if(expensesMap.containsKey(participant)){
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
                        expensesMap.put(participant, expensesMap.get(participant) + amount);
                    }else{
                        //(userExpense.getUserExpenseType().equals(UserExpenseType.HADTOPAY))
                        expensesMap.put(participant,expensesMap.get(participant) - amount);
                    }
                }else{
                    //for the first time
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
                        expensesMap.put(participant,0 + amount);
                    }else{
                        //(userExpense.getUserExpenseType().equals(UserExpenseType.HADTOPAY))
                        expensesMap.put(participant, 0 - amount);
                    }
                }
            }
        }
        return expensesMap;
    }


}
/*
  1. Go through all the expenses and find the total outstanding balance for each person
  2. All borrowers will go to min heap
  3. All lenders will go to max heap
  4. Pull min from minHeap and max from maxHeap and create the transaction
  5. Update the balances , put them back in heap
  6. Keep doing until the heap is empty

  Case

  1. lendor = 500 , borrower = -500
    both of them will become zero and keep both of them out of heap

  2. lendor = 1000 , borrower = -500
  borrower will become zero , and lendor will become 500 and will be added to maxheap

  3. lendor = 500 , borrower = -1000
  lendor will become zero , and borrower will become -500 and will be added to minHeap
 */
