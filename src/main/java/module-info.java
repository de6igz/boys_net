module com.example.boys_net_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.base;
    requires javafx.media;

    opens com.example.boys_net_2 to javafx.fxml;
    exports com.example.boys_net_2;
}