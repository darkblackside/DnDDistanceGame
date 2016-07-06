package Models.Files;

import Models.Files.Rights.Group;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmers on 04.07.2016.
 */
public class File implements Serializable
{
    private String filename;
    private String location;

    private Group needsGroup;
    private int needsRightLevel;
    private String needsSpecialRight;

    public File(String location, String filename)
    {
        this.location = location;
        this.filename = filename;
    }

    public File(String filename)
    {
        this.filename = filename;
        this.location = "data/";
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    public String getFilename()
    {
        return filename;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    public String getLocation()
    {
        return location;
    }

    public java.io.File getFile()
    {
        return new java.io.File(location, filename);
    }

    public void deleteFile()
    {
        java.io.File file1 = new java.io.File(location, filename);
        if(file1.exists())
        {
            file1.delete();
        }
    }

    public void createFile() throws IOException
    {
        java.io.File file1 = new java.io.File(location, filename);
        if(file1.exists())
        {
            file1.createNewFile();
        }
    }

    public void createFile(String ... content) throws IOException
    {
        createFile();
        Path file = Paths.get(location, filename);

        List<String> contents = new ArrayList<String>();

        for(int i = 0; i < content.length; i++)
        {
            contents.add(content[i]);
        }

        Files.write(file, contents);
    }

    public String getData()
    {
        Path file = Paths.get(location, filename);

        StringBuilder sb = new StringBuilder(512);
        try
        {
            Reader r = new InputStreamReader(new FileInputStream(file.toString()));
            int c = 0;

            while ((c = r.read()) != -1)
            {
                sb.append((char) c);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public Group getNeedsGroup() {
        return needsGroup;
    }

    public void setNeedsGroup(Group needsGroup) {
        this.needsGroup = needsGroup;
    }

    public int getNeedsRightLevel() {
        return needsRightLevel;
    }

    public void setNeedsRightLevel(int needsRightLevel) {
        this.needsRightLevel = needsRightLevel;
    }

    public String getNeedsSpecialRight() {
        return needsSpecialRight;
    }

    public void setNeedsSpecialRight(String needsSpecialRight) {
        this.needsSpecialRight = needsSpecialRight;
    }
}
