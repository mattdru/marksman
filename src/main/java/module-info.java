module de.catcode.marksman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.commonmark;

    requires org.controlsfx.controls;

    opens de.catcode.marksman to javafx.fxml;
    exports de.catcode.marksman;
}