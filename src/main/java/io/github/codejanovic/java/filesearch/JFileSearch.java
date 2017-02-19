package io.github.codejanovic.java.filesearch;

import io.github.codejanovic.java.filesearch.providers.PathSilentSearch;
import io.github.codejanovic.java.filesearch.providers.FileSearch;

import java.io.File;
import java.nio.file.Path;

public interface JFileSearch {
    SearchProvider silentPathStream(final Path path);
    SearchProvider fileStream(final File file);

    class Default implements JFileSearch {

        @Override
        public SearchProvider silentPathStream(Path path) {
            return new PathSilentSearch(path);
        }

        @Override
        public SearchProvider fileStream(File file) {
            return new FileSearch(file);
        }
    }
}
