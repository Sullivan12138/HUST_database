package sample;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import org.controlsfx.control.textfield.TextFields;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;

import java.awt.event.ActionEvent;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class caigouController {

    @FXML private JFXComboBox<String> wareName;
    @FXML private JFXComboBox<String> wareNumber;
    @FXML private JFXComboBox storagePlace;
    @FXML private JFXTextField factory;
    @FXML private JFXTextField amount;
    @FXML private JFXButton registerButton;
    @FXML private JFXButton clearButton;
    @FXML private JFXButton exitButton;
    @FXML private JFXButton seeLackListButton;
    @FXML private JFXButton seeImportButton;

    private Set<String> autoCompletions;
    SuggestionProvider<String> provider;

    @FXML public void initialize() {
        /*set up connection */
        storagePlace.getItems().addAll("货仓1", "货仓2", "货仓3", "货仓4", "货仓5", "货仓6", "货仓7", "货仓8");
        linkMySql con = new linkMySql();
        Connection myCon = con.caigouconnect();

        showList();
        /*fetch data*/
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        System.out.println("preparing lack message\n");
        String str = "";
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT DISTINCT name from lack");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("name").trim();
                str1 = str1 + " ";
                str = str + str1;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        if(str == "") JOptionPane.showMessageDialog(null, "最近暂无缺货情况！\n", "登录成功！",JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null, "最近有以下几种商品缺货，您需要进货了！\n" + str,"登录成功！",JOptionPane.INFORMATION_MESSAGE);
        /*close the connection*/
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        wareNumber.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                wareNumberChanged(wareNumber.getItems().get((int)newValue));
            }
        });
        wareName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                wareNameChanged(wareName.getItems().get((int)newValue));
            }
        });
    }
    void showList()
    {
        linkMySql con = new linkMySql();
        Connection myCon = con.caigouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        LinkedList<String> searchResult = new LinkedList<>();
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT * from goodinfo");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("wareNumber").trim();
                String str2 = rs.getString("wareName").trim();
                wareNumber.getItems().add(str1);
                wareName.getItems().add(str2);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    void wareNumberChanged(String newChoice)
    {
        if (wareName.getValue() != null)
        {
            if (isMatchNumber(newChoice,wareName.getValue())) return;
        }
        System.out.println("bb");
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareName from goodinfo WHERE wareNumber = ?");
            pStatement.setString(1,newChoice);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("wareName").trim();
                wareName.setValue(str1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void wareNameChanged(String newChoice)
    {
        if (wareNumber.getValue() != null)
        {
            if (isMatchName(newChoice, wareNumber.getValue())) return;
        }
        System.out.println("aa");
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareNumber from goodinfo WHERE wareName = ?");
            pStatement.setString(1,newChoice);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str1 = rs.getString("wareNumber").trim();
                wareNumber.setValue(str1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    boolean isMatchName(String str1, String str2)
    {
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return false;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareNumber from goodinfo WHERE wareName = ?");
            pStatement.setString(1,str1);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str3 = rs.getString("wareNumber").trim();
                if(str2.equals(str3)) return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean isMatchNumber(String str1, String str2)
    {
        linkMySql con = new linkMySql();
        Connection myCon = con.xiaoshouconnect();
        if (myCon == null) {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return false;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = (PreparedStatement) myCon.prepareStatement("SELECT wareName from goodinfo WHERE wareNumber = ?");
            pStatement.setString(1,str1);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            while (rs.next()) {
                String str3 = rs.getString("wareName").trim();
                if(str2.equals(str3)) return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @FXML public void on_register_clicked()
    {
        if (wareName.getValue().equals("")) {
            JOptionPane.showConfirmDialog(null,"货品名不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (wareNumber.getValue().equals("")) {
            JOptionPane.showConfirmDialog(null,"货品号不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (storagePlace.getValue().equals("")) {
            JOptionPane.showConfirmDialog(null,"存放地点不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (factory.getText().equals("")) {
            JOptionPane.showConfirmDialog(null,"生产厂家不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (amount.getText().equals("")) {
            JOptionPane.showConfirmDialog(null,"货品数量不能为空！","出错！",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int isRegister = JOptionPane.showConfirmDialog(null, "确定要登记吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(isRegister == 0) {
            String huowuName = wareName.getValue().toString();
            String huowuNumber = wareNumber.getValue().toString();
            String SCCJ = factory.getText();
            int shuliang = Integer.parseInt(amount.getText());
            String CFDD = storagePlace.getValue().toString();
            Date dNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String RKSJ = sdf.format(dNow);
            System.out.println(RKSJ);
            linkMySql con = new linkMySql();
            Connection myCon = con.caigouconnect();
            PreparedStatement pStatement = null;
            ResultSet rs = null;
            System.out.println("preparing to read\n");
            try {
                String insertImport = "INSERT INTO import VALUES(?,?,?,?,?,?,?)";
                System.out.println(insertImport);
                pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                pStatement.setString(1, huowuName);
                pStatement.setString(2, huowuNumber);
                pStatement.setString(3, CFDD);
                pStatement.setString(4, RKSJ);
                pStatement.setInt(5, shuliang);
                pStatement.setString(6, SCCJ);
                pStatement.setInt(7, 0);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                int isSuc = pStatement.executeUpdate();
                if(isSuc > 0)
                {
                    JOptionPane.showMessageDialog(null, "登记成功！请等待审核人员审核。", "提示", 1);
                    on_clear_clicked();

                }else
                {
                    JOptionPane.showMessageDialog(null, "由于某种原因，登记失败了！", "出错！", 0);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            /*close the connection*/
            try
            {
                myCon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML public void on_clear_clicked()
    {
        wareName.setValue(null);
        wareNumber.setValue(null);
        storagePlace.setValue(null);
        factory.clear();
        amount.clear();
    }
    @FXML public void on_exit_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要退出吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            JOptionPane.showMessageDialog(null, "欢迎下次使用！");
            Stage tempStage = (Stage)exitButton.getScene().getWindow();
            tempStage.close();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            try {
                myScene.setRoot((Parent) loader.load());
                Stage newStage = new Stage();
                newStage.setTitle("登录界面");
                newStage.setScene(myScene);
                newStage.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else return;
    }
    @FXML public void on_seeLackList_clicked()
    {
        Stage tempStage = (Stage)seeLackListButton.getScene().getWindow();
        tempStage.close();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("quehuo.fxml")
        );
        AnchorPane root = new AnchorPane();
        Scene myScene = new Scene(root);
        try {
            myScene.setRoot((Parent)loader.load());
            Stage newStage = new Stage();
            newStage.setTitle("缺货单");
            newStage.setScene(myScene);
            newStage.show();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML public void on_seeImport_clicked()
    {
        Stage tempStage = (Stage)seeImportButton.getScene().getWindow();
        tempStage.close();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("jinhuo.fxml")
        );
        AnchorPane root = new AnchorPane();
        Scene myScene = new Scene(root);
        try {
            myScene.setRoot((Parent)loader.load());
            Stage newStage = new Stage();
            newStage.setTitle("进货单");
            newStage.setScene(myScene);
            newStage.show();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
