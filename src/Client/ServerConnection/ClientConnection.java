package Client.ServerConnection;

import sun.net.ConnectionResetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by bmers on 20.06.2016.
 */
public class ClientConnection implements Runnable
{
    final String host = "localhost";
    final int portNumber = 81;
    BlockingQueue<String> outQueue;
    BlockingQueue<String> inQueue;
    BufferedReader in;
    PrintWriter out;
    Socket socket;

    public ClientConnection(BlockingQueue<String> outQueue, BlockingQueue<String> inQueue) throws IOException, InterruptedException {
        this.outQueue = outQueue;
        this.inQueue = inQueue;

        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        socket = new Socket(host, portNumber);

        //Get connection-communication elements
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void getAndPut() throws IOException, InterruptedException
    {
        if(in.ready())
        {
            String userOutput = in.readLine();
            outQueue.put(userOutput);

            if ("exit".equalsIgnoreCase(userOutput))
            {
                throw new ConnectionResetException("Server aborted connection");
            }
        }

        if(!inQueue.isEmpty())
        {
            String userInput = inQueue.take();
            out.println(userInput);
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                getAndPut();
            }
            catch(ConnectionResetException e)
            {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
