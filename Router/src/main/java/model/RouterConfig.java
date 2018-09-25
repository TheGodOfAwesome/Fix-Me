package model;

public class RouterConfig {

    int     port;
    String  host;

    public int getPort(){
        return port;
    }

    public String getHost(){
        return host;
    }

    public int setPort(int newPort){
        port = newPort;
        return port;
    }

    public String setHost(String newHost){
        host = newHost;
        return host;
    }

    public RouterConfig(String setHost, int setPort){
        setHost(setHost);
        setPort(setPort);
    }
}
