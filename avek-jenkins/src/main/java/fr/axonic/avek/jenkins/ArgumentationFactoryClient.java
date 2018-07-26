package fr.axonic.avek.jenkins;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatus;
import fr.axonic.avek.engine.StepToCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 08/08/17.
 */
public class ArgumentationFactoryClient {

    private static final Logger LOGGER= LoggerFactory.getLogger(ArgumentationFactoryClient.class);

    private String serverURL;

    public ArgumentationFactoryClient(String serverURL) {
        this.serverURL = serverURL;
    }

    public JustificationStep sendStep(String argumentationSystem, String patternId, JenkinsStatus jenkinsStatus, List<SupportArtifact> supportArtifacts) throws IOException {

        URL url =new URL(serverURL+argumentationSystem+"/"+patternId+"/step");
        LOGGER.info("Call URL "+url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        ObjectMapper mapper=new JerseyMapperProvider().getContext();
        List<Support> supports=new ArrayList<>();
        for(SupportArtifact supportArtifact: supportArtifacts){
            try {
                File artifactURL=new File(supportArtifact.getArtifactPath());
                Document document=new Document(artifactURL.getName());
                Support support= (Support) Class.forName(supportArtifact.getSupportId()).getDeclaredConstructor(String.class,Document.class).newInstance(artifactURL.getName(),document);
                supports.add(support);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                LOGGER.error(e.toString());
            }

        }
        Conclusion ccl= null;
        try {
            Pattern pattern=getPattern(argumentationSystem,patternId);
            ccl=pattern.getOutputType().create(null,jenkinsStatus);
        } catch (ArgumentationFactoryException | StepBuildingException e) {
            LOGGER.error(e.toString());
        }
        StepToCreate stepToCreate=new StepToCreate(supports,ccl);
        OutputStream wr = new DataOutputStream(conn.getOutputStream());
        mapper.writeValue(wr,stepToCreate);
        wr.flush();
        wr.close();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            conn.disconnect();
            throw new ResponseException(conn.getResponseCode(),"Server returned HTTP response code:"+conn.getResponseCode()+" for URL "+url.toString());
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        conn.disconnect();
        return mapper.readValue(result.toString(), JustificationStep.class);
    }

    public List<String> getPatterns(String argumentationSystem) throws ArgumentationFactoryException{
        try{
            URL url =new URL(serverURL+argumentationSystem+"/patterns");
            LOGGER.info("Call URL "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();



            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                conn.disconnect();
                throw new ArgumentationFactoryException(new NotFoundException());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()),StandardCharsets.UTF_8));
            StringBuilder res = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                res.append(output);
            }
            br.close();
            conn.disconnect();
            return mapper.readValue(res.toString(),List.class);
        }
        catch (IOException e){
            throw new ArgumentationFactoryException(e);
        }


    }

    public Pattern getPattern(String argumentationSystem, String patternID) throws ArgumentationFactoryException{
        try{
            URL url =new URL(serverURL+argumentationSystem+"/patterns/"+patternID);
            LOGGER.info("Call URL "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();



            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                conn.disconnect();
                throw new ArgumentationFactoryException(new NotFoundException());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()),StandardCharsets.UTF_8));
            StringBuilder res = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                res.append(output);
            }
            br.close();
            conn.disconnect();
            return mapper.readValue(res.toString(),Pattern.class);
        }
        catch (IOException e){
            throw new ArgumentationFactoryException(e);
        }


    }
    public List<String> getArgumentationSystems() throws ArgumentationFactoryException {
        try{
            URL url =new URL(serverURL+"systems");
            LOGGER.info("Call URL "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();



            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                conn.disconnect();
                throw new ArgumentationFactoryException(new NotFoundException());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()),StandardCharsets.UTF_8));
            StringBuilder res = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                res.append(output);
            }
            br.close();
            conn.disconnect();
            return mapper.readValue(res.toString(),List.class);
        }
        catch (IOException e){
            throw new ArgumentationFactoryException(e);
        }

    }

    public JustificationSystem getArgumentationSystem(String argumentationSystem) throws IOException {

        URL url =new URL(serverURL+argumentationSystem);
        LOGGER.info("Call URL "+url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        ObjectMapper mapper = new ObjectMapper();



        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            conn.disconnect();
            throw new HTTPException(conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream()),StandardCharsets.UTF_8));
        StringBuilder res = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            res.append(output);
        }
        br.close();
        conn.disconnect();
        return mapper.readValue(res.toString(), JustificationSystem.class);

    }

}
