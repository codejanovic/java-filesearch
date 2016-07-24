package io.github.codejanovic.jfilesearch;

import io.github.codejanovic.jfilesearch.filesystem.FileSystemEntry;
import io.github.codejanovic.jfilesearch.iterator.path.SilentRecursivePathIterator;

import java.nio.file.Path;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JFileSearch {
    public Stream<Path> searchDirectorySilentlyStream(final Path path) {
        final int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;
        final Spliterator<Path> spliterator = Spliterators.spliteratorUnknownSize(new SilentRecursivePathIterator(new FileSystemEntry.Smart(path)), characteristics);
        return StreamSupport.stream(spliterator, false);
    }
}
