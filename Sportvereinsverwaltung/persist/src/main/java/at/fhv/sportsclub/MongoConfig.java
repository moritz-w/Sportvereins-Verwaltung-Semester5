package at.fhv.sportsclub;

import com.mongodb.MongoClient;
import com.mongodb.MongoSocketOpenException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
      Created: 05.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Configuration
public class MongoConfig {

    private static final Logger logger = Logger.getRootLogger();

    @Value("classpath:mongo.properties")
    private Resource propertyResource;

    @Bean
    public MongoClient mongoClient(){
        Properties mongoProperties = new Properties();
        try {
            File file = propertyResource.getFile();
            InputStream inputStream = new FileInputStream(file);
            mongoProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(
                    mongoProperties.getProperty("mongo.host"),
                    Integer.parseInt(mongoProperties.getProperty("mongo.port"))
            );
            mongoClient.getAddress();
        } catch (Exception e){
            logger.fatal(e);
            logger.fatal("Failed to connect to database!");
        }
        return mongoClient;
    }
}
