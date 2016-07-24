package io.github.codejanovic.jfilesearch.iterator;

import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public interface RepeatableIterator<T> extends Iterator<T> {
    boolean hasCurrent();
    T current();

    final class Smart<T> implements RepeatableIterator<T> {
        private final Stack<T> stack = new Stack<>();
        private final Iterator<T> iterator ;

        public Smart(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasCurrent() {
            return !stack.isEmpty();
        }

        @Override
        public T current() {
            return stack.peek();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            if (!stack.isEmpty())
                stack.pop();
            return stack.push(iterator.next());
        }
    }

    final class Closeable<T> implements java.io.Closeable, RepeatableIterator<T> {
        private final RepeatableIterator<T> repeatableIterator;
        private final CloseableIterator<T> closeableIterator;

        public Closeable(final CloseableIterator<T> closeableIterator, final RepeatableIterator<T> repeatableIterator) {
            this.closeableIterator = closeableIterator;
            this.repeatableIterator = repeatableIterator;
        }

        public Closeable(final CloseableIterator<T> closeableIterator) {
            this(closeableIterator, new Smart<>(closeableIterator));
        }

        @Override
        public boolean hasCurrent() {
            return repeatableIterator.hasCurrent();
        }

        @Override
        public T current() {
            return repeatableIterator.current();
        }

        @Override
        public boolean hasNext() {
            return repeatableIterator.hasNext();
        }

        @Override
        public T next() {
            return repeatableIterator.next();
        }

        @Override
        public void close() throws IOException {
            closeableIterator.close();
        }
    }
}
