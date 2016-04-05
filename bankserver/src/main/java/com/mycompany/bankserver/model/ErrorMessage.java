
package com.mycompany.bankserver.model;

/**
 *
 * @author M
 */
public class ErrorMessage {
    private String message;
    private int code;
    
    public ErrorMessage(){
    }
    
    public ErrorMessage(String msg, int cd){
        this.message=msg;
        this.code=cd;
    }
    
    public String getMessage(){
        return message;
    }
    public void setMessage(String msg){
        this.message=msg;
    }
    
    public int getCode(){
        return code;
    }
    public void setCode(int code){
        this.code = code;
    }
}
