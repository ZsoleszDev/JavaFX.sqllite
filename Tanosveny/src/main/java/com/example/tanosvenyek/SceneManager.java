package com.example.tanosvenyek;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;


public class SceneManager {
    private final String SUCCESS = "sikeres mentes";
    private final String FAILED = "hiba tortent";
    private final String SAVE = "mentes";
    private final String DELETE = "torles";

    private final VBox root;

    public SceneManager(VBox root) {
        this.root = root;
    }

    private void removePane() {
        root.getChildren().remove(1,2);
    }
    private GridPane generateBaseForm() {
        GridPane form = new GridPane();
        form.setPadding(new Insets(25,25,25,25));
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        return form;
    }

    public MenuBar generateMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Adatbazis");
        Menu rest = new Menu("Rest1");
        Menu parallel = new Menu("Párhuzamos");

        MenuItem restCreate = new MenuItem("Create");
        restCreate.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateRestCreate());
        });
        MenuItem restRead = new MenuItem("Read");
        restRead.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateRestRead());
        });
        MenuItem restUpdate = new MenuItem("Update");
        restUpdate.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateRestUpdate());
        });
        MenuItem restDelete = new MenuItem("Delete");
        restDelete.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateRestDelete());
        });


        MenuItem parallelAlt = new MenuItem("Parhuzamos");
        parallelAlt.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateParallel());
        });

        MenuItem read = new MenuItem("Olvas");
        read.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateRead());
        });
        MenuItem read1 = new MenuItem("Olvas1");
        read1.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateRead2Form());
        });
        MenuItem write = new MenuItem("Ir");
        write.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateInsertForm());
        });
        MenuItem edit = new MenuItem("Modosit");
        edit.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateEditForm());
        });
        MenuItem delete = new MenuItem("Torol");
        delete.setOnAction(actionEvent -> {
            removePane();
            root.getChildren().add(generateDelete());
        });


        menu.getItems().add(read);
        menu.getItems().add(read1);
        menu.getItems().add(write);
        menu.getItems().add(edit);
        menu.getItems().add(delete);

        parallel.getItems().add(parallelAlt);

        rest.getItems().add(restRead);
        rest.getItems().add(restCreate);
        rest.getItems().add(restUpdate);
        rest.getItems().add(restDelete);

        menuBar.getMenus().add(menu);
        menuBar.getMenus().add(parallel);
        menuBar.getMenus().add(rest);

        Menu menuRest = new Menu("RestApi");
        menuRest.getItems().add(new MenuItem("Rest1"));

        return menuBar;
    }

    private GridPane generateRestDelete() {
        GridPane form = generateBaseForm();
        Label msg = new Label();
        msg.setWrapText(true);

        Label idLabel = new Label("id: ");
        TextField id = new TextField();
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                id.setText(newValue.replaceAll("\\D", ""));
            }
        });

        Button sendButton = new Button("felhasznalo torlese");
        sendButton.setOnAction(actionEvent -> {
            try {
                String response = RestAPI.delete("https://gorest.co.in/public/v2/users/" + id.getText());
                System.out.println(response);
                msg.setText("Sikeres torles");
            } catch (IOException ex) {
                ex.printStackTrace();
                msg.setText("Hiba a rest hivas kozben!");
            }
        });

        form.add(idLabel, 0,0);
        form.add(id, 0,1);
        form.add(sendButton, 0, 2);
        form.add(msg, 0,3);

        return form;
    }

    private GridPane generateRestUpdate() {
        GridPane form = generateBaseForm();
        Label msg = new Label();
        msg.setWrapText(true);

        Label idLabel = new Label("id: ");
        Label nameLabel = new Label("name: ");
        Label genderLabel = new Label("gender: ");
        Label emailLabel = new Label("email: ");
        Label activeLabel = new Label("active: ");

        TextField id = new TextField();
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                id.setText(newValue.replaceAll("\\D", ""));
            }
        });
        TextField name = new TextField();
        ComboBox<String> gender = new ComboBox<>(FXCollections.observableArrayList("", "male", "female"));
        TextField email = new TextField();
        CheckBox active = new CheckBox();

        Button sendButton = new Button("felhasznalo frissitese");
        sendButton.setOnAction(actionEvent -> {
            try {
                String response = RestAPI.put("https://gorest.co.in/public/v2/users/" + id.getText(), generateUpdateJson(name, gender, email, active));
                System.out.println(response);
                msg.setText("Sikeres frissites");
            } catch (IOException ex) {
                ex.printStackTrace();
                msg.setText("Hiba a rest hivas kozben!");
            }
        });

        form.add(idLabel, 0,0);
        form.add(id, 0,1);
        form.add(nameLabel, 0,2);
        form.add(name, 0,3);
        form.add(genderLabel, 0,4);
        form.add(gender, 0,5);
        form.add(emailLabel, 0,6);
        form.add(email, 0,7);
        form.add(activeLabel, 0,8);
        form.add(active, 1,8);
        form.add(sendButton, 0, 9);
        form.add(msg, 0,10);

        return form;
    }

    private String generateUpdateJson(TextField name, ComboBox<String> gender, TextField email, CheckBox active) {
        StringBuilder sb = new StringBuilder("{");
        if(name.getText() != null && !name.getText().isBlank()) {
            sb.append("\"name\":\"").append(name.getText()).append("\",");
        }
        if (gender.getValue() != null && !gender.getValue().isBlank()) {
            sb.append("\"gender\":\"").append(gender.getValue()).append("\",");
        }
        if (email.getText() != null && !email.getText().isBlank()) {
            sb.append("\"email\":\"").append(email.getText()).append("\",");
        }
        sb.append("\"status\":\"").append(active.isSelected()? "active":"inactive" ).append("\",");

        sb.append("}");

        System.out.println("generated json string in generateUpdateJson: ");
        System.out.println(sb.toString().replace(",}", "}"));

        return sb.toString().replace(",}", "}");
    }

    private GridPane generateRestRead() {
        GridPane form = generateBaseForm();

        Label msg = new Label();
        msg.setWrapText(true);

        try {
            String response = RestAPI.get("https://gorest.co.in/public/v2/users");
            System.out.println(response);
            msg.setText(response.replace("},{", "},\n{" ));
        } catch (IOException ex) {
            msg.setText("Hiba a rest hiv's kozben!");
        }

        form.add(msg, 0,1);

        return form;
    }

    private GridPane generateRestCreate() {
        GridPane form = generateBaseForm();

        Label msg = new Label();
        msg.setWrapText(true);

        Label nameLabel = new Label("name: ");
        Label genderLabel = new Label("gender: ");
        Label emailLabel = new Label("email: ");
        Label activeLabel = new Label("active: ");

        TextField name = new TextField();
        ComboBox<String> gender = new ComboBox<>(FXCollections.observableArrayList("male", "female"));
        TextField email = new TextField();
        CheckBox active = new CheckBox();

        Button sendButton = new Button("felhasznalo letrehozasa");
        sendButton.setOnAction(actionEvent -> {
            try {
                String response = RestAPI.post("https://gorest.co.in/public/v2/users",
                        String.format("{\"name\":\"%s\", " +
                                "\"gender\":\"%s\", " +
                                "\"email\":\"%s\", " +
                                "\"status\":\"%s\"}", name.getText(), gender.getValue(), email.getText(), active.isSelected() ? "active" : "inactive"));
                System.out.println("sikeres letrehozas");
                msg.setText(response);
            } catch (IOException ex) {
                msg.setText("Hiba a rest hivas kozben!");
            }
        });

        form.add(nameLabel, 0,0);
        form.add(name, 0,1);
        form.add(genderLabel, 0,2);
        form.add(gender, 0,2);
        form.add(emailLabel, 0,3);
        form.add(email, 0,4);
        form.add(activeLabel, 0,5);
        form.add(active, 1,5);
        form.add(sendButton, 0, 6);
        form.add(msg, 0,7);

        return form;
    }

    class Timer1 extends Thread {
        private int val;
        private Label label;
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                Platform.runLater(() -> label.setText("1 masodpercenkent: "+val++));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
        public void setLabel(Label label) {
            this.label = label;
        }
    }
    class Timer2 extends Thread {
        private int val;
        private Label label;
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                Platform.runLater(() -> label.setText("2 masodpercenkent: "+val++));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
        public void setLabel(Label label) {
            this.label = label;
        }
    }

    private GridPane generateParallel() {
        GridPane form = generateBaseForm();

        Label label1 = new Label("1 sec alatt: ");
        Label label2 = new Label("2 sec alatt: ");

        Timer1 t1 = new Timer1();
        t1.setLabel(label1);
        t1.start();
        Timer2 t2 = new Timer2();
        t2.setLabel(label2);
        t2.start();
        form.add(label1, 2,1);
        form.add(label2, 2,2);

        return form;
    }

    public TableView<ReadData> generateRead() {
        return generateRead(null, null, null, null);
    }

    private TableView<ReadData> generateRead(String utnev, String telepules, Boolean vezetes, Integer ido) {
        TableView<ReadData> table = new TableView<>();
        TableColumn<ReadData, String> col1 = new TableColumn<>("Település neve");
        TableColumn<ReadData, String> col2 = new TableColumn<>("Út neve");

        col1.setCellValueFactory(new PropertyValueFactory<>("telnev"));
        col2.setCellValueFactory(new PropertyValueFactory<>("utnev"));

        table.getColumns().add(col1);
        table.getColumns().add(col2);

        ArrayList<ReadData> res = HelloApplication.connector.getReadDataFromDb(utnev, telepules, vezetes, ido);

        if (res != null) {
            for (ReadData data : res) {
                table.getItems().add(data);
            }
        }

        return table;
    }

    private GridPane generateRead2Form() {
        GridPane form = generateBaseForm();

        Label utnevLabel = new Label("Út neve: ");
        Label telnevLabel = new Label("Település neve: ");
        Label vezetesLabel = new Label("Vezetés: ");
        Label allomasLabel = new Label("Állomások: ");
        TextField utnev = new TextField();
        ObservableList<String> options = FXCollections.observableArrayList("Abaliget", "Biharugra", "Csákvár");
        ComboBox<String> telnevek = new ComboBox<>(options);
        CheckBox vezetes = new CheckBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton ketto = new RadioButton("2"); //horror
        RadioButton doc = new RadioButton("4"); // negy
        RadioButton adventure = new RadioButton("8"); //nyolc
        ketto.setUserData("2");
        doc.setUserData("4");
        adventure.setUserData("8");

        Button searchButton = new Button("Keresés");
        searchButton.setOnAction(actionEvent -> {
            System.out.println("search button pressed");
            removePane();
            if (group.getSelectedToggle() != null) {
                root.getChildren().add(generateRead(utnev.getText(), telnevek.getValue(), vezetes.isSelected(), Integer.valueOf(group.getSelectedToggle().getUserData().toString())));
            } else {
                root.getChildren().add(generateRead(utnev.getText(), telnevek.getValue(), vezetes.isSelected(), null));
            }
        });
        ketto.setToggleGroup(group);
        doc.setToggleGroup(group);
        adventure.setToggleGroup(group);


        form.add(utnevLabel, 0,0);
        form.add(utnev, 0,1);
        form.add(telnevLabel, 0,2);
        form.add(telnevek, 0,3);
        form.add(vezetesLabel, 0,4);
        form.add(vezetes, 1,4);
        form.add(allomasLabel, 0,5);
        form.add(ketto, 0,6);
        form.add(doc, 0,7);
        form.add(adventure, 0,8);
        form.add(searchButton, 1,9);


        return form;
    }

    private GridPane generateInsertForm() {
        GridPane form = generateBaseForm();

        Label telnevekLabel = new Label("telepules: ");
        ObservableList<TelepulesData> telnevOptions = FXCollections.observableArrayList(HelloApplication.connector.getAllUniqueTelepules());
        ComboBox<TelepulesData> telnevek = new ComboBox<>(telnevOptions);

        Label utakLabel = new Label("ut: ");
        ObservableList<UtData> utakOptions = FXCollections.observableArrayList(HelloApplication.connector.getAllUniqueUt());
        ComboBox<UtData> utak = new ComboBox<>(utakOptions);

        Label msg = new Label();

        Button insertButton = new Button(SAVE);
        insertButton.setOnAction(actionEvent -> {
            if (HelloApplication.connector.insertLocation(telnevek.getValue(), utak.getValue())) {
                msg.setText(SUCCESS);
            } else {
                msg.setText(FAILED);
            }
        });

        form.add(telnevekLabel, 0, 0);
        form.add(telnevek, 0, 1);
        form.add(utakLabel, 0, 2);
        form.add(utak, 0, 3);
        form.add(insertButton,0,4);
        form.add(msg, 0,5);


        return form;
    }

    private GridPane generateEditForm() {
        GridPane form = generateBaseForm();
        Label parkidLabel = new Label("parkid:");
        Label telnevLabel = new Label("telnev:");
        Label cimLabel = new Label("cim:");
        TextField parkid = new TextField();
        parkid.setEditable(false);
        TextField telnev = new TextField();
        parkid.setUserData("parkid");
        telnev.setUserData("telnev");
        Label telepnevLabel = new Label("Válassz egy települést: ");
        ObservableList<TelepulesData> telnevOptions = FXCollections.observableArrayList(HelloApplication.connector.getAllUniqueTelepules());
        ComboBox<TelepulesData> selectedCinema = new ComboBox<>(telnevOptions);
        selectedCinema.valueProperty().addListener((observableValue, oldTelepulesData, newTelepulesData) -> {
            parkid.setText(newTelepulesData.gettelid().toString());
            telnev.setText(newTelepulesData.getTelnev());
        });

        // force the field to be numeric only
        parkid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                parkid.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Button edit = new Button(SAVE);
        Label msg = new Label();
        edit.setOnAction(actionEvent -> {
            TelepulesData selected = convertFormToTelepulesData(form);
            if (HelloApplication.connector.updateTelepules(selected)) {
                msg.setText(SUCCESS);
            } else {
                msg.setText(FAILED);
            }
        });

        form.add(telnevLabel, 0, 0);
        form.add(selectedCinema, 0, 1);
        form.add(parkidLabel, 0, 2);
        form.add(parkid, 0, 3);
        form.add(telepnevLabel, 0, 4);
        form.add(telnev, 0, 5);

        form.add(edit,0,12);
        form.add(msg, 0,13);

        return form;
    }

    private TelepulesData convertFormToTelepulesData(GridPane form) {
        TelepulesData res = new TelepulesData();

        for (Node act : form.getChildren()) {
            if (act instanceof TextField) {
                TextField casted = (TextField) act;
                if (casted.getUserData().equals("parkid") && !casted.getText().isBlank()) {
                    res.settelid(Integer.valueOf(casted.getText()));
                } else if (casted.getUserData().equals("telnev") && !casted.getText().isBlank()) {
                    res.setTelnev(casted.getText());
                }
            }
        }

        System.out.println("converted Telepules class:");
        System.out.println(res);
        return res;
    }

    private GridPane generateDelete() {
        GridPane form = generateBaseForm();

        Label movieLabel = new Label("utnev: ");
        ObservableList<UtData> utakOptions = FXCollections.observableArrayList(HelloApplication.connector.getAllUniqueUt());
        ComboBox<UtData> utak = new ComboBox<>(utakOptions);
        Button deleteButton = new Button(DELETE);
        deleteButton.setOnAction(actionEvent -> {
            if (utak.getValue() != null && utak.getValue().getutid() != null) {
               HelloApplication.connector.deleteUtById(utak.getValue().getutid());
            }
        });

        form.add(movieLabel, 0, 0);
        form.add(utak, 0, 1);
        form.add(deleteButton, 0, 2);

        return form;
    }
}
