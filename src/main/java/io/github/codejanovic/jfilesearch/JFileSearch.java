package io.github.codejanovic.jfilesearch;

import io.github.codejanovic.jfilesearch.providers.FileSearch;
import io.github.codejanovic.jfilesearch.providers.PathSilentSearch;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

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
