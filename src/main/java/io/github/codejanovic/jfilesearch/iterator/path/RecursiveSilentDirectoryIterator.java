package io.github.codejanovic.jfilesearch.iterator.path;

import io.github.codejanovic.jfilesearch.iterator.RepeatableIterator;
import io.github.codejanovic.jfilesearch.filesystem.Directory;
import io.github.codejanovic.jfilesearch.filesystem.FileEntry;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Stack;

public class RecursiveSilentDirectoryIterator implements Iterator<Path> {
    private final Stack<RepeatableIterator<Path>> iteratorStack = new Stack<>();
    private RepeatableIterator<Path> iterator;

    public RecursiveSilentDirectoryIterator(final FileEntry start) {
        this.iterator = start.isDirectory()?
                new RepeatableIterator.Closeable(
                        new DirectoryIterator(
                                new Directory.Smart(start))):
                new RepeatableIterator.Smart(
                        new FilePathIterator(start));
    }

    @Override
    public boolean hasNext() {
        if (givenCurrentPath())
            if (loopIsDetected())
                skipDirectory();
            else
                openDirectory();

        if (givenNoNextEntry())
            if (whenThereAreQueuedPaths())
                thenDequeueThosePathsUntilThereIsANextEntryOrNoPathsLeft();

        return iterator.hasNext();
    }

    private void skipDirectory() {
    }

    private boolean loopIsDetected() {
        final FileEntry current = new FileEntry.Smart(iterator.current());
        if (!current.isDirectory())
            return false;

        for (RepeatableIterator<Path> ancestor: iteratorStack)
            if (new FileEntry.Smart(ancestor.current()).equals(current))
                return true;

        return false;
    }

    @Override
    public Path next() {
        return iterator.next();
    }

    private boolean givenCurrentPath() {
        return iterator.hasCurrent();
    }

    private void thenDequeueThosePathsUntilThereIsANextEntryOrNoPathsLeft() {
        while (givenNoNextEntry()) {
            closeDirectorySilently(iterator);
            if (whenThereAreQueuedPaths())
                iterator = iteratorStack.pop();
            else
                return;
        }
    }

    private boolean whenThereAreQueuedPaths() {
        return !iteratorStack.isEmpty();
    }

    private boolean givenNoNextEntry() {
        return !iterator.hasNext();
    }

    private void openDirectory() {
        final FileEntry current = new FileEntry.Smart(iterator.current());
        if (!current.isDirectory())
            return;
        openDirectoryIfItIsNotEmpty(current);
    }

    private void openDirectoryIfItIsNotEmpty(FileEntry current) {
        final RepeatableIterator<Path> currentDirectoryIterator = new RepeatableIterator.Closeable(new DirectoryIterator(new Directory.Smart(current)));
        if (currentDirectoryIterator.hasNext()) {
            iteratorStack.push(iterator);
            iterator = currentDirectoryIterator;
        } else {
            closeDirectorySilently(currentDirectoryIterator);
        }
    }

    private void closeDirectorySilently(final Iterator<Path> iterator){
        try {
            if (iterator instanceof Closeable)
                ((Closeable) iterator).close();
        } catch (IOException e) {
        }
    }
}