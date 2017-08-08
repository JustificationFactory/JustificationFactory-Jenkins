package fr.axonic.avek.jenkins;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Element;

import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cduffau on 08/08/17.
 */
public class ArgumentationFactoryClient {

    private String serverURL;

    public ArgumentationFactoryClient(String serverURL) {
        this.serverURL = serverURL;
    }

    public void sendPattern(String argumentationSystem, String patternId) throws IOException {

        URL url =new URL(serverURL+"/step/"+argumentationSystem+"/"+patternId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        ObjectMapper mapper = new ObjectMapper();
        Conclusion conclusion=new Conclusion("test", new Element());
        String conclusionJson =mapper.writeValueAsString(conclusion);

        String evidencesJson="";

        OutputStream os = conn.getOutputStream();
        os.write(("evidences="+evidencesJson).getBytes());
        os.write(("conclusion="+conclusionJson).getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            System.out.println(conn.getResponseCode());
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
            throw new HTTPException(conn.getResponseCode());
        }
        conn.disconnect();

    }

    public static void main(String[] args) throws IOException {
        new ArgumentationFactoryClient("http://localhost:9999/rest/argumentation").sendPattern("AXONIC","1");

    }
}
