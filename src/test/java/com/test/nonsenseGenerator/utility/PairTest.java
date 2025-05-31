package com.test.nonsenseGenerator.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PairTest {

    /**
     * This class tests the functionality of the Pair class, specifically the getFirst method.
     * The getFirst method is expected to return the first element of the pair.
     */

    @Test
    void testGetFirstWithStringPair() {
        // Arrange
        Pair<String, Integer> pair = new Pair<>("Hello", 42);

        // Act
        String result = pair.getFirst();

        // Assert
        assertEquals("Hello", result, "The getFirst method should return the first element of the pair.");
    }

    @Test
    void testGetFirstWithIntegerPair() {
        // Arrange
        Pair<Integer, String> pair = new Pair<>(100, "World");

        // Act
        Integer result = pair.getFirst();

        // Assert
        assertEquals(100, result, "The getFirst method should return the first element of the pair.");
    }

    @Test
    void testGetFirstWithCustomObjectPair() {
        // Arrange
        Pair<Person, String> pair = new Pair<>(new Person("John", 30), "Developer");
        Person expectedPerson = new Person("John", 30);

        // Act
        Person result = pair.getFirst();

        // Assert
        assertEquals(expectedPerson, result, "The getFirst method should return the first element of the pair.");
    }

    // Supporting class for custom object pair test
    static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (age != person.age) return false;
            return name.equals(person.name);
        }
    }
}