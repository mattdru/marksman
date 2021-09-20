package de.catcode.marksman;

import de.catcode.marksman.markdown.MarkdownSupport;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextArea mdTextarea;

    @FXML
    private WebView htmlWebView;

    private DocumentModel documentModel;

    @FXML
    protected void mdTextAreaTextChanged() {
        setMarkdownToWebView(mdTextarea.getText());
    }

    @FXML
    protected void newDocument() {
        documentModel = new DocumentModel();
        mdTextarea.setText(null);
        // load content with empty string. Null does not clear up the view
        htmlWebView.getEngine().loadContent("");
    }

    @FXML
    protected void openDocument() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Show Markdown Files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown", "*.md"));
        final File chosenFile = fileChooser.showOpenDialog(mdTextarea.getScene().getWindow());
        if (chosenFile != null) {
            try {
                final List<String> lines = Files.readAllLines(chosenFile.toPath(), StandardCharsets.UTF_8);
                documentModel = new DocumentModel(chosenFile, String.join("\n", lines));
                mdTextarea.setText(documentModel.markdownContent);
                setMarkdownToWebView(mdTextarea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void exitApplication() {
        // https://learning.oreilly.com/library/view/mastering-javafx-10/9781788293822/c9d51657-9bcc-40ac-977b-4720e8a81b3c.xhtml
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        mdTextarea.textProperty().bind(mdTextareaProperty);
//        System.out.println("Initialize");
//        mdTextarea.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                System.out.println("Changed");
//            }
//        });
    }

    private void setMarkdownToWebView(final String markdown) {
        final String html = MarkdownSupport.getInstance().renderMarkdown(markdown);
        htmlWebView.getEngine().loadContent(html);
    }
}
