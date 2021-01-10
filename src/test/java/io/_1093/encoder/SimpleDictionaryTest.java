package io._1093.encoder;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleDictionaryTest {

    @Test
    public void duplicatesThrowException() {
        assertThrows(IllegalArgumentException.class, () -> SimpleDictionary.of("a", "b", "a"));
    }

    @Test
    public void createByArray() {
        final Dictionary<String> dict = SimpleDictionary.of("0", "1", "2", "3");

        assertEquals(4, dict.size());
        assertEquals("2", dict.get(2));
        assertEquals(1, dict.find("1"));
    }

    @Test
    public void createByList() {
        final Dictionary<String> dict = SimpleDictionary.of(List.of("123", "456", "789"));

        assertEquals(3, dict.size());
        assertEquals("456", dict.get(1));
        assertEquals(2, dict.find("789"));
    }

    @Test
    public void throwsExceptionOnUnknownWord() {
        final Dictionary<String> dict = SimpleDictionary.of("a");
        assertThrows(IllegalArgumentException.class, () -> dict.find("b"));
    }

    @Test
    public void returnsEmptyInstanceOnEmptyList() {
        assertSame(SimpleDictionary.empty(), SimpleDictionary.of(List.of()));
    }
}