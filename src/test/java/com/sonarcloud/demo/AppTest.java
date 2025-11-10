package com.sonarcloud.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void testAdd() {
        App app = new App();
        assertEquals(5, app.add(2, 3));
    }

    @Test
    void testSubtract() {
        App app = new App();
        assertEquals(1, app.subtract(3, 2));
    }

    @Test
    void testMultiply() {
        App app = new App();
        assertEquals(6, app.multiply(2, 3));
    }

    @Test
    void testDivide() {
        App app = new App();
        assertEquals(2, app.divide(6, 3));
    }
}
