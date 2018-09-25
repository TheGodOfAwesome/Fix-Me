import controller.RouterController;
import handler.ExceptionHandler;
import model.RouterConfig;

import java.net.SocketException;

public class Router {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        try {
            RouterConfig routerConfig = configureRouter();
            RouterController router = new RouterController(routerConfig);
            router.startRouterServers();
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    public static RouterConfig configureRouter() {
        String host = "localhost";
        int port = 5000;
        RouterConfig routerConfig = new RouterConfig(host, port);
        return routerConfig;
    }

}
