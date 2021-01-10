package io._1093.encoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordCoderTest {

    private final WordCoder<String> coder = new WordCoder<>(SimpleDictionary.of("0", "1"));

    @Test
    public void zero() {
        testValue(0L, "0");
    }

    @Test
    public void one() {
        testValue(1L, "1");
    }

    @Test
    public void ten() {
        testValue(10L, "0-1-1-0");
    }

    @Test
    public void maxValue() {
        testValue(Long.MAX_VALUE, "1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1");
    }

    @Test
    public void minValue() {
        testValue(Long.MIN_VALUE, "0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-1");
    }

    @Test
    public void minusOne() {
        testValue(-1L, "1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0-1-0");
    }

    @Test
    public void decodeInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> this.coder.decode(new StringWordCombination("0-1-a")));
    }

    @Test
    public void createWithInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> new WordCoder<>(SimpleDictionary.empty()));
    }

    @Test
    public void dictionarySizeIsNotAPowerOf2() {
        final WordCoder<String> coder = new WordCoder<>(SimpleDictionary.of("0", "1", "2"));
        final long input = 1234L;
        final WordCombination<String> expected = new StringWordCombination("0-1-1-1-2-2-0-1-1-1-2", '-');

        assertEquals(expected, coder.encode(input));
        assertEquals(input, coder.decode(expected));
    }

    private void testValue(long input, String expectedOutput) {
        final WordCombination<String> output = this.coder.encode(input);

        assertEquals(new StringWordCombination(expectedOutput), output);
        assertEquals(input, this.coder.decode(output));
    }
}