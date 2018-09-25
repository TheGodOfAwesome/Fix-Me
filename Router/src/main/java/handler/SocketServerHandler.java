package handler;

import controller.CheckSum;
import controller.IType;
import controller.RouterController;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

public class SocketServerHandler {



    public static class Attachment {
        public AsynchronousServerSocketChannel server;
        public int clientId;
        public AsynchronousSocketChannel client;
        public ByteBuffer buffer;
        public SocketAddress clientAddr;
        public String msg[];
        public boolean isRead;
        public ReadWriteHandler rwHandler;
    }

    public static class ConnectionHandler implements
            CompletionHandler<AsynchronousSocketChannel, Attachment> {
        private static int clientId = 100000;

        @Override
        public void completed(AsynchronousSocketChannel client, Attachment attach) {
            try {
                SocketAddress clientAddr = client.getRemoteAddress();
                System.out.format("Accepted a  connection from  %s%n", clientAddr);
                attach.server.accept(attach, this);
                ReadWriteHandler rwHandler = new ReadWriteHandler();
                Attachment newAttach = new Attachment();
                newAttach.server = attach.server;
                newAttach.client = client;
                newAttach.clientId = clientId++;
                newAttach.buffer = ByteBuffer.allocate(2048);
                newAttach.isRead = false;
                newAttach.clientAddr = clientAddr;
                Charset cs = Charset.forName("UTF-8");
                byte data [] = Integer.toString(newAttach.clientId).getBytes(cs);
                newAttach.rwHandler = rwHandler;
                newAttach.buffer.put(data);
                newAttach.buffer.flip();
                RouterController.addClient(newAttach);
                client.write(newAttach.buffer, newAttach, rwHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void failed(Throwable e, Attachment attach) {
            System.out.println("Failed to accept a  connection.");
            e.printStackTrace();
        }
    }

    public static class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

        private String SOH;
        public ReadWriteHandler()
        {
            SOH = "" + (char)1;
        }

        @Override
        public void completed(Integer result, Attachment attach) {

            if (result == -1) {
                try {
                    attach.client.close();
                    RouterController.removeClient(attach.clientId);
                    String port = attach.server.getLocalAddress().toString().split(":")[1];
                    System.out.format("[" + getServerName(port) + "]Stopped   listening to the   client %s%n",
                            attach.clientAddr);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if (attach.isRead) {
                attach.buffer.flip();
                int limits = attach.buffer.limit();
                byte bytes[] = new byte[limits];
                attach.buffer.get(bytes, 0, limits);
                Charset cs = Charset.forName("UTF-8");
                String msg = new String(bytes, cs);
                String datum[] = msg.split(SOH);
                attach.msg = datum;
                try
                {
                    String port = attach.server.getLocalAddress().toString().split(":")[1];
                    System.out.format("["+ getServerName(port) +"]Client at  %s  says: %s%n", attach.clientAddr,
                            msg.replace((char)1, '|'));
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                attach.isRead = false; // It is a write
                attach.buffer.rewind();
                attach.buffer.clear();
                byte[] data = msg.getBytes(cs);
                attach.buffer.put(data);
                attach.buffer.flip();
                if (attach.client.isOpen() && RouterController.getSize() > 1)
                {
                    new CheckSum().performAction(attach, IType.CHECKSUM);
                    //attach.client.write(attach.buffer, attach, this);
                }
                /*attach.buffer.put(bytes);
                attach.client.write(attach.buffer, attach, this);*/

            } else {
                // Write to the client
                //System.out.println("Hello");
                //attach.client.write(attach.buffer, attach, this);
                attach.isRead = true;
                attach.buffer.clear();
                attach.client.read(attach.buffer, attach, this);

            }
        }

        @Override
        public void failed(Throwable e, Attachment attach) {
            e.printStackTrace();
        }
        private String getServerName(String port)
        {
            if (port.equals("5000"))
                return "Broker Server";
            else
                return "Market Server";
        }

    }
}
