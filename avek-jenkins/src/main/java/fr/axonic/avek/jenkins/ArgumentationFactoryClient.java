package fr.axonic.avek.jenkins;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.JerseyMapperProvider;
import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.service.StepToCreate;
import org.apache.commons.validator.routines.UrlValidator;

import javax.ws.rs.NotFoundException;
import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by cduffau on 08/08/17.
 */
public class ArgumentationFactoryClient {

    private String serverURL;

    public ArgumentationFactoryClient(String serverURL) {
        this.serverURL = serverURL;
    }

    public void sendStep(String argumentationSystem, String patternId, SupportArtifact conclusion, List<SupportArtifact> supports) throws IOException {

       URL url =new URL(serverURL+argumentationSystem+"/"+patternId+"/step");
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        ObjectMapper mapper=new JerseyMapperProvider().getContext(null);
        StepToCreate stepToCreate=new StepToCreate();
        OutputStream wr = new DataOutputStream(conn.getOutputStream());
        mapper.writeValue(wr,stepToCreate);
        wr.flush();
        wr.close();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuilder reason=new StringBuilder();
            while ((output = br.readLine()) != null) {
                reason.append(output);
            }
            conn.disconnect();
            throw new ResponseException(conn.getResponseCode(),reason.toString());
        }
        conn.disconnect();

    }

    public List<String> getPatterns(String argumentationSystem) throws ArgumentationFactoryException{
        try{
            URL url =new URL(serverURL+argumentationSystem+"/patterns");
            System.out.println(url);
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
                    (conn.getInputStream())));
            String res="";
            String output;
            while ((output = br.readLine()) != null) {
                res+=output;
            }
            conn.disconnect();
            return mapper.readValue(res,List.class);
        }
        catch (IOException e){
            throw new ArgumentationFactoryException(e);
        }


    }

    public Pattern getPattern(String argumentationSystem, String patternID) throws ArgumentationFactoryException{
        try{
            URL url =new URL(serverURL+argumentationSystem+"/patterns/"+patternID);
            System.out.println(url);
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
                    (conn.getInputStream())));
            String res="";
            String output;
            while ((output = br.readLine()) != null) {
                res+=output;
            }
            conn.disconnect();
            return mapper.readValue(res,Pattern.class);
        }
        catch (IOException e){
            throw new ArgumentationFactoryException(e);
        }


    }
    public List<String> getArgumentationSystems() throws ArgumentationFactoryException {
        try{
            URL url =new URL(serverURL+"systems");
            System.out.println(url);
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
                    (conn.getInputStream())));
            String res="";
            String output;
            while ((output = br.readLine()) != null) {
                res+=output;
            }
            conn.disconnect();
            return mapper.readValue(res,List.class);
        }
        catch (IOException e){
            throw new ArgumentationFactoryException(e);
        }

    }

    public ArgumentationSystem getArgumentationSystem(String argumentationSystem) throws IOException {

        URL url =new URL(serverURL+argumentationSystem);
        System.out.println(url);
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
                (conn.getInputStream())));
        String res="";
        String output;
        while ((output = br.readLine()) != null) {
            res+=output;
        }
        conn.disconnect();
        System.out.println(res);
        return mapper.readValue(res,ArgumentationSystem.class);

    }

    public static void main(String[] args) throws ArgumentationFactoryException {
        List<String> patterns=new ArgumentationFactoryClient("http://localhost:9999/rest/argumentation/").getPatterns("AXONIC");
        System.out.println(patterns.get(0));

    }
}
