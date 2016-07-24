package io.github.codejanovic.jfilesearch.iterator.path;

import io.github.codejanovic.jfilesearch.filesystem.FileSystemEntry;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Stack;

public class FilePathIterator implements Iterator<Path> {
    private final Stack<Path> stack = new Stack<>();

    public FilePathIterator(final FileSystemEntry fileSystemEntry) {
        stack.push(fileSystemEntry.path());
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
