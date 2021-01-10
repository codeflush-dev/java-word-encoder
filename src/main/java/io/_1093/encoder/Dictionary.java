package io._1093.encoder;

public interface Dictionary<W> {

    int size();
    W get(int index);
    int find(W word);
}
