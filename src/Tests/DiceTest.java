package Tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import Models.Dice.*;

/**
 * Created by bmers on 14.06.2016.
 */
public class DiceTest extends TestCase
{
    public DiceTest(String name)
    {
        super(name);
    }

    public void testDice()
    {
        boolean isTrue = true;

        Dice d = new Dice(DICE_TYPE.D4);
        for(int i = 0; i < 1000; i++)
        {
            int value = d.Dice();
            if(value > 4 || value < 1)
            {
                isTrue = false;
            }
        }

        d = new Dice(DICE_TYPE.D6);
        for(int i = 0; i < 1000; i++)
        {
            int value = d.Dice();
            if(value > 6 || value < 1)
            {
                isTrue = false;
            }
        }

        d = new Dice(DICE_TYPE.D8);
        for(int i = 0; i < 1000; i++)
        {
            int value = d.Dice();
            if(value > 8 || value < 1)
            {
                isTrue = false;
            }
        }

        d = new Dice(DICE_TYPE.D10);
        for(int i = 0; i < 1000; i++)
        {
            int value = d.Dice();
            if(value > 10 || value < 1)
            {
                isTrue = false;
            }
        }

        d = new Dice(DICE_TYPE.D12);
        for(int i = 0; i < 1000; i++)
        {
            int value = d.Dice();
            if(value > 12 || value < 1)
            {
                isTrue = false;
            }
        }

        d = new Dice(DICE_TYPE.D20);
        for(int i = 0; i < 1000; i++)
        {
            int value = d.Dice();
            if(value > 20 || value < 1)
            {
                isTrue = false;
            }
        }

        assertTrue(isTrue);
    }

    public static void main(String[] args) {
        //junit.swingui.TestRunner.run(AdresseTest.class);
        TestRunner.run(DiceTest.class);
    }
}
