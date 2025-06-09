module fxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires textToSpeechApi;
    requires freetts;
    requires org.xerial.sqlitejdbc;

    opens Fxml to javafx.fxml;
    opens Controllers to javafx.fxml;
    exports program to javafx.graphics;
    exports Controllers to javafx.fxml;
    exports DictionaryCommandLine to javafx.fxml;
}
