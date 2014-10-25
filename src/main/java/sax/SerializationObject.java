package sax;

import java.io.Serializable;

public class SerializationObject implements Serializable {
    private static final long serialVersionUID = -3895203507200457732L;
    private int port;

    public SerializationObject() {
        this.port = 8080;
    }

    public SerializationObject(int port) {
        this.setPort(port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
