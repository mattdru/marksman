package de.catcode.marksman;

import java.io.File;
import java.util.Objects;

/**
 * @author Matthias Drummer
 */
public class DocumentModel {

    public File file;
    public String markdownContent;

    public DocumentModel() {
    }

    public DocumentModel(File file, String markdownContent) {
        this.file = file;
        this.markdownContent = markdownContent;
    }

    public boolean hasChanged(final String markdownValue) {
        return Objects.equals(markdownContent, markdownValue);
    }
}
