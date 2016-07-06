package Models.Dice;

import java.util.Random;

/**
 * Created by bmers on 14.06.2016.
 */
public class Dice
{
    private DICE_TYPE type;

    public Dice(DICE_TYPE type)
    {
        this.type = type;
    }

    public int Dice()
    {
        return Dice.ThrowDice(type);
    }

    public static int ThrowDice(DICE_TYPE type)
    {
        Random generator = new Random();
        return generator.nextInt(type.getNumVal()) + 1;
    }
}
