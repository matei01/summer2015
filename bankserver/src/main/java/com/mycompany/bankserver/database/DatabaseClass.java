
package com.mycompany.bankserver.database;

/**
 *
 * @author M
 */
import com.mongodb.*;
import com.mycompany.bankserver.exceptions.DataNotFoundException;
import com.mycompany.bankserver.model.Account;
import com.mycompany.bankserver.model.Transaction;
import com.mycompany.bankserver.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;
import org.bson.types.ObjectId;

public class DatabaseClass {
    private Mongo mongo;
    private DB db;
    private DBCollection collection;
    private DBCollection accounts;
    private DBCollection transactions;

    
    public DatabaseClass(List<String> lista) {
        try {
            mongo = new Mongo(lista.get(0),Integer.parseInt(lista.get(1)));
            db = mongo.getDB(lista.get(2));
            collection = db.getCollection(lista.get(3));
            accounts = db.getCollection(lista.get(4));
            transactions = db.getCollection(lista.get(5));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) { 
            BasicDBObject obj = (BasicDBObject) cursor.next();
            BasicDBObject query = new BasicDBObject("person_id", obj.getString("_id"));
            DBCursor cursor2 = accounts.find(query);
            User user = new User(obj.getString("name"),obj.getString("password"));
            while (cursor2.hasNext()){
                BasicDBObject obj2 = (BasicDBObject) cursor2.next();
                Account account = new Account(obj2.getString("name"),obj2.getLong("amount"));
                user.addAccount(account);
            }
        users.add(user);
        }
        return users;
    }
    
    public List<Account> getUserAccounts(String person ){
        List<Account> accountsList = new ArrayList<>();
        DBCursor cursor = collection.find(new BasicDBObject("name",person));
        if(cursor.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor.next();
            DBCursor cursor2 = accounts.find(new BasicDBObject("person_id",obj.getObjectId("_id")));
            while (cursor2.hasNext()){
                BasicDBObject obj2 = (BasicDBObject) cursor2.next();
                Account account = new Account(obj2.getString("name"),obj2.getLong("amount"));
                accountsList.add(account);
        }
        return accountsList;
        }
        else 
            return null;
        
    }
    public Account getSpecificAccount(String userName, String accountName){
        List<Account> accountsList= getUserAccounts(userName);
        for(Account account : accountsList){
            if(account.getName().equals(accountName)){
                return account;
            }
        }
        return null;
    }

    public User addUser(User user) {
        BasicDBObject query = new BasicDBObject("name", user.getName());
        DBCursor cursor = collection.find(query);
        if(cursor.hasNext()){
            throw new DataNotFoundException("user already exists");
        }
        
        BasicDBObjectBuilder userBuilder = BasicDBObjectBuilder.start()
                .add("name", user.getName())
                .add("password",user.getPassword());
        collection.insert(userBuilder.get()); 
        
        return user;
    }

    public Account addAccount(String userName, Account account) {
        //verifying that the account name is unique, so i won't have problems when updating an account;
        BasicDBObject query = new BasicDBObject("name", account.getName());
        DBCursor cursor = accounts.find(query);
        if(cursor.hasNext()){
            throw new DataNotFoundException("account already exists");

        }
        
        if(account.getAmount()<0)
            throw new DataNotFoundException("you can't open an account in debt");
        
        BasicDBObject query2 = new BasicDBObject("name", userName);
        DBCursor cursor2 = collection.find(query2);
        if(cursor2.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor2.next();
            BasicDBObjectBuilder accountBuilder = BasicDBObjectBuilder.start()
                .add("name",account.getName())
                .add("amount",account.getAmount())
                .add("person_id",obj.getObjectId("_id"));
            accounts.insert(accountBuilder.get());

            return account;
        }
        else return null;
        
    }

    public Account updateAccount(String userName,Account account) {
        
        BasicDBObject query = new BasicDBObject("name", account.getName());
        DBCursor cursor = accounts.find(query);
        if(cursor.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor.next();
            BasicDBObjectBuilder transactionBuilder = BasicDBObjectBuilder.start()
                .add("account_id",obj.getObjectId("_id"))
                .add("amount",account.getAmount()-obj.getLong("amount"));
            transactions.insert(transactionBuilder.get());
            
            BasicDBObjectBuilder accountBuilder = BasicDBObjectBuilder.start()
                    .add("name",account.getName())
                    .add("person_id",obj.getObjectId("person_id"))
                    .add("amount",account.getAmount());


            accounts.update(query, accountBuilder.get());

            return account;
        }
        else return null;
        
    }
    /*
    public int getBalance(Long name) {
        BasicDBObject query = new BasicDBObject("person", name);
        DBCursor cursor = collection2.find(query);
        int balance = 0;
        while(cursor.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor.next();
            balance+=obj.getInt("amount");
        }
        return balance;
    }
    */
    
    public List<Transaction> getTransactions(String accountName){
        
        List<Transaction> transacts = new ArrayList<>();
        BasicDBObject query = new BasicDBObject("name", accountName);
        DBCursor cursor = accounts.find(query);
        if(cursor.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor.next();
            BasicDBObject query2 = new BasicDBObject("account_id", obj.getObjectId("_id"));
            DBCursor cursor2 = transactions.find(query2);
            while(cursor2.hasNext()){
                BasicDBObject obj2 = (BasicDBObject) cursor2.next();
                Transaction transaction = new Transaction(obj2.getLong("amount"));
                transacts.add(transaction);
            }
            return transacts;
        }
        else
            return null;
    }
    /*
    public Account transferToAccount(String accountName, Account account) {
        BasicDBObject query = new BasicDBObject("name", accountName);
        
        
        DBCursor cursor = collection2.find(query);
        if (cursor.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor.next();
            if(obj.getLong("amount")<account.getAmount())
                return null;
            else{
                BasicDBObjectBuilder accountBuilder = BasicDBObjectBuilder.start()
                    .add("amount",obj.getLong("amount")-account.getAmount());
                collection2.update(query, accountBuilder.get());
                
                BasicDBObject query2 = new BasicDBObject("name", account.getName());
                DBCursor cursor2 = collection2.find(query);
                BasicDBObject obj2 = (BasicDBObject) cursor2.next();
                BasicDBObjectBuilder accountBuilder2 = BasicDBObjectBuilder.start()
                    .add("amount",obj2.getLong("amount")+account.getAmount());
                collection2.update(query2, accountBuilder2.get());
            }

        }
        else 
            return null;
        return account;
        
    }
    */

    public Long getUserBalance(String userName) {
        BasicDBObject query = new BasicDBObject("name", userName);
        Long balance;
        balance = new Long(0);
        DBCursor cursor = collection.find(query);
        if(cursor.hasNext()){
            BasicDBObject obj =(BasicDBObject) cursor.next();
            BasicDBObject query2 = new BasicDBObject("person_id", obj.getObjectId("_id"));
            DBCursor cursor2 = accounts.find(query2);
            while(cursor2.hasNext()){
                BasicDBObject obj2 =(BasicDBObject) cursor2.next();
                balance=balance+obj2.getLong("amount");
            }
            return balance;
            
        }
        else 
            return null;
    }

    
    
    
    
}
