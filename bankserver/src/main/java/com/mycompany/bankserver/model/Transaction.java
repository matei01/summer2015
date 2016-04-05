
package com.mycompany.bankserver.model;

import java.util.Date;

/**
 *
 * @author M
 */
public class Transaction {
    
    private Date date;
    private Long amount;
    
    public Transaction(){
    }
    
    public Transaction(Long amount){
        
        this.amount=amount;
        this.date= new Date();
    }
    
    
    
    public Date getDate(){
        return this.date;
    }
    
    public Long getAmount(){
        return this.amount;
    }
    
    
    public void setAmount(Long amount){
        this.amount=amount;
    }
    
    
    
}
