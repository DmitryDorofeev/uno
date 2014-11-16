package resources;

public class PortResource implements Resource {
    private int port;

    public PortResource() {
        setPort(8080);
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
