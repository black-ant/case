package com.gang.study.devtools.source.demo.restart;

import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * A single file that may be served from a {@link ClassLoader}. Can be used to represent
 * files that have been added, modified or deleted since the original JAR was created.
 *
 * @author Phillip Webb
 * @see ClassLoaderFileRepository
 * @since 1.3.0
 */
public class ClassLoaderFile implements Serializable {

    private static final long serialVersionUID = 1;

    private final Kind kind;

    private final byte[] contents;

    private final long lastModified;

    /**
     * Create a new {@link ClassLoaderFile} instance.
     *
     * @param kind     the kind of file
     * @param contents the file contents
     */
    public ClassLoaderFile(Kind kind, byte[] contents) {
        this(kind, System.currentTimeMillis(), contents);
    }

    /**
     * Create a new {@link ClassLoaderFile} instance.
     *
     * @param kind         the kind of file
     * @param lastModified the last modified time
     * @param contents     the file contents
     */
    public ClassLoaderFile(Kind kind, long lastModified, byte[] contents) {
        Assert.notNull(kind, "Kind must not be null");
        Assert.isTrue((kind != Kind.DELETED) ? contents != null : contents == null,
                () -> "Contents must " + ((kind != Kind.DELETED) ? "not " : "") + "be null");
        this.kind = kind;
        this.lastModified = lastModified;
        this.contents = contents;
    }

    /**
     * Return the file {@link Kind} (added, modified, deleted).
     *
     * @return the kind
     */
    public Kind getKind() {
        return this.kind;
    }

    /**
     * Return the time that the file was last modified.
     *
     * @return the last modified time
     */
    public long getLastModified() {
        return this.lastModified;
    }

    /**
     * Return the contents of the file as a byte array or {@code null} if
     * {@link #getKind()} is {@link Kind#DELETED}.
     *
     * @return the contents or {@code null}
     */
    public byte[] getContents() {
        return this.contents;
    }

    /**
     * The kinds of class load files.
     */
    public enum Kind {

        /**
         * The file has been added since the original JAR was created.
         */
        ADDED,

        /**
         * The file has been modified since the original JAR was created.
         */
        MODIFIED,

        /**
         * The file has been deleted since the original JAR was created.
         */
        DELETED

    }

}
