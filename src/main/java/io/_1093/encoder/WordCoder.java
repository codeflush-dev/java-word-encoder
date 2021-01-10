package io._1093.encoder;

import java.util.LinkedList;
import java.util.List;

public final class WordCoder<W> implements Bit64Coder<WordCombination<W>> {

    private final Dictionary<W> dictionary;
    private final long stepSize;
    private final long toggle;

    public WordCoder(Dictionary<W> dictionary) {
        if (dictionary.size() < 2) {
            throw new IllegalArgumentException("the dictionary must contain at least 2 words");
        }

        this.dictionary = dictionary;
        this.stepSize = calculateStepSize(this.dictionary.size());
        this.toggle = (1L << this.stepSize) - 1L;
    }

    private static int calculateStepSize(int valueCount) {
        final int leadingZeros = Integer.numberOfLeadingZeros(valueCount - 1);

        if (leadingZeros == Integer.numberOfLeadingZeros(valueCount)) {
            return Integer.SIZE - leadingZeros - 1;
        } else {
            return Integer.SIZE - leadingZeros;
        }
    }

    @Override
    public WordCombination<W> encode(long value) {
        final List<W> words = new LinkedList<>();
        final Dictionary<W> dictionary = this.dictionary;
        final long toggle = this.toggle;
        final long stepSize = this.stepSize;

        int offset = 0;
        int index;

        do {
            index = offset + (int)(value & toggle);
            value >>>= stepSize;

            if (index >= dictionary.size()) {
                index -= dictionary.size();
            }

            words.add(dictionary.get(index));
            offset = index;
        } while (value != 0L);

        return new SimpleWordCombination<>(words);
    }

    @Override
    public long decode(WordCombination<W> value) {
        final Dictionary<W> dictionary = this.dictionary;
        final long stepSize = this.stepSize;

        long result = 0L;
        int i = 0;
        int index;
        int offset = 0;
        int temp;

        for (W word : value) {
            index = dictionary.find(word);
            temp = index;
            index -= offset;

            if (index < 0) {
                index = dictionary.size() + index;
            }

            result |= ((long) index << (stepSize * i++));
            offset = temp;
        }

        return result;
    }
}
