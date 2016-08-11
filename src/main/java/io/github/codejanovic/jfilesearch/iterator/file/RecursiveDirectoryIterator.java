package io.github.codejanovic.jfilesearch.iterator.file;

import io.github.codejanovic.jfilesearch.iterator.RepeatableIterator;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public class RecursiveDirectoryIterator implements Iterator<File>{
    private final Stack<RepeatableIterator<File>> iteratorStack = new Stack<>();
    private RepeatableIterator<File> iterator;

    public RecursiveDirectoryIterator(final File start) {
        this.iterator = start.isDirectory()?
                new RepeatableIterator.Smart<>(new DirectoryIterator(start))
                : new RepeatableIterator.Smart(new FileIterator(start));
    }

    @Override
    public boolean hasNext() {
        if (givenCurrentFile())
            whenCurrentFileIsADirectoryDoOpenIt();

        if (givenNoNextEntry())
            if (whenThereAreQueuedFiles())
                thenDequeueThoseFilesUntilThereIsANextEntryOrNoFilesLeft();

        return iterator.hasNext();
    }

    @Override
    public File next() {
        return iterator.next();
    }

    private boolean givenCurrentFile() {
        return iterator.hasCurrent();
    }

    private void thenDequeueThoseFilesUntilThereIsANextEntryOrNoFilesLeft() {
        while (givenNoNextEntry()) {
            closeDirectorySilently(iterator);
            if (whenThereAreQueuedFiles())
                iterator = iteratorStack.pop();
            else
                return;
        }
    }

    private boolean whenThereAreQueuedFiles() {
        return !iteratorStack.isEmpty();
    }

    private boolean givenNoNextEntry() {
        return !iterator.hasNext();
    }

    private void whenCurrentFileIsADirectoryDoOpenIt() {
        final File current = iterator.current();
        if (!current.isDirectory())
            return;
        openDirectoryIfItIsNotEmpty(current);
    }

    private void openDirectoryIfItIsNotEmpty(File current) {
        final RepeatableIterator<File> currentDirectoryIterator = new RepeatableIterator.Smart<>(new DirectoryIterator(current));
        if (currentDirectoryIterator.hasNext()) {
            iteratorStack.push(iterator);
            iterator = currentDirectoryIterator;
        } else {
            closeDirectorySilently(currentDirectoryIterator);
        }
    }

    private void closeDirectorySilently(final Iterator<File> iterator){
        try {
            if (iterator instanceof Closeable)
                ((Closeable) iterator).close();
        } catch (IOException e) {
        }
    }
}
