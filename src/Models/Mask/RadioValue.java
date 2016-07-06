package Models.Mask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmers on 04.07.2016.
 */
public class RadioValue extends MaskValue
{
    protected List<String> radiooptions;

    private int selected = -1;

    public RadioValue()
    {
        radiooptions = new ArrayList<String>();
    }

    public void addRadioOption(String option)
    {
        if(!isExistentInOptions(option))
        {
            radiooptions.add(option);
        }
    }
    public void deleteRadioOption(String option)
    {
        if(isExistentInOptions(option))
        {
            if(selected == getKey(option))
            {
                selected = -1;
            }

            radiooptions.remove(getOption(option));
        }
    }
    public void changeRadioOption(int key, String option)
    {
        radiooptions.set(key, option);
    }
    public int getKey(String option)
    {
        return findOption(option);
    }

    public void select(String option)
    {
        if(isExistentInOptions(option))
        {
            selected = findOption(option);
        }
    }

    public boolean isSelected(String option)
    {
        return selected != -1;
    }

    public int getSelected()
    {
        return selected;
    }

    protected boolean isExistentInOptions(String option)
    {
        boolean isExistent = false;

        for(String radiooption:radiooptions)
        {
            if(radiooption.equals(option))
            {
                isExistent = true;
                break;
            }
        }

        return isExistent;
    }

    protected int findOption(String option)
    {
        int result = -1;

        for(String radiooption:radiooptions)
        {
            if(radiooption.equals(option))
            {
                result = radiooptions.indexOf(radiooption);
                break;
            }
        }

        return result;
    }

    protected String getOption(String option)
    {
        String result = null;

        for(String radiooption:radiooptions)
        {
            if(radiooption.equals(option))
            {
                result = radiooption;
                break;
            }
        }

        return result;
    }
}
