package util;

public class TickProvider {

    private double currentTick;

    public TickProvider() {
        this(0D);
    }

    public TickProvider(double currentTick) {
        this.currentTick = currentTick;
    }

    public double getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(double currentTick) {
        this.currentTick = currentTick;
    }

    public void tick() {
        tick(1D);
    }

    public void tick(double tickAmount) {
        this.currentTick += tickAmount;
    }


}
