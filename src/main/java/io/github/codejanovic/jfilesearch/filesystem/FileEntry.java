package io.github.codejanovic.jfilesearch.filesystem;

import io.github.codejanovic.jfilesearch.filesystem.attributes.Cached;
import io.github.codejanovic.jfilesearch.filesystem.attributes.FollowLinks;
import io.github.codejanovic.jfilesearch.filesystem.attributes.NoFollowLinksFallback;

import java.io.IOException;
import java.nio.file.Path;

public interface FileEntry {
    Path path();
    Attributes attributes();
    boolean valid();
    boolean isFile();
    boolean isDirectory();

    final class Smart implements FileEntry {
        private final Path path;
        private final Attributes attributes;

        public Smart(final Path path, final Attributes attributes) {
            this.path = path;
            this.attributes = attributes;
        }

        public Smart(final Path path) {
            this(path, new Cached(new NoFollowLinksFallback(new FollowLinks(path))));
        }

        @Override
        public Path path() {
            return path;
        }

        @Override
        public Attributes attributes() {
            return attributes;
        }

        @Override
        public boolean valid() {
            try {
                attributes.load();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public boolean isFile() {
            try {
                return attributes.load().isRegularFile();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public boolean isDirectory() {
            try {
                return attributes.load().isDirectory();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Smart aSmart = (Smart) o;

            return path != null ? path.equals(aSmart.path) : aSmart.path == null;

        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }
}
