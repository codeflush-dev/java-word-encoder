package io._1093.encoder;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public abstract class WordCombinationBase<W> implements WordCombination<W> {

    @Override
    public String toString(Function<? super W, String> toStringFunction, char separator) {
        return toString(toStringFunction, Character.toString(separator));
    }

    @Override
    public String toString(Function<? super W, String> toStringFunction, String separator) {
        final StringBuilder sb = new StringBuilder();
        int i = 0;

        for (W word : this) {
            if (i++ > 0) {
                sb.append(separator);
            }

            sb.append(toStringFunction.apply(word));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(DEFAULT_SEPARATOR);
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (W word : this) {
            result = 31 * result + Objects.hashCode(word);
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof WordCombination<?>)) {
            return false;
        }

        final WordCombination<?> other = (WordCombination<?>) obj;
        final int mySize = size();
        final int otherSize = other.size();

        if (mySize != -1 && otherSize != -1 && mySize != otherSize) {
            return false;
        }

        final Iterator<W> myIt = iterator();
        final Iterator<?> otherIt = other.iterator();
        boolean myHasNext;
        boolean match;

        do {
            myHasNext = myIt.hasNext();
            match = myHasNext == otherIt.hasNext();

            if (match && myHasNext) {
                match = Objects.equals(myIt.next(), otherIt.next());
            }
        } while (match && myHasNext);

        return match;
    }
}
