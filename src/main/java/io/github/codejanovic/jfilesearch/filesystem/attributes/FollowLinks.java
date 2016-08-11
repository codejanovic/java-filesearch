package io.github.codejanovic.jfilesearch.filesystem.attributes;

import io.github.codejanovic.jfilesearch.filesystem.Attributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public final class FollowLinks implements Attributes {
    private final Path path;

    public FollowLinks(final Path path) {
        this.path = path;
    }

    @Override
    public Path path() {
        return path;
    }

    @Override
    public boolean followLinks() {
        return true;
    }

    @Override
    public BasicFileAttributes load() throws IOException {
        return Files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowLinks that = (FollowLinks) o;

        return path != null ? path.equals(that.path) : that.path == null;

    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
