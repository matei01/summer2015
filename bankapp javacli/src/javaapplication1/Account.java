package javaapplication1;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author M
 */
public class Account {
    private String name;
    private long amount;
    
    public Account(){
        
    }
    
    public Account(String name, long amount){
        this.name=name;
        this.amount=amount;
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
