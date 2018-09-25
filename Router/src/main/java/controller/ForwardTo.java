package controller;

import handler.SocketServerHandler;

public class ForwardTo {


    private int DISPATCH = IType.DISPATCH;

    public void performAction(SocketServerHandler.Attachment attach, int resp)
    {
        if (resp != DISPATCH)
        {
            new EchoBack().performAction(attach, resp);
            return ;
        }
        int id = getDestination(attach.msg);
        int srcId = getSource(attach.msg);
        if (srcId != attach.clientId)
        {
            System.out.println("src = " + srcId + " clientId = "+ attach.clientId);
            new EchoBack().performAction(attach, IType.ECHOBACK);
            return ;
        }
        try
        {
            if (attach.client.isOpen() && RouterController.getSize() > 1)
            {
                SocketServerHandler.Attachment att = RouterController.getClient(id);
                if (att == null)
                {
                    new EchoBack().performAction(attach, IType.ECHOBACK);
                    return ;
                }
                att.isRead = false;
                att.client.write(attach.buffer, att, attach.rwHandler);
            }
        }
        catch(Exception e)
        {
            new EchoBack().performAction(attach, IType.ECHOBACK);
        }
    }

    private int getDestination(String datum[])
    {
        try
        {
            for(int i = 0; i < datum.length; i++)
            {
                if (datum[i].contains("56"))
                    return Integer.parseInt(datum[i].split("=")[1]);
            }
        }
        catch(Exception e)
        {

        }
        return -1;
    }

    private int getSource(String datum[])
    {
        try
        {
            if (datum[0].split("=")[0].equalsIgnoreCase("id"))
                return Integer.parseInt(datum[0].split("=")[1]);
        }
        catch(Exception e)
        {
        }
        return -1;
    }

}
