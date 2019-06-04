package sample;

import java.awt.event.ActionEvent;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class loginController implements Initializable {
    @FXML private JFXButton confirmButton;
    @FXML private JFXButton clearButton;
    @FXML private JFXTextField account;
    @FXML private JFXPasswordField password;
    @FXML private JFXComboBox loginList;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginList.getItems().addAll("管理人员","采购人员","销售人员");
        loginList.setValue("管理人员");
    }




    @FXML
    public void on_confirm_clicked()
    {
        System.out.print("The login button is clicked");
        System.out.print(account.getText());
        System.out.print(password.getText());

        /*If username or password input is null*/
        if(account.getText().equals(""))
        {

            JOptionPane.showMessageDialog(null, "用户名不能为空！");
            return;
        }
        if(password.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "密码不能为空！");
            return;
        }
        if (loginList.getValue().equals("管理人员"))
            guanliLogin();
        else if (loginList.getValue().equals("采购人员"))
            caigouLogin();
        else xiaoshouLogin();

        return;
    }

    @FXML
    public void on_clear_clicked()
    {
        account.clear();
        password.clear();
        return;
    }
    void guanliLogin()
    {
        if (account.getText().equals("taowei")) {
            if (password.getText().equals("000001"))
            {
                Stage tempStage = (Stage)confirmButton.getScene().getWindow();
                tempStage.close();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("guanli.fxml")
                );
                AnchorPane root = new AnchorPane();
                Scene myScene = new Scene(root);
                try {
                    myScene.setRoot((Parent) loader.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("管理人员操作界面");
                    newStage.setScene(myScene);
                    newStage.show();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "密码错误！");
                return;
            }
        } else JOptionPane.showMessageDialog(null, "用户名错误！");
        return;
    }
    void caigouLogin()
    {
        if (account.getText().equals("taowei2")) {
            if (password.getText().equals("000002"))
            {
                Stage tempStage = (Stage)confirmButton.getScene().getWindow();
                tempStage.close();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("caigou.fxml")
                );
                AnchorPane root = new AnchorPane();
                Scene myScene = new Scene(root);
                try {
                    myScene.setRoot((Parent) loader.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("采购人员操作界面");
                    newStage.setScene(myScene);
                    newStage.show();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "密码错误！");
                return;
            }
        } else JOptionPane.showMessageDialog(null, "用户名错误！");
        return;
    }
    void xiaoshouLogin()
    {
        if (account.getText().equals("taowei3")) {
            if (password.getText().equals("000003"))
            {
                Stage tempStage = (Stage)confirmButton.getScene().getWindow();
                tempStage.close();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("xiaoshou.fxml")
                );
                AnchorPane root = new AnchorPane();
                Scene myScene = new Scene(root);
                try {
                    myScene.setRoot((Parent) loader.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("销售人员操作界面");
                    newStage.setScene(myScene);
                    newStage.show();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "密码错误！");
                return;
            }
        } else JOptionPane.showMessageDialog(null, "用户名错误！");
        return;
    }
}