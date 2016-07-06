package Models.Mask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmers on 04.07.2016.
 */
public class CheckboxValue extends RadioValue
{
    private List<Integer> selected;

    public CheckboxValue()
    {
        selected = new ArrayList<Integer>();
    }


    public void deleteRadioOption(String option)
    {
        if(isExistentInOptions(option))
        {
            if(selected.contains(getKey(option)))
            {
                selected.remove((Integer)getKey(option));
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

    @Override
    public void select(String option)
    {
        if(isExistentInOptions(option))
        {
            if(!selected.contains((Integer)getKey(option)))
            {
                selected.add(getKey(option));
            }
        }
    }

    public void unSelect(String option)
    {
        if(isExistentInOptions(option))
        {
            if(selected.contains((Integer)getKey(option)))
            {
                selected.remove(getKey(option));
            }
        }
    }

    @Override
    public boolean isSelected(String option)
    {
        return selected.contains((Integer)getKey(option));
    }

    public List<Integer> getSelectedList()
    {
        return selected;
    }
}
