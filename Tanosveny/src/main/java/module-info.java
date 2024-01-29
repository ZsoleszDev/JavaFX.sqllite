module com.example.tanosvenyek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tanosvenyek to javafx.fxml;
    exports com.example.tanosvenyek;
}