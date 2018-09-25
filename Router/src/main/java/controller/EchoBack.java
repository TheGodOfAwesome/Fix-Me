package controller;

import handler.SocketServerHandler;

public class EchoBack {
    private int ECHOBACK = IType.ECHOBACK;

    public void performAction(SocketServerHandler.Attachment attatch, int resp)
    {
        if (resp != ECHOBACK)
            return ;
        attatch.isRead = false;
        attatch.client.write(attatch.buffer, attatch, attatch.rwHandler);
    }
}
