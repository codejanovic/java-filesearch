package io.github.codejanovic.jfilesearch.filesystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public interface Attributes {
    Path path();
    boolean followLinks();
    BasicFileAttributes load() throws IOException;
}
