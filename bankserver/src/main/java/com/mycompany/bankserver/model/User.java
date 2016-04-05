
package com.mycompany.bankserver.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author M
 */


public class User {
    private String name;
    private String password;
    private List<Account> accounts = new ArrayList<>();
    
    public User(){
        
    }
    public User(String name, String password){
        this.name=name;
        this.password=password;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String newname){
        this.name=newname;
    }
    
    public String getPassword(){
        return password;
    }
    public void setPassword(String newpassword){
        this.password=newpassword;
    }
    
    public List<Account> getAccounts(){
        return accounts;
    }
    public void addAccount(Account account){
        this.accounts.add(account);
    }
    
    
    
}
