package lk.ijse.dep.pos.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.pos.db.HibernateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lk.ijse.dep.pos.db.HibernateUtil.*;

/**

 */
public class MainFormController implements Initializable {

    public ProgressIndicator pgb;
    public ImageView imgPurchaseOrder;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgCustomer;
    @FXML
    private ImageView imgItem;
    @FXML
    private ImageView imgOrder;
    @FXML
    private ImageView imgViewOrders;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblDescription;

    /**
     * Initializes the lk.ijse.dep.pos.controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        pgb.setVisible(false);
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgCustomer":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view customers");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or lk.ijse.dep.pos.view items");
                    break;
                case "imgOrder":
                    lblMenu.setText("Place Orders");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "imgViewOrders":
                    lblMenu.setText("Search Orders");
                    lblDescription.setText("Click if you want to search orders");
                    break;
                case "imgPurchaseOrder":
                    lblMenu.setText("Manage Package");
                    lblDescription.setText("Click if you want to Manage Package");
                    break;

            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "imgCustomer":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/ManageCustomerForm.fxml"));
                    break;
                case "imgItem":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/ManageItemForm.fxml"));
                    break;
                case "imgOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/PlaceOrderForm.fxml"));
                    break;
                case "imgViewOrders":
                    root =  FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/ViewReportForm.fxml"));
                    break;
                case "imgPurchaseOrder":
                    root =  FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/pos/view/ManagePackForm.fxml"));
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }

    public void btnRestore_OnAction(ActionEvent actionEvent) {

        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Let's restore the backup");
            fileChooser.getExtensionFilters().
                    add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
            File file = fileChooser.showOpenDialog(this.root.getScene().getWindow());
            if (file != null) {

                String[] commands;
                if (HibernateUtil.getPassword().length() > 0) {
                    commands = new String[]{"mysql", "-h", getHost(), "-u", getUsername(),
                            "-p" + getPassword(), "--port", getPort(), getDatabase(), "-e", "source " + file.getAbsolutePath()};
                } else {
                    commands = new String[]{"mysql", "-h", getHost(), "-u", getUsername(), "--port", getPort(),
                            getDatabase(), "-e", "source " + file.getAbsolutePath()};
                }

                // Long running task == Restore
                this.root.getScene().setCursor(Cursor.WAIT);
                this.pgb.setVisible(true);

                Task task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Process process = Runtime.getRuntime().exec(commands);
                        int exitCode = process.waitFor();
                        if (exitCode != 0) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                            br.lines().forEach(System.out::println);
                            br.close();
                            throw new RuntimeException("Wadea Kachal");
                        } else {
                            return null;
                        }
                    }
                };

                task.setOnSucceeded(event -> {
                    this.pgb.setVisible(false);
                    this.root.getScene().setCursor(Cursor.DEFAULT);
                    new Alert(Alert.AlertType.INFORMATION, "Restore process has been successfully done").show();
                });
                task.setOnFailed(event -> {
                    this.pgb.setVisible(false);
                    this.root.getScene().setCursor(Cursor.DEFAULT);
                    new Alert(Alert.AlertType.ERROR, "Failed to restore the backup. Contact DEPPO").show();
                });

                new Thread(task).start();
            }
        } catch (Exception ex) {
            Logger.getLogger("lk.ijse.dep.pos.controller.MainFormController").log(Level.SEVERE, null, ex);
        }

    }

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save the DB Backup");
            fileChooser.getExtensionFilters().
                    add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
            File file = fileChooser.showSaveDialog(this.root.getScene().getWindow());
            if (file != null) {

                // Now, we have to backup the DB...
                // Long running task == We have to backup
                this.root.getScene().setCursor(Cursor.WAIT);
                this.pgb.setVisible(true);

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String[] commands;
                        if (getPassword().length() > 0) {
                            commands = new String[]{"mysqldump", "-h", getHost(), "-u", getUsername(),
                                    "-p" + getPassword(), "--port", getPort(), getDatabase(), "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                        } else {
                            commands = new String[]{"mysqldump", "-h", getHost(), "-u", getUsername(), "--port", getPort(),
                                    getDatabase(), "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                        }

                        Process process = Runtime.getRuntime().exec(commands);
                        int exitCode = process.waitFor();
                        if (exitCode != 0) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                            br.lines().forEach(System.out::println);
                            br.close();
                            throw new RuntimeException("Wadea Kachal");
                        } else {
                            return null;
                        }
                    }
                };

                task.setOnSucceeded(event -> {
                    this.pgb.setVisible(false);
                    this.root.getScene().setCursor(Cursor.DEFAULT);
                    new Alert(Alert.AlertType.INFORMATION, "Backup process has been done successfully").show();
                });

                task.setOnFailed(event -> {
                    this.pgb.setVisible(false);
                    this.root.getScene().setCursor(Cursor.DEFAULT);
                    new Alert(Alert.AlertType.ERROR, "Failed to back up. Contact DEEPO").show();
                });

                new Thread(task).start();
            }
        } catch (Exception ex) {
            Logger.getLogger("lk.ijse.dep.pos.controller.MainFormController").log(Level.SEVERE, null, ex);
        }
    }
}
