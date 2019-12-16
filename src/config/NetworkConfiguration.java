package config;

public interface NetworkConfiguration {
    int INITIAL_LOGGING_INTERVAL = 20;
    int SILENT_PERIOD = 40;
    int ROUND_TRIP_DELAY = 0;
    int ATTACK_THRESHOLD = 0;
    int BLOOM_FILTER_EXPECTED_INSERTIONS = 100;
    double DEFAULT_FALSE_POSITIVE_RATE = 0.025;
}
