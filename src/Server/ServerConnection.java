package Server;

import Logging.Logger;
import Models.Dice.DICE_TYPE;
import Models.Dice.Dice;
import Models.Files.File;
import Models.User.User;
import Server.Data.DataManagement;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/**
 * Created by bmers on 14.06.2016.
 */
public class ServerConnection implements Runnable, AutoCloseable, IServerListener
{
    Socket clientSocket;
    PrintWriter clientWriter;
    BufferedReader clientReader;
    boolean end = false;
    User user = null;

    List<ServerConnection> otherClients;

    public ServerConnection(Socket s, List<ServerConnection> otherClients)
    {
        this.otherClients = otherClients;
        clientSocket = s;
    }

    @Override
    public void run()
    {
        try
        {
            String str = "";
            String username = "";
            OutputStream os = null;
            os = clientSocket.getOutputStream();

            clientWriter = new PrintWriter(os, true);
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            boolean passwordRight = false;

            while(!passwordRight)
            {
                clientWriter.println("name?");

                str = clientReader.readLine();
                username = str;

                clientWriter.println("password?");

                str = clientReader.readLine();

                if(DataManagement.getInstance().userExists(username))
                {
                    if(DataManagement.getInstance().getUser(username).passwordEquals(str))
                    {
                        clientWriter.println("passwordcorrect");
                        passwordRight = true;

                        user = DataManagement.getInstance().getUser(username);
                    }

                    str = "";
                }
                else
                {
                    clientWriter.println("repeatPassword?");

                    String str2 = clientReader.readLine();

                    if(str.equals(str2))
                    {
                        User u = new User(username);
                        u.setPassword(str2);

                        DataManagement.getInstance().addUser(u);

                        clientWriter.println("accountCreatedPleaseLogIn");
                    }
                    else
                    {
                        clientWriter.println("passwordNotEqual");
                    }

                    str = "";
                    str2 = "";
                }
            }

            while(!end)
            {
                str = clientReader.readLine();

                if(str.startsWith("!"))
                {
                    if (str.startsWith("!exit"))
                    {
                        //Verbindung:
                        //!exit
                        end = true;
                        clientWriter.println("!exit");
                    }
                    else if (str.startsWith("!dice"))
                    {
                        //Verbindung:
                        //!dice;-;;;-;-;;;-;d4/d6/d8/d10/d12/d20;-;;;-;-;;;-;all/me
                        DICE_TYPE dt = null;
                        String[] splitted = str.split(";-;;;-;-;;;-;");

                        if (splitted.length == 3) {
                            switch (splitted[1]) {
                                case "d4":
                                    dt = DICE_TYPE.D4;
                                    break;
                                case "d6":
                                    dt = DICE_TYPE.D6;
                                    break;
                                case "d8":
                                    dt = DICE_TYPE.D8;
                                    break;
                                case "d10":
                                    dt = DICE_TYPE.D10;
                                    break;
                                case "d12":
                                    dt = DICE_TYPE.D12;
                                    break;
                                case "d20":
                                    dt = DICE_TYPE.D20;
                                    break;
                                default:
                                    clientWriter.write("!format not right: " + str);
                            }

                            if (dt != null)
                            {
                                int diceResult = Dice.ThrowDice(dt);

                                if (splitted[2].equalsIgnoreCase("all"))
                                {
                                    sendActionToAllUsers("!Diced " + diceResult + "");
                                }
                                else
                                {
                                    sendActionToUser("!Diced " + diceResult + "");
                                }
                            }
                        }
                    }
                    else if(str.startsWith("!newFile"))
                    {
                        //Verbindung:
                        //!newFile;-;;;-;-;;;-;filename;-;;;-;-;;;-;groupname;-;;;-;-;;;-;rightlevel;-;;;-;-;;;-;specialright;-;;;-;-;;;-;data
                        String[] splitted = str.split(";-;;;-;-;;;-;");

                        if(splitted.length == 6)
                        {
                            String filename = splitted[1];
                            String groupname = splitted[2];
                            String rightlevel = splitted[3];
                            String specialRight = splitted[4];
                            String data = splitted[5];

                            if(!DataManagement.getInstance().isFileExistent(filename))
                            {
                                Models.Files.File f = new File(filename);
                                f.setNeedsGroup(DataManagement.getInstance().getGroup(groupname));
                                f.setNeedsRightLevel(Integer.parseInt(rightlevel));
                                f.setNeedsSpecialRight(specialRight);
                                f.createFile(data);
                                DataManagement.getInstance().addFile(f);
                                sendActionToUser("!file created: " + filename);
                            }
                            else
                            {
                                sendActionToUser("!filename existent: " + filename);
                            }
                        }
                        else
                        {
                            sendActionToUser("!format wrong");
                        }
                    }
                    else if(str.startsWith("!getFiles"))
                    {
                        //Verbindung:
                        //!getFiles
                        //Return:
                        //!files;-;;;-;-;;;-;filename;-;;;-;-;;;-;filename;-;;;-;-;;;-;filename
                        List<File> f = DataManagement.getInstance().getFiles(user);

                        String result = "!files";

                        for(File file:f)
                        {
                            result = result + ";-;;;-;-;;;-;" + file.getFilename();
                        }

                        sendActionToAllUsers(result);
                    }
                    else if(str.startsWith("!getFile"))
                    {
                        //Verbindung:
                        //!getFile;-;;;-;-;;;-;filename
                        //Return:
                        //!file;-;;;-;-;;;-;filename;-;;;-;-;;;-;data

                        String[] splitted;
                        splitted = str.split(";-;;;-;-;;;-;");

                        if(splitted.length == 2)
                        {
                            File f = DataManagement.getInstance().getFile(user, splitted[1]);
                            if(f != null)
                            {
                                sendActionToUser("!file", ";-;;;-;-;;;-;", f.getFilename(), ";-;;;-;-;;;-;", f.getData());
                            }
                            else
                            {
                                sendActionToUser("!fileNotExistent");
                            }
                        }
                    }
                }
                else
                {
                    sendActionToAllUsers(str);
                }
            }
        }
        catch (IOException e)
        {
            Logger.logString("Could not connect to " + clientSocket.getInetAddress());
            sendActionToAllUsers("!Client disconnected");
        }
    }

    public void sendActionToAllUsers(String firstServerMessage, String... serverMessage)
    {
        for(ServerConnection s:otherClients)
        {
            s.sendActionToUser(new Date().toString() + " - <" + user.getUsername() + ">: " + firstServerMessage, serverMessage);
        }
    }

    @Override
    public void close() throws Exception
    {
        clientWriter.close();
        clientReader.close();
        clientSocket.close();
    }

    public void sendActionToUser(String firstServerMessage, String... serverMessage)
    {
        if(user != null)
        {
            if(clientWriter != null)
            {
                clientWriter.print(firstServerMessage);

                if(serverMessage != null)
                {
                    for(int i = 0; i < serverMessage.length; i++)
                    {
                        clientWriter.print(serverMessage[i]);
                    }
                }

                clientWriter.println();
            }
        }
    }
}
