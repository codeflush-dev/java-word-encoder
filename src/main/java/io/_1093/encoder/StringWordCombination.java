package io._1093.encoder;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class StringWordCombination extends WordCombinationBase<String> {

    private final String raw;
    private final String separator;

    public StringWordCombination(String raw, String separator) {
        this.raw = Objects.requireNonNull(raw);
        this.separator = Objects.requireNonNull(separator);
    }

    public StringWordCombination(String raw, char separator) {
        this(raw, Character.toString(separator));
    }

    public StringWordCombination(String raw) {
        this(raw, DEFAULT_SEPARATOR);
    }

    @Override
    public Iterator<String> iterator() {
        return new It();
    }

    private class It implements Iterator<String> {

        private int offset;
        private String next;

        private It() {
            this.offset = 0;
            this.next = null;
        }

        private String ensureNext() {
            if (this.next == null && this.offset != -1) {
                final int offset = this.offset;
                final String raw = StringWordCombination.this.raw;
                final String separator = StringWordCombination.this.separator;

                int index = raw.indexOf(separator, offset);

                if (index == -1) {
                    index = raw.length();
                    this.offset = -1;
                } else {
                    this.offset = index + separator.length();
                }

                this.next = StringWordCombination.this.raw.substring(offset, index);
            }

            return this.next;
        }

        @Override
        public boolean hasNext() {
            return ensureNext() != null;
        }

        @Override
        public String next() {
            final String next = ensureNext();
            if (next == null) {
                throw new NoSuchElementException();
            }

            this.next = null;
            return next;
        }
    }
}
