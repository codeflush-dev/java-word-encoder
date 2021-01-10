package io._1093.encoder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class SimpleWordCombination<W> extends WordCombinationBase<W> {

    private final Collection<W> words;

    public SimpleWordCombination(Collection<W> words) {
        this.words = words;
    }

    @Override
    public int size() {
        return this.words.size();
    }

    @Override
    public Iterator<W> iterator() {
        return this.words.iterator();
    }

    @Override
    public void forEach(Consumer<? super W> action) {
        this.words.forEach(action);
    }

    @Override
    public Spliterator<W> spliterator() {
        return this.words.spliterator();
    }
}
