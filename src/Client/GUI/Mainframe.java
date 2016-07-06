package Client.GUI;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by bmers on 05.07.2016.
 */
public class Mainframe extends JFrame
{
    JPanel panelBottom;
    JButton sendButton;
    JTextField sendField;
    JTextArea logArea;
    JScrollPane pane;
    JPanel panelEast;
    JScrollPane fileTreePane;
    JTree fileTree;
    JPanel fileTreeEast;
    JButton fileTreeAdd;
    JButton fileTreeRemove;
    JButton fileTreeDownload;

    public Mainframe()
    {
        super("DnD Mainframe - Client");
        panelBottom = new JPanel();
        panelEast = new JPanel();
        sendButton = new JButton("Absenden");
        sendButton.setActionCommand("sendText");
        sendField = new JTextField();
        sendField.setPreferredSize(new Dimension(650, 20));
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setPreferredSize(new Dimension(800, 400));
        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        pane = new JScrollPane(logArea);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fileTree = new JTree();
        fileTreePane = new JScrollPane(fileTree);
        fileTreePane.setPreferredSize(new Dimension(300, 400));
        fileTreeEast = new JPanel();
        fileTreeAdd = new JButton("Add file");
        fileTreeAdd.setActionCommand("addFile");
        fileTreeRemove = new JButton("Delete file");
        fileTreeRemove.setActionCommand("deleteFile");
        fileTreeDownload = new JButton("Download file");
        fileTreeDownload.setActionCommand("downloadFile");

        fileTreeEast.setLayout(new GridLayout(3, 1));
        fileTreeEast.add(fileTreeAdd, 0);
        fileTreeEast.add(fileTreeRemove, 1);
        fileTreeEast.add(fileTreeDownload, 2);

        panelEast.add(fileTreePane, BorderLayout.CENTER);
        panelEast.add(fileTreeEast, BorderLayout.EAST);

        panelBottom.add(sendField, BorderLayout.CENTER);
        panelBottom.add(sendButton, BorderLayout.EAST);

        this.getRootPane().setDefaultButton(sendButton);
        this.add(pane, BorderLayout.CENTER);
        this.add(panelBottom, BorderLayout.SOUTH);
        this.add(panelEast, BorderLayout.EAST);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }

    public void attachToLog(String text)
    {
        logArea.setText(logArea.getText() + text);
    }

    public void clearLog()
    {
        logArea.setText("");
    }

    public String getText()
    {
        String text = sendField.getText();
        sendField.setText("");
        return text;
    }

    public void addActionListener(ActionListener al)
    {
        sendButton.addActionListener(al);
        fileTreeAdd.addActionListener(al);
        fileTreeRemove.addActionListener(al);
        fileTreeDownload.addActionListener(al);
    }
}
