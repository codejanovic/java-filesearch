package io.github.codejanovic.java.filesearch;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class Search {
    private Optional<File> directory;
    private boolean recursively;

    public Search() {
        directory = Optional.empty();
        recursively = false;
    }

    public static Search search() {
        return new Search();
    }

    public Search directory(final File file) {
        directory = Optional.of(file);
        return this;
    }

    public Search directory(final Path path) {
        directory = Optional.of(path.toFile());
        return this;
    }

    public Search directory(final String path) {
        directory = Optional.of(new File(path));
        return this;
    }

    public Search recursively() {
        recursively = true;
        return this;
    }

    public Search notRecursively() {
        recursively = false;
        return this;
    }

    public FileSearch<File> byFile() {
        return recursively ? new FileSearch.ByFileRecursive(directory.get()): new FileSearch.ByFile(directory.get());
    }

    public FileSearch<Path> byPath() {
        return recursively ? new FileSearch.ByPathRecursive(directory.get()): new FileSearch.ByPath(directory.get());
    }
}
