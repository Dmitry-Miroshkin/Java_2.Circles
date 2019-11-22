package chat.server.core;

import chat.common.Library;
import network.ServerSocketThread;
import network.ServerSocketThreadListener;
import network.SocketThread;
import network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/*
	 1. Досконально разобраться с кодом
	 2. Составить вопросы
	 3. * Разобрать принимаемую строку и отформатировать
* */

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private ServerSocketThread server;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");
    private final ChatServerListener listener;
    private final Vector<SocketThread> clients = new Vector<>();

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        if (server != null && server.isAlive()) {
            putLog("Server is already running!");
        } else {
            putLog("Server starts at port: " + port);
            server = new ServerSocketThread(this, "server", port, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putLog("Server is not running");
        } else {
            server.interrupt();
            putLog("Server stopped");
        }
    }

    private void putLog(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() +
                ":" + msg;
//        System.out.println(msg);
        listener.onChatServerMessage(this, msg);
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
        SqlClient.connect();
        //putLog(SqlClient.getNickname("ivan", "123"));
    }

    @Override
    public void onServerAcceptTimeout(ServerSocketThread thread, ServerSocket server) {
    }

    @Override
    public void onSocketAccepted(ServerSocket server, Socket socket) {
        putLog("Socket accept");
        String name = "SocketThread " +
                socket.getInetAddress() +
                ":" + socket.getPort();
        new chat.server.core.ClientThread(this, name, socket);
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
        SqlClient.disconnect();
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
        clients.remove(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthorizedMsg(client, msg);
        } else {
            handleNonAuthorizedMsg(client, msg);
        }
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket ready");
        clients.add(thread);
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        putLog("exception: " +
                e.getClass().getName() +
                ": " + e.getMessage());
    }

    private void handleAuthorizedMsg(ClientThread thread, String msg) {
        sendToAllAuthorizedClients(msg);
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    private void handleNonAuthorizedMsg(ClientThread newClient, String msg) {
//        /auth_request§login§password
        String[] arr = msg.split(Library.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Library.AUTH_REQUEST)) {
            newClient.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog("Invalid password for login: " + login);
            newClient.authFail();
        } else {
            newClient.authAccept(nickname);
            sendToAllAuthorizedClients(
                    Library.getTypeBroadcast("Server", nickname + "connected"));
        }
    }

}
