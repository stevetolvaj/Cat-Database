package com.steve.crud;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
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

    /**
     * The handleButtonAction method runs the login procedure or create new user procedure.
     * @param mouseEvent The event chosen by the user.
     */
    public void handleButtonAction(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnNewUser) {
            newUser(textFieldUsername.getText(), passwordFieldUserPass.getText());
        } else if (mouseEvent.getSource() == btnLogin) {
            login();
        }
    }

    /**
     * The getConnection method will retrieve a connection to the mySQL database.
     * @return Connection to mySQL database.
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
     * The login method will compare the hashed password to the stored passwords.
     */
    private void login() {

        String userName = textFieldUsername.getText();
        String userPassword = passwordFieldUserPass.getText();
        String dbSalt = null;
        String dbPassword;
        Connection conn = getConnection();
        String query = "SELECT * FROM login_table WHERE user_name = '" + userName + "'";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            /*
            ----------------------------------------------------------------------------------------------------
            // TODO rs.getString is a one time procedure. Try to assign it to dbPass and dbSalt in one line.
            /*
            --------------------------------------------------------------------------------------------------
             */
            while (rs.next())
            dbPassword = rs.getString("user_password");
            dbSalt = rs.getString("pass_salt");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        byte[] returnedSalt = hexToByteArray(dbSalt);
        String returnedPassword = hashedPass(userPassword, returnedSalt);

        if (returnedPassword.equals(returnedPassword)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Login is good.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect user name of password. Try again!");
            alert.show();
        }


    }

    /**
     * The newUser method will store the username, uuid, hashed password, and solt in
     * the database.
     * @param userName The username chosen.
     * @param password The password chosen.
     */
    private void newUser(String userName, String password) {


        // Retrieve salt from method.
        byte [] salt = salt();
        // Convert to String to store in DB.
        String saltStr = byteArrayToHexString(salt);
        // Create new UUID to use with cat_details table.
        UUID id = UUID.randomUUID();
        String passwordString = hashedPass(password, salt);
        if (passwordString == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING, "New user creation failed!\n You may already have an account or your password was less than 8 characters.");
            alert.show();
        } else {
            String query = "INSERT INTO login_table VALUES ('" + id + "','" + userName + "','" + passwordString + "','" +
                    saltStr + "')";
            executeQuery(query);
            System.out.println(query);
            Alert alert = new Alert(Alert.AlertType.WARNING, "New user created successfully!");
            alert.show();
        }


    }

    /**
     * The hashedPass method will be provide the hashed password using PBKDF2WithHmacSHA1
     * hasing through java.security library.
     * @param pass The password to be hashed.
     * @param salt The salt created.
     * @return
     */
    private String hashedPass(String pass, byte[] salt) {
        // Check password length.
        if (pass.length() < 8) {
            return "";
        }

        try {
            KeySpec keySpec = new PBEKeySpec(pass.toCharArray(), salt, 200000, 512);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
            StringBuilder sb = new StringBuilder();

//            for (byte b :
//                    hash) {
//                sb.append(String.format("%02x", b));
//            }

            return byteArrayToHexString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
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

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (byte b :
                bytes) {
            str.append(String.format("%02x", b));
        }
        return str.toString();
    }

    private byte[] hexToByteArray(String hexStr) {
        byte[] b = new BigInteger(hexStr,16).toByteArray();
        return b;
    }


}
