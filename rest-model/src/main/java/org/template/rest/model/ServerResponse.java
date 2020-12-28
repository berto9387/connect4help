package org.template.rest.model;

/**
 * @author Victor Mezrin
 */
public class ServerResponse {

    boolean result;

    String cognome;

    public boolean getResult() {
        return result;
    }

    public String getCognome() {
        return cognome;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setCognome(String result) {
        this.cognome = result;
    }
}
