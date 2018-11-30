import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    TableView table;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputEmail;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    TextField inputExport;
    @FXML
    Button exportButton;

    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";


    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Szabó", "Gyula", "gy.gmail.com"),
            new Person("Dikk", "Tesó", "d.gmail.com"),
            new Person("Kiss", "Mukk", "m.gmail.com"));

    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
            data.add(new Person(inputLastName.getText(), inputFirstName.getText(), email));
            inputLastName.clear();
            inputFirstName.clear();
            inputEmail.clear();
        }
    }

    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMaxWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> event) {
                        ((Person) event.getTableView().getItems().get(event.getTablePosition().getRow())).setLastName(event.getNewValue());
                    }
                }
        );

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMaxWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> event) {
                        ((Person) event.getTableView().getItems().get(event.getTablePosition().getRow())).setFirstName(event.getNewValue());
                    }
                }
        );

        TableColumn emailCol = new TableColumn("E-mail");
        emailCol.setMaxWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> event) {
                        ((Person) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmail(event.getNewValue());
                    }
                }
        );

        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol);
        table.setItems(data);
    }


    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        //nodeItemA.setExpanded(true);

        Node contactNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (selectedMenu != null) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableData();
        setMenuData();
        PdfGeneration pdfCreation = new PdfGeneration();
        pdfCreation.pdfGeneration("fajlnev", "Tartalom");
    }
}
