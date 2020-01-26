package network.logdos.strategy;

import util.TickProvider;

public interface TimeBasedLoggingStrategy {

    TickProvider getTickProvider();

    void setTickProvider(TickProvider tickProvider);
}
