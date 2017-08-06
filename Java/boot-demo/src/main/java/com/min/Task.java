package com.min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

    private final Logger logger = LoggerFactory.getLogger(Task.class);

    private int count;

    @Scheduled(cron = "0/60 * * * * ?")
    public void scheduler() {
        logger.debug(">>>>>>>>>>>>> scheduled ... "+(count++)+"<<<<<<<<<<<<<<");
    }

}
