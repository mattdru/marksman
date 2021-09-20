package de.catcode.marksman.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Objects;

/**
 * @author Matthias Drummer
 */
public class MarkdownSupport {

    private static MarkdownSupport INSTANCE;

    private final Parser parser;
    private final HtmlRenderer htmlRenderer;

    public static MarkdownSupport getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new MarkdownSupport();
        }
        return INSTANCE;
    }

    private MarkdownSupport() {
        parser = Parser.builder().build();
        htmlRenderer = HtmlRenderer.builder().build();
    }

    public String renderMarkdown(final String markdown) {
        final Node document = parser.parse(markdown);
        return htmlRenderer.render(document);
    }
}
