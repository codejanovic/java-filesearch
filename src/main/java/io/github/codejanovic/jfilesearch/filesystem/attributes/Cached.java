package io.github.codejanovic.jfilesearch.filesystem.attributes;

import io.github.codejanovic.jfilesearch.filesystem.Attributes;
import sun.nio.fs.BasicFileAttributesHolder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public final class Cached implements Attributes {
    private final Attributes fallback;

    public Cached(final Attributes attributes) {
        this.fallback = attributes;
    }

    @Override
    public Path path() {
        return fallback.path();
    }

    @Override
    public boolean followLinks() {
        return fallback.followLinks();
    }

    @Override
    public BasicFileAttributes load() throws IOException {
        if ((path() instanceof BasicFileAttributesHolder) && (System.getSecurityManager() == null)) {
            final BasicFileAttributes cached = ((BasicFileAttributesHolder) path()).get();
            if (cached != null && (!followLinks() || !cached.isSymbolicLink())) {
                return cached;
            }
        }
        return fallback.load();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        io.github.codejanovic.jfilesearch.filesystem.attributes.Cached cached = (io.github.codejanovic.jfilesearch.filesystem.attributes.Cached) o;

        return fallback != null ? fallback.equals(cached.fallback) : cached.fallback == null;

    }

    @Override
    public int hashCode() {
        return fallback != null ? fallback.hashCode() : 0;
    }
}
