
package com.mycompany.bankserver.services;

import com.mycompany.bankserver.database.DatabaseClass;
import com.mycompany.bankserver.model.Account;
import com.mycompany.bankserver.model.Transaction;
import com.mycompany.bankserver.model.User;
import java.util.List;
import com.mycompany.bankserver.exceptions.DataNotFoundException;
import java.io.IOException;


/**
 *
 * @author M
 */
public class UserService {  
    UtilsClass utils = new UtilsClass();
    DatabaseClass dbclass;

    public UserService() {
        dbclass=new DatabaseClass(utils.getMongoProps());

    }
    
    public List<User> getAllUsers(){
        List<User> userlist = dbclass.getAllUsers() ;
        if(userlist.size() < 1) {
            throw new DataNotFoundException("no users found");
        }
        return userlist;
    }
    
    public List<Account> getUserAccounts(String person){
        List<Account> list= dbclass.getUserAccounts(person);
        if(list.size()<1){
            throw new DataNotFoundException("no accounts found");
        }
        return list;
    }

    public User addUser(User user) {
        dbclass.addUser(user);
        return user;
    }

    public Account addAccount(String userName,Account account) {
        return dbclass.addAccount(userName,account);
         
    }

    public Account updateAccount(String userName,Account account) {
        return dbclass.updateAccount(userName, account);
    }
    /*
    public int getBalance(Long name){
        return users.getBalance(name);
    }
    */
    public List<Transaction> getTransactions(String accountName){
        List<Transaction> trans = dbclass.getTransactions(accountName);
        return trans;
    }
    /*
    public Account transferToAccount(String accountName,Account account) {
        return users.transferToAccount(accountName,account);
    }
    */

    public Long getUserBalance(String userName) {
        return dbclass.getUserBalance(userName);
        
    }
    
    public Account get1Account(String userName,String accountName){
        if(dbclass.getSpecificAccount(userName,accountName)==null){
            throw new DataNotFoundException("account not found");
        }
        else
            return dbclass.getSpecificAccount(userName, accountName);
    }
    
    
}
