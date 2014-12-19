package resources;

public class ServerConfigResource implements Resource {
    private int port;
    private int socketTimeOut;

    public ServerConfigResource() {
        setPort(9000);
        setSocketTimeOut(10);
    }

    public ServerConfigResource(int port, int socketTimeOut) {
        setPort(port);
        setSocketTimeOut(socketTimeOut);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }
}
