package resources;

public class ServerStartResource implements Resource {
    private int port;

    public ServerStartResource() {
        setPort(8000);
    }

    public ServerStartResource(int port) {
        this.setPort(port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
