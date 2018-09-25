package controller;

import handler.SocketServerHandler;
import model.RouterConfig;
import java.nio.channels.*;
import java.net.*;

public class RouterServerController implements Runnable {

    public RouterConfig routerConfig;

    public RouterServerController(RouterConfig _routerConfig) {
        routerConfig = _routerConfig;
    }

    @Override
    public void run() {
        SocketServerHandler socketServerHandler = new SocketServerHandler();
        String  host = routerConfig.getHost();
        int     port = routerConfig.getPort();
        System.out.println("Port: " + port + "\nHost:" + host);

        try
        {
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
                InetSocketAddress sAddr = new InetSocketAddress(host, port);
            server.bind(sAddr);
            // getAddress() method
            System.out.println("Inet Address : " + sAddr.getAddress());
            // getPort() method
            System.out.println("Inet Port : " + sAddr.getPort());
            if (port % 2 == 0)
                System.out.format("Broker Server is listening at %s%n", sAddr);
            else
                System.out.format("Market Server is listening at %s%n", sAddr);
            SocketServerHandler.Attachment attach = new SocketServerHandler.Attachment();
            attach.server = server;
            server.accept(attach, new SocketServerHandler.ConnectionHandler());
            Thread.currentThread().join();
        }
        catch(Exception e)
        {
            System.out.println("RouterServerController");
            System.out.println(e);
        }
    }
}
