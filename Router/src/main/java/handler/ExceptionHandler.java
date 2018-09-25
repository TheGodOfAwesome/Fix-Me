package handler;

import java.io.IOException;
import java.net.SocketException;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            if (e instanceof SocketException) {
                // swallow
            } else if (e instanceof IOException) {
                // swallow
            } else if (e instanceof NullPointerException) {
                // swallow
            } else {
                //e.printStackTrace();
            }
        }
    }
