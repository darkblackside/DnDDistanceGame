package Client;

import Client.GUI.Mainframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

/**
 * Created by bmers on 05.07.2016.
 */
public class ClientSynchronizer implements ActionListener, Runnable
{
    Mainframe mainframe;
    BlockingQueue<String> in;
    BlockingQueue<String> out;

    public ClientSynchronizer(Mainframe mainframe, BlockingQueue<String> in, BlockingQueue<String> out)
    {
        this.mainframe = mainframe;
        this.in = in;
        this.out = out;

        mainframe.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equalsIgnoreCase("sendText"))
        {
            try
            {
                in.put(mainframe.getText());
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String s = out.take();
                mainframe.attachToLog(s + "\r\n");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
