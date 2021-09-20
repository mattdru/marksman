package de.catcode.marksman;

import de.catcode.marksman.markdown.MarkdownSupport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
        ((Stage) mdTextarea.getScene().getWindow()).close();
    }

    @FXML
    protected void save() {
        // Saves the current open document.
        // If the document has not been saved yet the file choser has to be opened
        // If the document has a name overwrite the file without asking
        if (documentModel != null) {
            // Take the text from the textarea. The model content is old at this moment
            writeContentToDisk(documentModel.file, mdTextarea.getText());
        } else {
            final FileChooser saveAsFileChooser = new FileChooser();
            saveAsFileChooser.setTitle("Save As");
            saveAsFileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown", "*.md"));
            final File file = saveAsFileChooser.showSaveDialog(mdTextarea.getScene().getWindow());
            // Take the text from the textarea. The model content is old at this moment
            writeContentToDisk(file, mdTextarea.getText());
        }
    }

    @FXML
    protected void saveAs() {

        // Opens the file choser dialog
        final FileChooser saveAsFileChooser = new FileChooser();
        saveAsFileChooser.setTitle("Save As");
        saveAsFileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown", "*.md"));

        if (documentModel != null) {
            // getParent strips the filename from path
            saveAsFileChooser.setInitialDirectory(documentModel.file.toPath().getParent().toFile());
            saveAsFileChooser.setInitialFileName(documentModel.file.getName());
        }

        final File file = saveAsFileChooser.showSaveDialog(mdTextarea.getScene().getWindow());
        // Take the text from the textarea. The model content is old at this moment
        writeContentToDisk(file, mdTextarea.getText());
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

    private void writeContentToDisk(final File file, final String content) {
        try {
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
