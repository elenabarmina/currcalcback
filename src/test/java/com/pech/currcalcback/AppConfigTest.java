package com.pech.currcalcback;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class AppConfigTest {

    @Test
    void getProperty() {
        AppConfig appConfig = new AppConfig();

        String testProperty = AppConfig.getProperty("test.property");
        assertEquals(testProperty, "test");
    }
}