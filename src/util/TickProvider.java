package util;

public class TickProvider {

    private int currentTick;

    public TickProvider() {
        this(0);
    }

    public TickProvider(int currentTick) {
        this.currentTick = currentTick;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public void tick() {
        tick(1);
    }

    public void tick(int tickAmount) {
        this.currentTick += tickAmount;
    }


}
