/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 *
 * @author M
 */
public class Prog {

    /**
     * @param args the command line arguments
     */
        public static void main (String[] args)
    {
        try
        {
            Prog obj = new Prog ();
            obj.run (args);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    public String help="help:commands;\n"
            + "transfer:transfer\n"+
            "exit: exit\n";

    public void run (String[] args) throws JSONException, IOException
    {
        int k=0;
        while(k==0){
            Scanner sc = new Scanner(System.in);
            System.out.print("your command:");
            String i = sc.nextLine();

            if("exit".equals(i))
                k=1;
            else{
                if("help".equals(i)){
                    System.out.print(help);
                }
                else 
                    if("transfer".equals(i)){
                    System.out.print("user:");
                    String user = sc.nextLine();
                    System.out.print("from account:");
                    String account1 = sc.nextLine();
                    System.out.print("to account:");
                    String account2 = sc.nextLine();
                    System.out.print("amount:");
                    Long amount = sc.nextLong();
                    Long amount1=getAmountfromAcc(account1,user);
                    Long amount2=getAmountfromAcc(account2,user);
                    JSONObject obj1=new JSONObject();
                    JSONObject obj2=new JSONObject();
                    obj1.put("name",account1);
                    obj1.put("amount",amount1-amount);
                    String obj11=obj1.toString();
                    transferStuff(obj11,user);
                    
                    obj2.put("name",account2);
                    obj2.put("amount",amount2+amount);
                    String obj22=obj2.toString();
                    transferStuff(obj22,user);
                    System.out.print("accounts updated\n");

                }
                    else
                    System.out.print("incorrect command\n");
            }
        }
    }
    
    public Long getAmountfromAcc(String accountName,String userName) throws JSONException{
        JSONObject jsonArray = null;

                    try {
                        URL u = new URL("http://localhost:8080/bankserver/users/"+userName+"/"+accountName);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line + '\n');
                        }
                        String jsonString = stringBuilder.toString();
                        jsonArray = new JSONObject(jsonString);
                        Long amountt= jsonArray.getLong("amount");
                        System.out.print("amount:" +amountt +"/n");
                        return amountt;


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            return null;
    }
    
    public void transferStuff(String obj,String user) throws MalformedURLException, IOException{
        
        URL object=new URL("http://localhost:8080/bankserver/users/"+user);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("PUT");
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(obj);
        wr.flush();
        wr.close();
        con.getInputStream();
    }
}
