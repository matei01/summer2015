package com.mycompany.bankserver.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author M
 */
public class Client {
    int i;
    String[] myStrings = new String[] {"help: display commands",
                                        "createu:create user",
                                        "createa:create account",
                                        "trasnfer:transfer from accounts",
                                         "exit:exit"};
    public int main(){
        for(String string: myStrings){
            System.out.print(string);
        }
        return 0;
    }
}
