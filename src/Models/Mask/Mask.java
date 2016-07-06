package Models.Mask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bmers on 04.07.2016.
 */
public class Mask
{
    private Map<String, MaskValue> maskElements;
    private String maskName;

    public Mask()
    {
        maskElements = new HashMap<String, MaskValue>();
    }

    public void setMaskName(String name)
    {
        this.maskName = name;
    }

    public String getMaskName()
    {
        return maskName;
    }

    public MaskValue getMaskValue(String key)
    {
        return maskElements.get(key);
    }

    public void addMaskValue(String key, MaskValue mask)
    {
        maskElements.put(key, mask);
    }

    public void deleteMaskValue(String key)
    {
        maskElements.remove(key);
    }

    public void editMaskValue(String key, MaskValue mask)
    {
        maskElements.put(key, mask);
    }
}
