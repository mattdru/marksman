package de.catcode.marksman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MarksmanApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // https://stackoverflow.com/questions/59182382/plaform-exit-crashes-java
        stage.setOnCloseRequest((event -> Platform.exit()));

        FXMLLoader fxmlLoader = new FXMLLoader(MarksmanApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Marksman Markdown Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
