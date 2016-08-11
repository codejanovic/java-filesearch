package io.github.codejanovic.jfilesearch.iterator.file;

import java.io.File;
import java.util.Iterator;
import java.util.Stack;

public class FileIterator implements Iterator<File> {
    private final Stack<File> stack = new Stack<>();

    public FileIterator(final File file) {
        stack.push(file);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public File next() {
        return stack.pop();
    }
}
