package com.mycompany.bankserver.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author M
 */
class UtilsClass {
    
    Logger log = Logger.getLogger(UtilsClass.class);
    public UtilsClass(){
        
    }
    
    public List<String> getMongoProps(){
        List<String> list = null;
        try{
            list = Files.readAllLines(Paths.get("C:/New Folder/mongo.proprieties"),
                                    Charset.defaultCharset());
        }
        catch (IOException ex){
            log.info("something wrong with reading");
        }
        return list;
    }
}
