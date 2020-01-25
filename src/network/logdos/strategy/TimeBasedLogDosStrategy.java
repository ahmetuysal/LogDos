package network.logdos.strategy;

import util.TickProvider;

public interface TimeBasedLogDosStrategy {

    TickProvider getTickProvider();

    void setTickProvider(TickProvider tickProvider);
}
