package com.ega.datarepositorysevice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LoggingTest {
    private static final Logger LOGGER = LogManager.getLogger(LoggingTest.class);

    @Test
    public void testDebug() {

        LOGGER.debug("This is a debug message");
        LOGGER.info("This is an info message");
        LOGGER.error("This is an error message");
    }
}
