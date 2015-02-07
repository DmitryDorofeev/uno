package resources;

public class ServerConfigResource implements Resource {
    private int port;
    private int socketTimeOut;
    private int serviceSleepTime;
    private int logPeriod;

    public ServerConfigResource() {
        setPort(9000);
        setSocketTimeOut(10);
        setServiceSleepTime(100);
        setLogPeriod(60);
    }

    public ServerConfigResource(int port, int socketTimeOut, int serviceSleepTime, int logPeriod) {
        setPort(port);
        setSocketTimeOut(socketTimeOut);
        setServiceSleepTime(serviceSleepTime);
        setLogPeriod(logPeriod);
    }

    public int getLogPeriod() {
        return logPeriod;
    }

    public void setLogPeriod(int logPeriod) {
        this.logPeriod = logPeriod;
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
