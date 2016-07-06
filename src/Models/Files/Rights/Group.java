package Models.Files.Rights;

import java.io.Serializable;

/**
 * Created by bmers on 04.07.2016.
 */
public class Group implements Serializable
{
    private String groupName;
    private Group extend;

    public Group(String groupName)
    {
        this.groupName = groupName;
        extend = null;
    }

    public String getGroupName()
    {
        return groupName;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public Group getExtendedGroup()
    {
        return extend;
    }
    public void setExtendedGroup(Group extend)
    {
        this.extend = extend;
    }

    public boolean equals(String s)
    {
        return this.getGroupName().equals(s);
    }

    public boolean isChildOf(String group)
    {
        boolean result = false;
        Group g = this;

        while(g != null)
        {
            if(g.equals(group))
            {
                result = true;
                break;
            }

            g = g.getExtendedGroup();
        }

        return result;
    }
}
