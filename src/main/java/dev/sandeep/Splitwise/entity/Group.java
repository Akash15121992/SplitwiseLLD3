package dev.sandeep.Splitwise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "SPLITWISE_GROUP")
public class Group extends BaseModel{
    private String name;

    @ManyToOne
    private User createdBy;
    private LocalDateTime creationDate;
    private double totalAmountSpent;

    @ManyToMany(mappedBy = "groups")
    private List<User> members;
    @OneToMany
    private List<Expense> expenses;
    @OneToMany
    private List<SettlementTransaction> settleUpTransaction;

    /*
    *   Group      User
    *    1          1
    *    M          1
    *
    * Group  -> User
    * M:1
    *
    *Group    Members(User)
    * 1        M
    * M        1
    *
    * M:M
    *
    * Group    Expense
    * 1         M
    * 1         1
    *
    * 1:M
    *
    * Group  Transaction
    * 1       M
    * 1       1
    *
    * 1: M
    * */
}
