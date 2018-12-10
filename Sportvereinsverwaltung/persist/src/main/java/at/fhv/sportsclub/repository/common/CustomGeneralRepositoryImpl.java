package at.fhv.sportsclub.repository.common;

import com.mongodb.MongoClient;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Repository;

import java.io.*;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Repository
public class CustomGeneralRepositoryImpl implements CustomGeneralRepository {

    private static final Logger logger = Logger.getRootLogger();
    private final MongoOperations mongoOperations;
    private final MongoClient mongoClient;

    public CustomGeneralRepositoryImpl(MongoOperations mongoOperations, MongoClient mongoClient){
        this.mongoOperations = mongoOperations;
        this.mongoClient = mongoClient;
    }


    @Override
    public boolean loadScript(String scriptFile) {
        StringBuilder script = new StringBuilder();
        try {
            InputStream scriptFileStream = this.getClass().getClassLoader().getResourceAsStream(scriptFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(scriptFileStream));
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        } catch (IOException e){
            logger.error(e);
            return false;
        }
        ScriptOperations scriptOperations = mongoOperations.scriptOps();
        ExecutableMongoScript mongoScript = new ExecutableMongoScript(script.toString());
        scriptOperations.execute(mongoScript);
        return true;
    }

    @Override
    public boolean isConnected(){
        try {
            mongoClient.getAddress();
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
