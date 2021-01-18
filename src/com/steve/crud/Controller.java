package com.steve.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;


public class Controller {

    @FXML
    private TextField textFieldCatsName;

    @FXML
    private TextField textFieldDOB;

    @FXML
    private TextField textFieldBreed;

    @FXML
    private TextField textFieldWeight;

    @FXML
    private TextField textFieldColor;

    @FXML
    private TableView<Cats> tableViewCats;

    @FXML
    private TableColumn<Cats, String> columnCatsName;

    @FXML
    private TableColumn<Cats, Date> columnDOB;

    @FXML
    private TableColumn<Cats, String> columnBreed;

    @FXML
    private TableColumn<Cats, Integer> columnWeight;

    @FXML
    private TableColumn<Cats, String> columnColor;

    @FXML
    private Button buttonInsert;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    @FXML
    public void handleButtonAction(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        showCats();
        // TODO
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cats", "root", "root");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Cats> getCatsList() {
        ObservableList<Cats> catsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM cats";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Cats cats;
            while (rs.next()) {
                cats = new Cats(rs.getString("name"), rs.getDate("dob"),
                        rs.getString("breed"), rs.getInt("weight"),
                        rs.getString("color"));
                catsList.add(cats);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Testing connection.
//        for (int i = 0; i < 5; i++) {
//            System.out.println(catsList.get(i).getName());
//        }
        return catsList;
    }

    public void showCats() {
        ObservableList<Cats> list = getCatsList();

        columnCatsName.setCellValueFactory(new PropertyValueFactory<Cats, String>("name"));
        columnDOB.setCellValueFactory(new PropertyValueFactory<Cats, Date>("dob"));
        columnBreed.setCellValueFactory(new PropertyValueFactory<Cats, String>("breed"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<Cats, Integer>("weight"));
        columnColor.setCellValueFactory(new PropertyValueFactory<Cats, String>("color"));

        tableViewCats.setItems(list);
    }

}
