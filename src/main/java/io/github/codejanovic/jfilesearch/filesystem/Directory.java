package io.github.codejanovic.jfilesearch.filesystem;

import io.github.codejanovic.jfilesearch.filesystem.attributes.Cached;
import io.github.codejanovic.jfilesearch.filesystem.attributes.FollowLinks;
import io.github.codejanovic.jfilesearch.filesystem.attributes.NoFollowLinksFallback;
import io.github.codejanovic.jfilesearch.iterator.CloseableIterator;
import io.github.codejanovic.jfilesearch.iterator.path.DirectoryIterator;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Directory extends FileEntry {
    CloseableIterator<Path> iterator();
    DirectoryStream<Path> open() throws IOException;

    final class Smart implements Directory {
        private final FileEntry fileEntry;
        private final boolean test = false;

        public Smart(final FileEntry fileEntry) {
            this.fileEntry = fileEntry;
        }

        public Smart(final Path path) {
            this(
                    new FileEntry.Smart(path,
                            new Cached(
                                    new NoFollowLinksFallback(
                                            new FollowLinks(path)))));
        }

        private Object uuid() {
            try {
                return attributes().load().fileKey();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        public Path path() {
            return fileEntry.path();
        }

        @Override
        public Attributes attributes() {
            return fileEntry.attributes();
        }

        @Override
        public boolean valid() {
            return fileEntry.valid();
        }

        @Override
        public boolean isFile() {
            return fileEntry.isFile();
        }

        @Override
        public boolean isDirectory() {
            return fileEntry.isDirectory();
        }


        @Override
        public DirectoryStream<Path> open() throws IOException {
            return Files.newDirectoryStream(fileEntry.path());
        }

        @Override
        public CloseableIterator<Path> iterator() {
            return new DirectoryIterator(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Directory.Smart aSmart = (Directory.Smart) o;

            return uuid() != null ? uuid().equals(aSmart.uuid()) : false;
        }

        @Override
        public int hashCode() {
            int result = uuid() != null ? uuid().hashCode() : 0;
            result = 31 * result + (test ? 1 : 0);
            return result;
        }
    }
}
