package Models.Files.Rights;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmers on 04.07.2016.
 */
public class Right implements Serializable
{
    private Group group;
    private int permissionLevel;
    private List<String> specialRights;

    public Right()
    {
        specialRights = new ArrayList<String>();
    }

    public Group getGroup()
    {
        return group;
    }
    public void setGroup(Group g)
    {
        group = g;
    }

    public int getPermissionValue()
    {
        return permissionLevel;
    }
    public void setPermissionValue(int permissionLevel)
    {
        this.permissionLevel = permissionLevel;
    }

    public List<String> getSpecialRights()
    {
        List<String> copy;
        copy = new ArrayList<String>();
        copy.addAll(specialRights);
        return copy;
    }
    public void deleteSpecialRight(String s)
    {
        if(specialRightExists(s))
        {
            specialRights.remove(getStringNumber(s));
        }
    }
    public void addSpecialRight(String s)
    {
        if(!specialRightExists(s))
        {
            specialRights.add(s);
        }
    }

    private int getStringNumber(String search)
    {
        for(String s:specialRights)
        {
            if(s.equals(search))
            {
                return specialRights.indexOf(s);
            }
        }

        return -1;
    }

    private boolean specialRightExists(String search)
    {
        for(String s:specialRights)
        {
            if(s.equals(search))
            {
                return true;
            }
        }

        return false;
    }

    public boolean hasRightForSpecialRight(String specialRight)
    {
        return (specialRightExists(specialRight));
    }

    public boolean hasRightForGroup(Group groupToFind)
    {
        return (group.isChildOf(groupToFind.getGroupName()));
    }

    public boolean hasRightLevel(int level)
    {
        return (permissionLevel >= level);
    }
}
