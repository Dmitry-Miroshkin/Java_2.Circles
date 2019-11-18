package chat.server.core;

import network.ServerSocketThread;
import network.ServerSocketThreadListener;
import network.SocketThread;
import network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private ServerSocketThread server;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");

    public void start(int port) {
        if (server != null && server.isAlive()) {
            System.out.println("Server is already running!");
        } else {
            System.out.printf("Server starts at port: %d\n", port);
            server = new ServerSocketThread(this, "server", port, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            System.out.println("Server is not running");
        } else {
            server.interrupt();
            System.out.println("Server stopped");
        }
    }

    private void putLog(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() +
                ":" + msg;
        System.out.println(msg);
    }

    /**
     * Server Socket Thread Events
     * */

    @Override
    public void onThreadStart(ServerSocketThread thread) {
        putLog("Thread start");
    }

    @Override
    public void onServerStart(ServerSocketThread thread, ServerSocket server) {
        putLog("Server start");
    }

    @Override
    public void onServerAcceptTimeout(ServerSocketThread thread, ServerSocket server) {
        putLog("Accept timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocket server, Socket socket) {
        putLog("Socket accept");
        String name = "SocketThread " +
                socket.getInetAddress() +
                ":" + socket.getPort();
        new SocketThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Exception e) {
        putLog("exception: " +
                e.getClass().getName() +
                ": " + e.getMessage());
    }

    @Override
    public void onThreadStop(ServerSocketThread thread) {
        putLog("Thread stop");
    }

    /**
     * Socket Thread Events
     * */

    @Override
    public synchronized void onSocketThreadStart(SocketThread thread, Socket socket) {
        putLog("Socket Thread Start");
    }

    @Override
    public synchronized void onSocketThreadStop(SocketThread thread) {
        putLog("Socket Thread Stop");
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        thread.sendMessage("Echo: " + msg);
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket ready");
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        putLog("exception: " +
                e.getClass().getName() +
                ": " + e.getMessage());
    }
}
