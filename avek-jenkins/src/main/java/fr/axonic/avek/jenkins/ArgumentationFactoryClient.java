package fr.axonic.avek.jenkins;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.JerseyMapperProvider;
import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatus;
import fr.axonic.avek.service.StepToCreate;

import javax.ws.rs.NotFoundException;
import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 08/08/17.
 */
public class ArgumentationFactoryClient {

    private String serverURL;

    public ArgumentationFactoryClient(String serverURL) {
        this.serverURL = serverURL;
    }

    public void sendStep(String argumentationSystem, String patternId, String conclusionId, JenkinsStatus jenkinsStatus, List<SupportArtifact> supports) throws IOException {

        URL url =new URL(serverURL+argumentationSystem+"/"+patternId+"/step");
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        ObjectMapper mapper=new JerseyMapperProvider().getContext(null);
        List<SupportRole> supportRoles=new ArrayList<>();
        for(SupportArtifact supportArtifact: supports){
            try {
                File artifactURL=new File(supportArtifact.getArtifactPath());
                Document document=new Document(artifactURL.getAbsolutePath());
                Support support= (Support) Class.forName(supportArtifact.getSupportId()).getDeclaredConstructor(String.class,Document.class).newInstance(artifactURL.getName(),document);
                supportRoles.add(new SupportRole("jenkins-artifact",support));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        Conclusion ccl= null;
        try {
            ccl = (Conclusion) Class.forName(conclusionId).getDeclaredConstructor(JenkinsStatus.class).newInstance(jenkinsStatus);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        StepToCreate stepToCreate=new StepToCreate(supportRoles,ccl);
        OutputStream wr = new DataOutputStream(conn.getOutputStream());
        mapper.writeValue(wr,stepToCreate);
        wr.flush();
        wr.close();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            conn.disconnect();
            throw new ResponseException(conn.getResponseCode(),"Server returned HTTP response code:"+conn.getResponseCode()+" for URL "+url.toString());
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
