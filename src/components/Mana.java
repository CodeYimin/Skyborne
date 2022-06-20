package components;

public class Mana extends Component {
    private int mana;
    private int maxMana;

    public Mana(int maxMana) {
        this.mana = maxMana;
        this.maxMana = maxMana;
    }

    public int getCurrent() {
        return mana;
    }

    public void setCurrent(int newMana) {
        int oldMana = this.mana;
        this.mana = newMana;
        // eventManager.emit(new ManaEvent(oldMana, newMana));
    }

    public void use(int mana) {
        setCurrent(this.mana - mana);
    }

    public int getMax() {
        return maxMana;
    }

    public void setMax(int maxMana) {
        this.maxMana = maxMana;
    }
}
