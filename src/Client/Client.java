package Client;

import Client.GUI.Mainframe;
import Client.ServerConnection.ClientConnection;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by bmers on 20.06.2016.
 */
public class Client
{
    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<String> out = new ArrayBlockingQueue<String>(10000);
        BlockingQueue<String> in = new ArrayBlockingQueue<String>(10000);

        ClientConnection cc = new ClientConnection(out, in);

        Mainframe m = new Mainframe();

        ClientSynchronizer synchronizer = new ClientSynchronizer(m, in, out);

        Thread t1 = new Thread(cc);
        t1.start();

        Thread t2 = new Thread(synchronizer);
        t2.start();
    }
}
