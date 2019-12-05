package util;

public class TickProvider {

    private static TickProvider instance;

    private int currentTick;

    private TickProvider() {
        currentTick = 0;
    }

    public static synchronized TickProvider getInstance() {
        if (instance == null) {
            instance = new TickProvider();
        }
        return instance;
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
