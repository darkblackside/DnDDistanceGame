package Server.Data;

import Models.Files.File;
import Models.Files.Rights.Group;
import Models.Mask.Mask;
import Models.User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmers on 05.07.2016.
 */
public class DataManagement
{
    private static String filename = "data.ser";

    private List<Models.Files.File> filesList;
    private List<User> usersList;
    public List<Mask> maskList;

    private static DataManagement instance = null;

    public static DataManagement getInstance()
    {
        if(instance == null)
        {
            instance = new DataManagement();
        }
        return instance;
    }

    private DataManagement()
    {
        try
        {
            load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void load() throws IOException, ClassNotFoundException
    {
        if(new java.io.File(filename).exists())
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));

            filesList = (List<File>)ois.readObject();
            usersList = (List<User>)ois.readObject();
            maskList = (List<Mask>)ois.readObject();

            ois.close();
        }
        else
        {
            filesList = new ArrayList<File>();
            usersList = new ArrayList<User>();
            maskList = new ArrayList<Mask>();

            save();
        }
    }

    public void save() throws IOException
    {
        if(!new java.io.File(filename).exists())
        {
            new java.io.File(filename).createNewFile();
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));

        oos.writeObject(filesList);
        oos.writeObject(usersList);
        oos.writeObject(maskList);

        oos.close();
    }

    public void addUser(User u)
    {
        usersList.add(u);
        try
        {
            save();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean userExists(String username)
    {
        return getUser(username) != null;
    }

    public User getUser(String username)
    {
        for(User u:usersList)
        {
            if(u.getUsername().equals(username))
            {
                return u;
            }
        }

        return null;
    }

    public boolean isFileExistent(String filename)
    {
        for(File f:filesList)
        {
            if(f.getFilename().equals(filename))
            {
                return true;
            }
        }

        return false;
    }

    public void addFile(File f)
    {
        filesList.add(f);
        try
        {
            save();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Group getGroup(String groupname)
    {
        List<Group> groups = new ArrayList<Group>();

        for(User u:usersList)
        {
            if(u.getRight() != null && u.getRight().getGroup() != null)
            {
                groups.add(u.getRight().getGroup());
            }
        }

        for(Group g:groups)
        {
            if(g.getGroupName().equals(groupname))
            {
                return g;
            }
        }

        return null;
    }

    public List<Models.Files.File> getFiles(User user)
    {
        List<Models.Files.File> result = new ArrayList<File>();

        for(File f:filesList)
        {
            if(userCanGetFile(user, f))
            {
                result.add(f);
            }
        }

        return result;
    }

    public File getFile(User user, String filename)
    {
        File result = null;

        for(File f:filesList)
        {
            if(f.getFilename().equals(filename) && userCanGetFile(user, f))
            {
                result = f;
                break;
            }
        }

        return result;
    }

    public boolean userCanGetFile(User user, File f)
    {
        return((user.getRight() != null && user.getRight().getGroup() != null && f.getNeedsGroup() != null && user.getRight().getGroup().isChildOf(f.getNeedsGroup().getGroupName())) ||
                (user.getRight() != null && user.getRight().hasRightLevel(f.getNeedsRightLevel())) ||
                (user.getRight() != null && f.getNeedsSpecialRight() != null && user.getRight().hasRightForSpecialRight(f.getNeedsSpecialRight())));
    }
}
