package sax;

import java.io.Serializable;

public class PortResource implements Serializable {
    private static final long serialVersionUID = -3895203507200457732L;
    private int port;

    public PortResource() {
        this.port = 8080;
    }

    public PortResource(int port) {
        this.setPort(port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
