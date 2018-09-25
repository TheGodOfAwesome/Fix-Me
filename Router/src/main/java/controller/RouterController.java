package controller;

import handler.SocketServerHandler;
import model.RouterConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RouterController {

    private String _host;
    private RouterConfig routerConfig = new RouterConfig("localhost", 5000);
    private RouterConfig _routerConfig;
    private int _port;
    private static List<SocketServerHandler.Attachment> clients = new ArrayList<SocketServerHandler.Attachment>();

    public RouterController(RouterConfig routerConfig)
    {
        _host = routerConfig.getHost();
        _port = routerConfig.getPort();
        _routerConfig = routerConfig;

        _routerConfig = routerConfig;
        _routerConfig.setHost(_routerConfig.getHost());
        _routerConfig.setPort(_routerConfig.getPort() + 1);
    }

    public static void addClient(SocketServerHandler.Attachment client)
    {
        clients.add(client);
    }

    public static SocketServerHandler.Attachment getClient(int id)
    {
        for(SocketServerHandler.Attachment client : clients)
        {
            if (client.clientId == id)
                return client;
        }
        return null;
    }

    public static int getSize()
    {
        return clients.size();
    }

    public void startRouterServers() throws Exception
    {
        System.out.println(routerConfig.getPort());
        System.out.println(_routerConfig.getPort());
        ExecutorService threads = Executors.newCachedThreadPool();
        threads.submit(new RouterServerController(routerConfig));
        threads.submit(new RouterServerController(_routerConfig));
        threads.shutdown();
    }

    public static void removeClient(int id)
    {
        try
        {
            clients.remove(getClient(id));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
