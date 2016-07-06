package Models.Dice;

/**
 * Created by bmers on 14.06.2016.
 */
public enum DICE_TYPE
{
    D4(4),
    D6(6),
    D8(8),
    D10(10),
    D12(12),
    D20(20);

    private int numVal;

    DICE_TYPE(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
