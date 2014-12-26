package resources;

public class ServerConfigResource implements Resource {
    private int port;
    private int socketTimeOut;
    private int serviceSleepTime;

    public ServerConfigResource() {
        setPort(9000);
        setSocketTimeOut(10);
        setServiceSleepTime(100);
    }

    public ServerConfigResource(int port, int socketTimeOut, int serviceSleepTime) {
        setPort(port);
        setSocketTimeOut(socketTimeOut);
    }

    public int getServiceSleepTime() {
        return serviceSleepTime;
    }

    public void setServiceSleepTime(int serviceSleepTime) {
        this.serviceSleepTime = serviceSleepTime;
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
