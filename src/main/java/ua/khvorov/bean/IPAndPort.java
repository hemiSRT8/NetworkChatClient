package ua.khvorov.bean;


public class IPAndPort {
    private String ip;
    private int port;

    public IPAndPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "IPAndPort{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
