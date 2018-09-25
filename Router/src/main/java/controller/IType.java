package controller;

import handler.SocketServerHandler;

public interface IType {

    public static int CHECKSUM = 1;
    public static int DISPATCH = 2;
    public static int ECHOBACK = 3;
    public void performAction(SocketServerHandler.Attachment attach, int resp);
}
