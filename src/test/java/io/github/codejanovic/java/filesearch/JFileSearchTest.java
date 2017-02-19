package io.github.codejanovic.java.filesearch;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JFileSearchTest {
    private Path givenSearchDirectory;
    private int filesFound;

    @Test
    public void testSilentPathSearchFindsAsMuchFilesAsDefaultJavaSearch() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingWithPathSilentStream();
        thenFoundFilesAreSameAsFromDefaultJavaSearchExceptStartingDirectory();
    }

    @Test
    public void testSilentPathStreamFindsOnlyOneFileWhenFileIsAFile() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure/1/11.txt");
        whenSearchingWithPathSilentStream();
        thenFoundFilesAreSameAsFromDefaultJavaSearch();
    }

    @Test
    public void testFileStreamFindsAsMuchFilesAsDefaultJavaSearch() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingWithFileStream();
        thenFoundFilesAreSameAsFromDefaultJavaSearchExceptStartingDirectory();
    }

    @Test
    public void testFileStreamFindsOnlyOneFileWhenFileIsAFile() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure/1/11.txt");
        whenSearchingWithFileStream();
        thenFoundFilesAreSameAsFromDefaultJavaSearch();
    }

    private void whenSearchingWithFileStream() {
        final Counter filesFound = new Counter();
        new JFileSearch.Default().fileStream(givenSearchDirectory.toFile()).stream().forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void thenFoundFilesAreSameAsFromDefaultJavaSearchExceptStartingDirectory() {
        assertThat(new JavaFileSearch().howMuchFilesAreFoundIgnoringExceptions(givenSearchDirectory)).isEqualTo(filesFound +1);
    }

    private void thenFoundFilesAreSameAsFromDefaultJavaSearch() {
        assertThat(new JavaFileSearch().howMuchFilesAreFoundIgnoringExceptions(givenSearchDirectory)).isEqualTo(filesFound);
    }

    private void whenSearchingWithPathSilentStream() {
        final Counter filesFound = new Counter();
        new JFileSearch.Default().silentPathStream(givenSearchDirectory).stream().forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void givenSearchDirectory(final String path) {
        givenSearchDirectory = Paths.get(path);
    }
}