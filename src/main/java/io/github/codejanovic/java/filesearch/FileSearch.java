package io.github.codejanovic.java.filesearch;

import io.github.codejanovic.java.filesearch.filesystem.Directory;
import io.github.codejanovic.java.filesearch.filesystem.FileEntry;
import io.github.codejanovic.java.filesearch.iterator.file.RecursiveDirectoryIterator;
import io.github.codejanovic.java.filesearch.iterator.path.DirectoryIterator;
import io.github.codejanovic.java.filesearch.iterator.path.RecursiveSilentDirectoryIterator;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface FileSearch<T> {
    default Stream<T> stream() {
        final int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;
        final Spliterator<T> splitIterator = Spliterators.spliteratorUnknownSize(iterator(), characteristics);
        return StreamSupport.stream(splitIterator, false);
    }

    Iterator<T> iterator();

    final class ByPath implements FileSearch<Path> {
        private final Path directory;

        public ByPath(Path directory) {
            this.directory = directory;
        }

        public ByPath(File directory) {
            this.directory = directory.toPath();
        }

        @Override
        public Iterator<Path> iterator() {
            return new DirectoryIterator(new Directory.Smart(new FileEntry.Smart(directory)));
        }
    }

    final class ByPathRecursive implements FileSearch<Path> {
        private final Path directory;

        public ByPathRecursive(Path directory) {
            this.directory = directory;
        }

        public ByPathRecursive(File directory) {
            this.directory = directory.toPath();
        }

        @Override
        public Iterator<Path> iterator() {
            return new RecursiveSilentDirectoryIterator(new Directory.Smart(new FileEntry.Smart(directory)));
        }
    }

    final class ByFile implements FileSearch<File> {
        private final File directory;

        public ByFile(File directory) {
            this.directory = directory;
        }

        public ByFile(Path directory) {
            this.directory = directory.toFile();
        }

        @Override
        public Iterator<File> iterator() {
            return new io.github.codejanovic.java.filesearch.iterator.file.DirectoryIterator(directory);
        }
    }

    final class ByFileRecursive implements FileSearch<File> {
        private final File directory;

        public ByFileRecursive(File directory) {
            this.directory = directory;
        }

        public ByFileRecursive(Path directory) {
            this.directory = directory.toFile();
        }

        @Override
        public Iterator<File> iterator() {
            return new RecursiveDirectoryIterator(directory);
        }
    }
}
