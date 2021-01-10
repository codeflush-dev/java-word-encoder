package io._1093.encoder;

import java.util.Objects;
import java.util.function.Function;

public interface WordCombination<W> extends Iterable<W> {

    char DEFAULT_SEPARATOR = '-';
    int UNKNOWN_SIZE = -1;

    default int size() {
        return UNKNOWN_SIZE;
    }
    String toString(Function<? super W, String> toStringFunction, char separator);
    String toString(Function<? super W, String> toStringFunction, String separator);
    default String toString(char separator) {
        return toString(Objects::toString, separator);
    }
    default String toString(String separator) {
        return toString(Objects::toString, separator);
    }
}
