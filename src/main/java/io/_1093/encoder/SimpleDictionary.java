package io._1093.encoder;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public final class SimpleDictionary<W> implements Dictionary<W>, Serializable {

    private static final SimpleDictionary<Object> EMPTY = new SimpleDictionary<>(new Object[0], Collections.emptyMap());

    private final W[] values;
    private final Map<W, Integer> reverseValues;

    private SimpleDictionary(W[] values, Map<W, Integer> reverseValues) {
        this.values = values;
        this.reverseValues = reverseValues;
    }

    private SimpleDictionary(W[] values) {
        this(values, createReverse(values));
    }

    public static <W> SimpleDictionary<W> empty() {
        return (SimpleDictionary<W>) EMPTY;
    }

    public static <W> SimpleDictionary<W> of(W first, W... more) {
        final W[] copy = (W[]) Array.newInstance(first.getClass(), more.length + 1);
        copy[0] = first;

        System.arraycopy(more, 0, copy, 1, more.length);

        return new SimpleDictionary<>(copy);
    }

    public static <W> SimpleDictionary<W> of(List<W> words) {
        final int size = words.size();
        if (size < 1) {
            return empty();
        }


        final W[] copy = (W[]) Array.newInstance(words.get(0).getClass(), size);
        int i = 0;

        for (W word : words) {
            copy[i++] = word;
        }

        return new SimpleDictionary<>(copy);
    }

    private static <W> Map<W, Integer> createReverse(W[] array) {
        final Map<W, Integer> reverseValues = new HashMap<>(array.length);

        for (int i = 0; i < array.length; i++) {
            putReverse(array[i], i, reverseValues);
        }

        return reverseValues;
    }

    private static <W> void putReverse(W value, int index, Map<W, Integer> reverseValues) {
        final Integer prev = reverseValues.put(value, index);
        if (prev != null) {
            throw new IllegalArgumentException("the dictionary contains at least one duplicate (found " + prev + " at " + index + " and " + prev + ")");
        }
    }

    @Override
    public int size() {
        return this.values.length;
    }

    @Override
    public W get(int index) {
        return this.values[index];
    }

    @Override
    public int find(W value) {
        final int index = this.reverseValues.getOrDefault(value, -1);
        if (index == -1) {
            throw new IllegalArgumentException("the requested value is not part of this dictionary");
        }

        return index;
    }
}
