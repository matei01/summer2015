
package com.mycompany.bankserver.exceptions;

/**
 *
 * @author M
 */
public class DataNotFoundException extends RuntimeException {
    
    
    public DataNotFoundException(String message){
        super(message);
    }
}
