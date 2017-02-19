package io.github.codejanovic.java.filesearch.iterator.path;

import io.github.codejanovic.java.filesearch.filesystem.Directory;
import io.github.codejanovic.java.filesearch.iterator.CloseableIterator;
import io.github.codejanovic.java.filesearch.iterator.empty.EmptyIterator;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Stack;

public class DirectoryIterator implements CloseableIterator<Path> {
    private final Stack<DirectoryStream<Path>> stream = new Stack<>();
    private final Stack<Iterator<Path>> iterator = new Stack<>();
    private final Directory directory;

    public DirectoryIterator(final Directory directory) {
        this.directory = directory;
    }

    @Override
    public void close() throws IOException {
        if (stream.empty())
            return;
        stream.pop().close();
    }

    private void openDirectoryIfNecessary() {
        if (!iterator.empty())
            return;
        try {
            iterator.push(stream.push(directory.open()).iterator());
        } catch (IOException e) {
            iterator.push(new EmptyIterator<>());
        }
    }

    @Override
    public boolean hasNext() {
        openDirectoryIfNecessary();
        return iterator.peek().hasNext();
    }

    @Override
    public Path next() {
        openDirectoryIfNecessary();
        return iterator.peek().next();
    }
}
