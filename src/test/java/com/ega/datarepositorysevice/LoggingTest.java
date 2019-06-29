package com.ega.datarepositorysevice;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
public class LoggingTest {
    private Logger logger = LoggerFactory.getLogger(LoggingTest.class);

    @Test
    public void testDebug() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
            }
}
