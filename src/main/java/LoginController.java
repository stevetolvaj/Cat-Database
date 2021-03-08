import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * The LoginController class is used to control the login screen events.
 */

public class LoginController {

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordFieldUserPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnNewUser;

    private UserInfo user;

    /**
     * The handleButtonAction method runs the login procedure or create new user procedure.
     * @param mouseEvent The event chosen by the user.
     */
    public void handleButtonAction(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnNewUser) {
            newUser();
        } else if (mouseEvent.getSource() == btnLogin) {
            login();
        }
    }

    /**
     * The login method will compare the hashed password to the stored passwords.
     */
    private void login() {

        String userName = textFieldUsername.getText();
        String userPassword = passwordFieldUserPass.getText();
        String dbSalt;
        String dbPassword = null;

        Connection conn = DbConnection.getConnection();

        String queryPassword = "SELECT * FROM login_table WHERE user_name = '" + userName + "'";
        Statement st;
        ResultSet rs = null;
        byte[] returnedSalt;    // The salt in the DB.
        String returnedPassword = null; // The password in the DB.
        boolean userNameFound = false;

        try {
            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(queryPassword);
            }

            if (rs != null && rs.next()) {
                dbPassword = rs.getString("user_password");
                dbSalt = rs.getString("pass_salt");
                // Set up user information for use in cat controller class.
                user = new UserInfo(UUID.fromString(rs.getString("user_uuid")), rs.getString("user_name"));
                // Hold user in singleton.
                UserHolder.getInstance().setUser(user);
                // Check if username was actually found in DB.
                // If either field is null there was nothing to return from missing username.
                if (dbPassword != null) {
                    returnedSalt = PasswordHasher.hexToByteArray(dbSalt);
                    returnedPassword = PasswordHasher.hashedPass(userPassword, returnedSalt);
                    userNameFound = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection error: ");
            e.printStackTrace();
        }
        // TODO Redesign alerts.
        // Testing Login Password match from database.
        Alert alert;
        if (returnedPassword != null && returnedPassword.equals(dbPassword)) {
            openCatPage();
        } else if (!userNameFound){
            alert = new Alert(Alert.AlertType.WARNING, "User name is not registered in the system!");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.WARNING, "Incorrect password. Try again!");
            alert.show();
        }

    }

    /**
     * The openCatPage method will open the main database table screen and close the current login screen.
     */
    private void openCatPage() {
        Parent part = null;
        try {
            part = FXMLLoader.load(getClass().getResource("cats.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(part);
            stage.setScene(scene);
            stage.getIcons().add(new Image("/cat_icon_138789.png"));
            stage.setTitle("The Cat Dating Database");
            stage.show();

            // Hide the current login screen after main screen opens.
            textFieldUsername.getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * The newUser method will store the username, uuid, hashed password, and solt in
     * the database.
     */
    private void newUser() {

        String userName = textFieldUsername.getText();
        String password = passwordFieldUserPass.getText();
        Alert alert;

        // Check password length if too short.
        if (password.length() < 8) {
            alert = new Alert(Alert.AlertType.WARNING, "Please use password of at least 8 characters.");
            alert.show();
            return;
        }

        // Retrieve salt from method.
        byte [] salt = PasswordHasher.salt();
        // Convert to String to store in DB.
        String saltStr = PasswordHasher.byteArrayToHexString(salt);
        // Create new UUID to use with cat_details table.
        UUID id = UUID.randomUUID();
        String passwordString = PasswordHasher.hashedPass(password, salt);
        if (passwordString.equals("")) {
            alert = new Alert(Alert.AlertType.WARNING, "New user creation failed!\n You may already have an account.");
        } else {
            String query = "INSERT INTO login_table VALUES ('" + id + "','" + userName + "','" + passwordString + "','" +
                    saltStr + "')";

            DbConnection.executeQuery(query);
            System.out.println(query);
            alert = new Alert(Alert.AlertType.WARNING, "New user created successfully!");
        }
        alert.show();
    }

}
