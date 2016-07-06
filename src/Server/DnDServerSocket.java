package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmers on 14.06.2016.
 */
public class DnDServerSocket
{
    public static void main(String args[]) throws IOException {
        final int portNumber = 81;
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        List<ServerConnection> serverConnections = new ArrayList<ServerConnection>();

        while (true)
        {
            ServerConnection connection = new ServerConnection(serverSocket.accept(), serverConnections);
            serverConnections.add(connection);
            Thread t = new Thread(connection);
            t.start();
        }
    }
}
