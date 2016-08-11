package io.github.codejanovic.jfilesearch.providers;

import io.github.codejanovic.jfilesearch.SearchProvider;
import io.github.codejanovic.jfilesearch.iterator.file.RecursiveDirectoryIterator;

import java.io.File;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileSearch implements SearchProvider<File> {
    private final int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;
    private final File file;

    public FileSearch(File file) {
        this.file = file;
    }

    @Override
    public Stream<File> stream() {
        final Spliterator<File> splitIterator = Spliterators.spliteratorUnknownSize(iterator(), characteristics);
        return StreamSupport.stream(splitIterator, false);
    }

    @Override
    public Iterator<File> iterator() {
        return new RecursiveDirectoryIterator(file);
    }
}
