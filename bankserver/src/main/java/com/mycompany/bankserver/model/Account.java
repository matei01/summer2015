package com.mycompany.bankserver.model;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author M
 */
public class Account {
    private List<Transaction> transactions= new ArrayList<>();
    private String name;
    private long amount;
    
    public Account(){
        
    }
    
    public Account(String name, long amount){
        this.name=name;
        this.amount=amount;
    }
    
    
    
    public List<Transaction> getTransactions(){
        return transactions;
    }
    public void addTransaction(Transaction transact){
        transactions.add(transact);
    }
    
    
    public String getName(){
        return name;
    }
    public void setName(String newname){
        this.name=newname;
    }
    
    public long getAmount(){
        return amount;
    }
    public void setAmount(long newamount){
        this.amount=newamount;
    }

    
}
