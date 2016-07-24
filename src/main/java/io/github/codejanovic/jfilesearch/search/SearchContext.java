package io.github.codejanovic.jfilesearch.search;
import io.github.codejanovic.jfilesearch.filesystem.FileSystemEntry;

import java.util.Optional;

public interface SearchContext {
    FileSystemEntry file();
    FileSystemEntry directory();
    Optional<Throwable> error();
}
