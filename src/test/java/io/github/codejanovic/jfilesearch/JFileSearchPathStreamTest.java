package io.github.codejanovic.jfilesearch;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JFileSearchPathStreamTest {
    private Path givenSearchDirectory;
    private int filesFound;

    @Test
    public void testJFileSearchSilentStreamFindsAsMuchFilesAsDefaultJavaSearch() throws Exception {
        givenSearchDirectory("src/test/resources/searchstructure");
        whenSearchingWithJFileSearchSilentStream();
        thenFoundFilesAreSameAsFromDefaultJavaSearchExceptStartingDirectory();
    }

    private void thenFoundFilesAreSameAsFromDefaultJavaSearchExceptStartingDirectory() {
        assertThat(new JavaFileSearch().howMuchFilesAreFoundIgnoringExceptions(givenSearchDirectory)).isEqualTo(filesFound +1);
    }

    private void whenSearchingWithJFileSearchSilentStream() {
        final Counter filesFound = new Counter();
        new JFileSearch().searchDirectorySilentlyStream(givenSearchDirectory).forEach(path -> filesFound.increment());
        this.filesFound = filesFound.value();
    }

    private void givenSearchDirectory(final String path) {
        givenSearchDirectory = Paths.get(path);
    }
}