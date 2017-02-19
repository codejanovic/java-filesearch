package io.github.codejanovic.java.filesearch.providers;

import io.github.codejanovic.java.filesearch.SearchProvider;
import io.github.codejanovic.java.filesearch.filesystem.FileEntry;
import io.github.codejanovic.java.filesearch.iterator.path.RecursiveSilentDirectoryIterator;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PathSilentSearch implements SearchProvider<Path> {
    private final int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;
    private final Path path;

    public PathSilentSearch(Path path) {
        this.path = path;
    }

    public Stream<Path> stream(){
        final Spliterator<Path> splitIterator = Spliterators.spliteratorUnknownSize(iterator(), characteristics);
        return StreamSupport.stream(splitIterator, false);
    }

    public Iterator<Path> iterator() {
        return new RecursiveSilentDirectoryIterator(new FileEntry.Smart(path));
    }
}