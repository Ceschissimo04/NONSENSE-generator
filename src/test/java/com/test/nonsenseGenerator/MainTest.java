package com.test.nonsenseGenerator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class for the Main application.
 * This class contains unit tests for verifying the behavior of the main method in the Main class.
 */
@SpringBootTest
public class MainTest {

    @Autowired
    private Main main;

    /**
     * Test to ensure that the Spring application context is loaded successfully when the main method is executed.
     */
    @Test
    public void whenMainMethodIsExecuted_thenApplicationContextLoadsSuccessfully() {
        String[] args = {};
        Main.main(args);
        assertThat(main).isNotNull();
    }
}