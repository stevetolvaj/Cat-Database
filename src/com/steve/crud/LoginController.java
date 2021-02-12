package com.steve.crud;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

public class LoginController {

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordFieldUserPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnNewUser;

    @FXML
    public void initialize() {

    }

    public void handleButtonAction(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnNewUser) {
            newUser(textFieldUsername.getText(), passwordFieldUserPass.getText());
        }
    }


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

    private void login() {

    }

    private void newUser(String userName, String password) {


        // Retrieve salt from method.
        byte [] salt = salt();
        // Convert to String to store in DB.
        StringBuilder saltStr = new StringBuilder();
        for (byte b :
                salt) {
            saltStr.append(String.format("%02x", b));
        }
        // Create new UUID to use with cat_details table.
        UUID id = UUID.randomUUID();
        String passwordString = hashedPass(password, salt);
        if (passwordString == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING, "New user creation failed!\n You may already have an account or your password was less than 8 characters.");
            alert.show();
        } else {
            String query = "INSERT INTO login_table VALUES ('" + id + "','" + userName + "','" + passwordString + "','" +
                    saltStr.toString() + "')";
            executeQuery(query);
            System.out.println(query);
            Alert alert = new Alert(Alert.AlertType.WARNING, "New user created successfully!");
            alert.show();
        }


    }

    private String hashedPass(String pass, byte[] salt) {
        // Check password length.
        if (pass.length() < 8) {
            return "";
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            messageDigest.update(salt);
            byte [] resultBytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b :
                    resultBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }


    }

    private byte[] salt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
