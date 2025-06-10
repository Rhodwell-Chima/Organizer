package org.organise.scanner;

public interface Scanner<T> {
    T scan();

    T scan(int depth);
}
