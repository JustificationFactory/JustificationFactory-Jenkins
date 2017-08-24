package fr.axonic.avek.jenkins;

import javax.xml.ws.http.HTTPException;

/**
 * Created by cduffau on 23/08/17.
 */
public class ResponseException extends HTTPException {

    private String reason;
    public ResponseException(int responseCode, String reason) {
        super(responseCode);
        this.reason=reason;
    }

    public String getReason() {
        return reason;
    }
}
