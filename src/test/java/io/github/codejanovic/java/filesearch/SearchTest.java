package io.github.codejanovic.java.filesearch;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.codejanovic.java.filesearch.Search.search;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest {
    private Path givenSearchDirectory;
    private int filesFound;

    @Test
    public void testRecursiveSearchByPathForDirectory() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingRecursivelyByPath();
        thenFilesFoundAre(34);
    }

    @Test
    public void testSearchByPathForDirectory() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingByPath();
        thenFilesFoundAre(4);
    }

    @Test
    public void testRecursiveSearchByPathForFile() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure/1/11.txt");
        whenSearchingRecursivelyByPath();
        thenFilesFoundAre(1);
    }

    @Test
    public void testRecursiveSearchByFileForDirectory() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingByFileRecursively();
        thenFilesFoundAre(34);
    }

    @Test
    public void testSearchByFileForDirectory() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingByFile();
        thenFilesFoundAre(4);
    }

    @Test
    public void testRecursiveSearchByFileForFile() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure/1/11.txt");
        whenSearchingByFileRecursively();
        thenFilesFoundAre(1);
    }

    private void thenFilesFoundAre(int amount) {
        assertThat(filesFound).isEqualTo(amount);
    }

    private void whenSearchingByFileRecursively() {
        final Counter filesFound = new Counter();
        search().directory(givenSearchDirectory).recursively().byFile().stream().forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void whenSearchingByFile() {
        final Counter filesFound = new Counter();
        search().directory(givenSearchDirectory).byFile().stream().forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void whenSearchingRecursivelyByPath() {
        final Counter filesFound = new Counter();
        search().directory(givenSearchDirectory).recursively().byPath().stream().forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void whenSearchingByPath() {
        final Counter filesFound = new Counter();
        search().directory(givenSearchDirectory).byPath().stream().forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void givenSearchDirectory(final String path) {
        givenSearchDirectory = Paths.get(path);
    }
}