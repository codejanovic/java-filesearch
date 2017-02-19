package io.github.codejanovic.java.filesearch.filesystem;

import io.github.codejanovic.java.filesearch.filesystem.attributes.Cached;
import io.github.codejanovic.java.filesearch.filesystem.attributes.FollowLinks;
import io.github.codejanovic.java.filesearch.filesystem.attributes.NoFollowLinksFallback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface FileEntry {
    Path path();
    Attributes attributes();
    Object uniqueKey();
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
        public Object uniqueKey() {
            try {
                return attributes().load().fileKey();
            } catch (IOException e) {
                return null;
            }
        }

        private boolean isSame(FileEntry file) {
            try {
                return Files.isSameFile(path(), file.path());
            } catch (IOException e) {
                return false;
            }
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

            FileEntry aSmart = (FileEntry) o;

            if ((uniqueKey() == null) || (aSmart.uniqueKey() == null))
                return isSame(aSmart);
            else
                return uniqueKey() != null ? uniqueKey().equals(aSmart.uniqueKey()) : false;
        }

        @Override
        public int hashCode() {
            return uniqueKey() != null ? uniqueKey().hashCode() : path.hashCode();
        }
    }
}
