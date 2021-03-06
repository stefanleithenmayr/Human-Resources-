package mainPackage;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import loginPackage.DBConnection;
import sun.security.util.Password;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeDataController implements Initializable {
    @FXML
    JFXTextField userName, skill1, skill2, skill3, skill4,realNameField,ageField,ortField,streetField,telefonNumberField,eMailField;
    @FXML
    JFXTextArea descriptionField;
    @FXML
    JFXSlider sliderSkill1,sliderSkill2,sliderSkill3,sliderSkill4;
    @FXML
    JFXButton saveAllButton, addSkillButton, substractSkillButton;
    @FXML
    ImageView imageViewAddSkill,imageViewSubstractSkill;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    JFXCheckBox passwordCheckBox;
    @FXML
    private void substractSkill(ActionEvent event) throws SQLException{
        addSkillButton.setVisible(true);
        imageViewAddSkill.setVisible(true);
        if (!skill4.getText().equals("")){
            skill4.setText("");
            sliderSkill4.setValue(0);
            skill4.setVisible(false);
            sliderSkill4.setVisible(false);
        }
        else if (!skill3.getText().equals("")){
            skill3.setText("");
            sliderSkill3.setValue(0);
            skill3.setVisible(false);
            sliderSkill3.setVisible(false);
        }
        else if (!skill2.getText().equals("")){
            skill2.setText("");
            sliderSkill2.setValue(0);
            skill2.setVisible(false);
            sliderSkill2.setVisible(false);
            substractSkillButton.setVisible(false);
            imageViewSubstractSkill.setVisible(false);
        }
    }
    @FXML
    private void SaveAll(ActionEvent event) throws SQLException {
        String update = userName.getText() + "'" + passwordField.getText()+ "'" + realNameField.getText() + "'";
        String updateSkills = "";
        if (!skill1.getText().equals("")){
            updateSkills += skill1.getText()+"="+sliderSkill1.getValue()+";";
        }
        if (!skill2.getText().equals("")){
            updateSkills += skill2.getText()+"="+sliderSkill2.getValue()+";";
        }
        if (!skill3.getText().equals("")){
            updateSkills += skill3.getText()+"="+sliderSkill3.getValue()+";";
        }
        if (!skill4.getText().equals("")){
            updateSkills += skill4.getText()+"="+sliderSkill4.getValue()+";";
        }
        update += updateSkills;
        if (!ageField.getText().equals("")){
            update +="'"+ ageField.getText() + "'";
        }
        else update += "' '";
        if (!ortField.getText().equals("")){
            update += ortField.getText() + "'";
        }
        else update += " '";
        if (!streetField.getText().equals("")){
            update += streetField.getText() + "'";
        }
        else update += " '";
        if (!telefonNumberField.getText().equals("")){
            update += telefonNumberField.getText() + "'";
        }
        else update += " '";
        if (!eMailField.getText().equals("")){
            update += eMailField.getText() + "'";
        }
        else update += " '";
        if (descriptionField.getText() != null && !descriptionField.getText().equals("")){
            update += descriptionField.getText();
        }
        else update += " '";
        DBConnection.getInstance().UpdateAll(update);
    }
    @FXML
    private void addSkill(ActionEvent event){
        substractSkillButton.setVisible(true);
        imageViewSubstractSkill.setVisible(true);
        if (skill1.getText().equals("")) {
            skill1.setVisible(true);
            sliderSkill1.setVisible(true);
            skill1.setText("YourSkill");
            sliderSkill1.setValue(0);
        }
        else if (skill2.getText().equals("")) {
            skill2.setVisible(true);
            sliderSkill2.setVisible(true);
            skill2.setText("YourSkill");
            sliderSkill2.setValue(0);
        }
        else if (skill3.getText().equals("")) {
            skill3.setVisible(true);
            sliderSkill3.setVisible(true);
            skill3.setText("YourSkill");
            sliderSkill3.setValue(0);
        }
        else if (skill4.getText().equals("")) {
            skill4.setVisible(true);
            sliderSkill4.setVisible(true);
            skill4.setText("YourSkill");
            sliderSkill4.setValue(0);
            addSkillButton.setVisible(false);
            imageViewAddSkill.setVisible(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String name = DBConnection.getInstance().userName;
        String password = DBConnection.getInstance().password;
        userName.setText(name);
        skill1.setVisible(false);
        skill2.setVisible(false);
        skill3.setVisible(false);
        skill4.setVisible(false);
        sliderSkill1.setVisible(false);
        sliderSkill2.setVisible(false);
        sliderSkill3.setVisible(false);
        sliderSkill4.setVisible(false);
        String skills = "";
        try {
            skills = DBConnection.getInstance().getUserSkills(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (skills != null && !skills.equals("<null>") ) {
            String[] lines = skills.split(";");

            for (int i = 0; i < lines.length && i < 4; i++) {
                String[] line = lines[i].split("=");
                if (i == 0 && line.length == 2) {
                    skill1.setVisible(true);
                    sliderSkill1.setVisible(true);
                    skill1.setText(line[0]);
                    sliderSkill1.setValue(Double.parseDouble(line[1]));
                } else if (i == 1 && line.length == 2) {
                    skill2.setVisible(true);
                    sliderSkill2.setVisible(true);
                    skill2.setText(line[0]);
                    sliderSkill2.setValue(Double.parseDouble(line[1]));
                } else if (i == 2 && line.length == 2) {
                    skill3.setVisible(true);
                    sliderSkill3.setVisible(true);
                    skill3.setText(line[0]);
                    sliderSkill3.setValue(Double.parseDouble(line[1]));
                } else if (i == 3 && line.length == 2) {
                    skill4.setVisible(true);
                    sliderSkill4.setVisible(true);
                    skill4.setText(line[0]);
                    sliderSkill4.setValue(Double.parseDouble(line[1]));
                }
            }
        }
        passwordField.setText(password);

        String realName = "";
        try {
            realName = DBConnection.getInstance().getUserRealName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (realName != null && !realName.equals("") && !realName.equals("<null>")){
            realNameField.setText(realName);
        }

        Integer age = -1;
        try {
            age = DBConnection.getInstance().getUserAge(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (age != null && age != -1 && age != 0 ){
            ageField.setText(Integer.toString(age));
        }

        String place = "";
        try {
            place = DBConnection.getInstance().getUserPlace(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (place != null && !place.equals("") && !place.equals("<null>")){
            ortField.setText(place);
        }

        String street = "";
        try {
            street = DBConnection.getInstance().getUserStreet(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (street != null && !street.equals("") && !street.equals("<null>")){
            streetField.setText(street);
        }

        String telefonNumber = "";
        try {
            telefonNumber = DBConnection.getInstance().getUserTelefonnumber(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (telefonNumber != null && !telefonNumber.equals("") && !telefonNumber.equals("<null>")){
            telefonNumberField.setText(telefonNumber);
        }

        String eMail = "";
        try {
            eMail = DBConnection.getInstance().getUserEMail(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (eMail != null && !eMail.equals("") && !eMail.equals("<null>")){
            eMailField.setText(eMail);
        }

        String description = "";
        try {
            description = DBConnection.getInstance().getUserDescription(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (description != null && !description.equals("") && !description.equals("<null>")){
            descriptionField.setText(description);
        }
    }
    @FXML
    public void writePassword(ActionEvent event){
        String password = DBConnection.getInstance().password;
        if (passwordCheckBox.isSelected() == true){
            passwordField.clear();
            passwordField.setPromptText(password);
            return;
        }
        passwordField.setText(password);
    }
}
