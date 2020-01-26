package config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkConfigurationTest {
    @Test
    public void it_should_verify_default_configuration_values() {
        //Given

        //When
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        //Then
        assertEquals(networkConfiguration.getInitialLoggingPeriod(),20D);
        assertEquals(networkConfiguration.getSilentPeriod(), 40D);
        assertEquals(networkConfiguration.getRoundTripDelay(), 0D);
        assertEquals(networkConfiguration.getAttackThreshold(), 0D);
        assertEquals(networkConfiguration.getBloomFilterExpectedInsertions(), 100);
        assertEquals(networkConfiguration.getDefaultFalsePositiveRate(), 0.025D);
    }
}