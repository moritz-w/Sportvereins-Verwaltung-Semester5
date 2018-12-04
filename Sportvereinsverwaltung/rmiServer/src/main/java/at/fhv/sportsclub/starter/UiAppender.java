package at.fhv.sportsclub.starter;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class UiAppender extends AppenderSkeleton {

    private final UiNotify notifier;

    public UiAppender(UiNotify notify){
        this.notifier = notify;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        notifier.update(loggingEvent.getLevel().toString() + " " + loggingEvent.getMessage().toString());
    }

    @Override
    public void close() {}

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
