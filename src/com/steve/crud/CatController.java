package com.steve.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.UUID;

/**
 * The CatController class will be used for control of the main screen with tableview that is linked to the mySQL database.
 */


public class CatController {

    @FXML
    private TextField textFieldCatsName;

    @FXML
    private DatePicker textFieldDOB;

    @FXML
    private TextField textFieldBreed;

    @FXML
    private TextField textFieldWeight;

    @FXML
    private TextField textFieldColor;

    @FXML
    private TableView<Cat> tableViewCats;

    @FXML
    private TableColumn<Cat, String> columnCatsName;

    @FXML
    private TableColumn<Cat, Date> columnDOB;

    @FXML
    private TableColumn<Cat, String> columnBreed;

    @FXML
    private TableColumn<Cat, Integer> columnWeight;

    @FXML
    private TableColumn<Cat, String> columnColor;

    @FXML
    private Button buttonInsert;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    private UserInfo user;


    /**
     * The handleButtonAction method will choose the correct action related to the button selected.
     * @param event The event chosen by button click.
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonInsert) {
            insertRecord();
        }
        if (event.getSource() == buttonUpdate) {
            updateRecord();
        }
        if (event.getSource() == buttonDelete) {
            deleteButton();
        }
    }

    /**
     * Initialize method is used to retrieve the data from the database, disable the text portion of the date picker,
     * and retrieve the user information that was acquired during login from the UserHolder class.
     */
    @FXML
    public void initialize() {
        showCats();
        textFieldDOB.getEditor().setDisable(true);
        // Get the UUID and username stored in singleton.
        user = UserHolder.getInstance().getUser();
    }

    /**
     * The getConnection method will return a connection to the mySql Database.
     * @return The connection to the database.
     */
    private Connection getConnection() {
        Connection conn;
        try {
            // Not using ssl in non production environment.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cats?autoReconnect=true&useSSL=false", "root", "root");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    /**
     * The getCatsList will retrieve the list of cats from the database and store it in an observable list.
     * @return The list of cats.
     */
    private ObservableList<Cat> getCatsList() {
        ObservableList<Cat> catsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM cats_details";
        Statement st;
        ResultSet rs;

        try {
            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);


                Cat cats;
                while (rs.next()) {
                    cats = new Cat(rs.getString("name"), rs.getDate("dob"),
                            rs.getString("breed"), rs.getInt("weight"),
                            rs.getString("color"));
                    catsList.add(cats);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return catsList;
    }

    /**
     * The showCats method will refresh the list of cats in the table view.
     */
    private void showCats() {
        ObservableList<Cat> list = getCatsList();

        columnCatsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        columnBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        columnColor.setCellValueFactory(new PropertyValueFactory<>("color"));

        tableViewCats.setItems(list);
        // Sort list in ascending by cats name.
        tableViewCats.getSortOrder().add(columnCatsName);
    }

    /**
     * The insertRecord will insert a new cat into the database according to what is in the text fields.
     */
    private void insertRecord() {
        // Use single quotes for mySQL statements.
        Date getDateFromPicker = java.sql.Date.valueOf(textFieldDOB.getValue());
        // TODO will use uuid provided by login database once login features are created.
        String query = "INSERT INTO cats_details VALUES ('" + UUID.randomUUID().toString() + "','" +
                textFieldCatsName.getText() + "','" +
                getDateFromPicker + "','" + textFieldBreed.getText() + "','" + textFieldWeight.getText() + "','" +
                textFieldColor.getText() + "')";
        executeQuery(query);
        showCats();
        System.out.println(query);
    }

    /**
     * The updateRecord method will update the record of a cat in the database according to what name is in the name
     * text field.
     */
    private void updateRecord() {
        // TODO allow updating of the cats name too.
        Date getDateFromPicker = java.sql.Date.valueOf(textFieldDOB.getValue());
        String query = "UPDATE cats_details SET dob = '" + getDateFromPicker +
                "', breed = '" + textFieldBreed.getText() + "', weight = '" + textFieldWeight.getText() +
                "', color = '" + textFieldColor.getText() + "' WHERE name = '" + textFieldCatsName.getText() +"'";
        executeQuery(query);
        showCats();
    }

    /**
     * The delete button will delete the cat selected from the database according to what name is in the name text
     * field.
     */
    private void deleteButton() {
        String query = "DELETE FROM cats_details WHERE name = '" + textFieldCatsName.getText() + "'";
        executeQuery(query);
        showCats();
    }

    /**
     * The executeQuery method will execute the query string provided and apply it to the database.
     * @param query The query string to execute.
     */
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            if (conn != null) {
                st = conn.createStatement();
                st.executeUpdate(query);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The handleMouseAction method will update the text fields to show the selected row from the table.
     * @param mouseEvent The row selected by mouse click event.
     */
    @FXML
    public void handleMouseAction(MouseEvent mouseEvent) {
        Cat cat = tableViewCats.getSelectionModel().getSelectedItem();

        textFieldCatsName.setText(cat.getName());
        textFieldDOB.setValue(cat.getDob().toLocalDate());
        textFieldBreed.setText(cat.getBreed());
        textFieldWeight.setText(String.valueOf(cat.getWeight()));
        textFieldColor.setText(cat.getColor());
    }
}
