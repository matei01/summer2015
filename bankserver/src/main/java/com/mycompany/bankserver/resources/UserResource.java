
package com.mycompany.bankserver.resources;

import com.mycompany.bankserver.exceptions.DataNotFoundException;
import com.mycompany.bankserver.model.Account;
import com.mycompany.bankserver.model.Transaction;
import com.mycompany.bankserver.model.User;
import com.mycompany.bankserver.services.UserService;
import java.io.IOException;
import static java.lang.System.console;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.json.JsonWriter;
import static javax.ws.rs.HttpMethod.OPTIONS;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;



@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    
    Logger log = Logger.getLogger(UserResource.class);
    
    UserService userService=new UserService();

    
    
    
    @GET
    @Lock(LockType.READ)
    public List<User> getUsers(){
        log.info("GET API called for id all users ");
        return userService.getAllUsers();       
    }
    
    @GET
    @Lock(LockType.READ)
    @Path("/{userName}")
    public List<Account> getUserAccounts(@PathParam("userName") String person){
        log.info("GET API called for id : " + person);
        return userService.getUserAccounts(person);
    }
    
    @POST
    @Lock(LockType.WRITE)
    @Consumes(MediaType.APPLICATION_JSON)
    public User addUser(User user){
        log.info("User" + user.getName() + "added");
        return userService.addUser(user);
    }
    
    @POST
    @Lock(LockType.WRITE)
    @Path("/{userName}")
    public Account addAccount(@PathParam("userName") String userName,Account account){
        log.info("account: " + account.getName() + "added");
        return userService.addAccount(userName,account);
    }
    
    @GET
    @Lock(LockType.READ)
    @Path("/{userName}/{accountName}")
    public Account update1Account(@PathParam("userName") String userName, @PathParam("accountName") String accountName){
        log.info("account: " + accountName + "called with get");
        return userService.get1Account(userName,accountName);

    }
    @PUT
    @Lock(LockType.WRITE)
    @Path("/{userName}")
    public Account updateAccount(@PathParam("userName") String userName,Account account){
        log.info("account: " + account.getName() + "updated with" + account.getAmount());
        return userService.updateAccount(userName,account);
    }
    
    @GET
    @Lock(LockType.READ)
    @Path("/{userName}/{accountName}/transactions")
    public List<Transaction> getTransactions(@PathParam("accountName") String accountName){
        log.info("get transactions method called for account:" + accountName);
        return userService.getTransactions(accountName);
    }
    /*
    @PUT
    @Path("/{userName}/{accountName}")
    public Account transferToAccount(@PathParam("userName") String userName,
                    @PathParam("accountName") String accountName,Account account){
        return userService.transferToAccount(accountName,account);
    }
    */
    
    @GET
    @Lock(LockType.READ)
    @Path("/{userName}/balance")
    @Produces(MediaType.TEXT_PLAIN)
    public Long getUserBalance(@PathParam("userName") String userName){
        log.info("get balance method called for user:" + userName);
        return userService.getUserBalance(userName);
    }
    /*@GET
    @Path("/{userName}")
    public int getBalance(@PathParam("userName") Long name){
        return userService.getBalance(name);
    }
    */
    
}
