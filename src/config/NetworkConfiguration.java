package config;

public class NetworkConfiguration {

    private static NetworkConfiguration _instance;
    private double initialLoggingPeriod;
    private double silentPeriod;
    private double roundTripDelay;
    private int attackThreshold;
    private int bloomFilterExpectedInsertions;
    private double defaultFalsePositiveRate;

    private NetworkConfiguration() {
        this.initialLoggingPeriod = 20;
        this.silentPeriod = 40;
        this.roundTripDelay = 0;
        this.attackThreshold = 0;
        this.bloomFilterExpectedInsertions = 100;
        this.defaultFalsePositiveRate = 0.025;
    }

    public static synchronized NetworkConfiguration getInstance() {
        if (_instance == null) {
            _instance = new NetworkConfiguration();
        }

        return _instance;
    }

    public double getInitialLoggingPeriod() {
        return initialLoggingPeriod;
    }

    public void setInitialLoggingPeriod(double initialLoggingPeriod) {
        this.initialLoggingPeriod = initialLoggingPeriod;
    }

    public double getSilentPeriod() {
        return silentPeriod;
    }

    public void setSilentPeriod(double silentPeriod) {
        this.silentPeriod = silentPeriod;
    }

    public double getRoundTripDelay() {
        return roundTripDelay;
    }

    public void setRoundTripDelay(double roundTripDelay) {
        this.roundTripDelay = roundTripDelay;
    }

    public int getAttackThreshold() {
        return attackThreshold;
    }

    public void setAttackThreshold(int attackThreshold) {
        this.attackThreshold = attackThreshold;
    }

    public int getBloomFilterExpectedInsertions() {
        return bloomFilterExpectedInsertions;
    }

    public void setBloomFilterExpectedInsertions(int bloomFilterExpectedInsertions) {
        this.bloomFilterExpectedInsertions = bloomFilterExpectedInsertions;
    }

    public double getDefaultFalsePositiveRate() {
        return defaultFalsePositiveRate;
    }

    public void setDefaultFalsePositiveRate(double defaultFalsePositiveRate) {
        this.defaultFalsePositiveRate = defaultFalsePositiveRate;
    }
}
