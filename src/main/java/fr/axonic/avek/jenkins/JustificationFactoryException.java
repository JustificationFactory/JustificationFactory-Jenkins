package fr.axonic.avek.jenkins;

import javax.ws.rs.NotFoundException;

/**
 * Created by cduffau on 09/08/17.
 */
public class JustificationFactoryException extends Exception {
    public JustificationFactoryException(Exception e) {
        super(e);
    }
}
