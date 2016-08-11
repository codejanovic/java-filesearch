package io.github.codejanovic.jfilesearch.iterator.path;

import io.github.codejanovic.jfilesearch.filesystem.FileEntry;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Stack;

public class FilePathIterator implements Iterator<Path> {
    private final Stack<Path> stack = new Stack<>();

    public FilePathIterator(final FileEntry fileEntry) {
        stack.push(fileEntry.path());
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Path next() {
        return stack.pop();
    }
}
