package io.github.codejanovic.java.filesearch.filesystem.attributes;

import io.github.codejanovic.java.filesearch.filesystem.Attributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public final class NoFollowLinksFallback implements Attributes {
    private final Attributes attributes;
    private final Attributes noFollowAttributes;

    public NoFollowLinksFallback(final Attributes attributes, final Attributes noFollowAttributes) {
        this.attributes = attributes;
        this.noFollowAttributes = noFollowAttributes;
    }

    public NoFollowLinksFallback(Attributes attributes) {
        this(attributes, new NoFollowLinks(attributes.path()));
    }

    @Override
    public Path path() {
        return attributes.path();
    }

    @Override
    public boolean followLinks() {
        return false;
    }

    @Override
    public BasicFileAttributes load() throws IOException {
        try {
            return attributes.load();
        } catch (IOException e) {
            return noFollowAttributes.load();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoFollowLinksFallback that = (NoFollowLinksFallback) o;

        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) return false;
        return noFollowAttributes != null ? noFollowAttributes.equals(that.noFollowAttributes) : that.noFollowAttributes == null;

    }

    @Override
    public int hashCode() {
        int result = attributes != null ? attributes.hashCode() : 0;
        result = 31 * result + (noFollowAttributes != null ? noFollowAttributes.hashCode() : 0);
        return result;
    }
}
