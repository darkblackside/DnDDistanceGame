package Models.User;

import Models.Files.Rights.Right;

import java.io.Serializable;

/**
 * Created by bmers on 04.07.2016.
 */
public class User implements Serializable
{
    private String username;
    private String password;
    private Right right;

    public User(String username)
    {
        this.username = username;
        right = new Right();
    }

    public String getUsername()
    {
        return username;
    }

    public boolean passwordEquals(String password)
    {
        return (password.equals(this.password));
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Right getRight()
    {
        return right;
    }

    public void setRight(Right right)
    {
        this.right = right;
    }
}
