package dev.sandeep.Splitwise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private String description;
    private double amount;
    private LocalDateTime expenseTime;
    @ManyToOne
    private User addedBy;
    private Currency currency;
    @OneToMany
    private List<UserExpense> userExpenses;

    /*
        Expense   User
        1         1
        M         1

        M:1

        Expense  UserExpense
        1         M
        1         1

        1:M
     */
}
