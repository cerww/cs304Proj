import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class ModifyVoterThing extends Thing {
    Program program;
    public ModifyVoterThing(Program mainProgram){

        program = mainProgram;
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(0,1));

        addInsertUI();
        addModifyUI();
        addDeleteUI();


    }

    private void addOptionLabelFor(String optionName,JPanel panel) {
        JLabel label = new JLabel();
        label.setText(optionName);
        panel.add(label);
    }

    private void addInsertUI(){
        JPanel insertUi = new JPanel();
        insertUi.setLayout(new GridLayout(0,2));

        addOptionLabelFor("voter id",insertUi);
        JTextField voterIDField = new JTextField();
        insertUi.add(voterIDField);
        addOptionLabelFor("address",insertUi);
        JTextField addressField = new JTextField();
        insertUi.add(addressField);

        addOptionLabelFor("name",insertUi);
        JTextField nameField = new JTextField();
        insertUi.add(nameField);
        addOptionLabelFor("age",insertUi);
        JTextField ageField = new JTextField();
        insertUi.add(ageField);
        addOptionLabelFor("postal code",insertUi);
        JTextField postalCodeField = new JTextField();
        insertUi.add(postalCodeField);
        sidePanel.add(insertUi);
        JButton insertButton = new JButton("add voter");
        insertButton.addActionListener((a)->{
            try (Statement s = program.dbConnection.createStatement()){
                String sqlStatement = MessageFormat.format(
                        "INSERT INTO VOTER VALUES({0},\"{1}\",\"{2}\",{3},\"{4}\");",
                        voterIDField.getText(),
                        addressField.getText(),
                        nameField.getText(),
                        ageField.getText(),
                        postalCodeField.getText()
                );
                System.out.println(sqlStatement);
                s.execute(sqlStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        sidePanel.add(insertButton);
    }

    private void addModifyUI(){
        JPanel insertUi = new JPanel();
        insertUi.setLayout(new GridLayout(0,2));

        addOptionLabelFor("voter id",insertUi);
        JTextField voterIDField = new JTextField();
        insertUi.add(voterIDField);
        addOptionLabelFor("address",insertUi);
        JTextField addressField = new JTextField();
        insertUi.add(addressField);

        addOptionLabelFor("name",insertUi);
        JTextField nameField = new JTextField();
        insertUi.add(nameField);

        addOptionLabelFor("age",insertUi);
        JTextField ageField = new JTextField();
        insertUi.add(ageField);

        addOptionLabelFor("postal code",insertUi);
        JTextField postalCodeField = new JTextField();
        insertUi.add(postalCodeField);

        sidePanel.add(insertUi);



        JButton modify_voterButton = new JButton("modify voter");
        modify_voterButton.addActionListener((a)->{
            try (Statement s = program.dbConnection.createStatement()){
                var format = new MessageFormat(
                        "UPDATE VOTER SET {0} = \"{1}\" where voter_id = {2};");


                if(!addressField.getText().isBlank()){
                    var sql_statement = format.format(new Object[]{"address",addressField.getText(),voterIDField.getText()});
                    System.out.println(sql_statement);
                    s.execute(sql_statement);
                }
                if(!nameField.getText().isBlank()){
                    var sql_statement = format.format(new Object[]{"name",nameField.getText(),voterIDField.getText()});
                    System.out.println(sql_statement);
                    s.execute(sql_statement);
                }
                if(!ageField.getText().isBlank()){
                    var sql_statement = format.format(new Object[]{"age",ageField.getText(),voterIDField.getText()});
                    System.out.println(sql_statement);
                    s.execute(sql_statement);
                }
                if(!postalCodeField.getText().isBlank()){
                    var sql_statement = format.format(new Object[]{"postal_code",postalCodeField.getText(),voterIDField.getText()});
                    System.out.println(sql_statement);
                    s.execute(sql_statement);
                }

                //System.out.println(sqlStatement);
                //s.execute(sqlStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        sidePanel.add(modify_voterButton);
    }

    private void addDeleteUI(){
        JPanel insertUi = new JPanel();
        insertUi.setLayout(new GridLayout(0,2));

        addOptionLabelFor("voter id",insertUi);
        JTextField voterIDField = new JTextField();
        insertUi.add(voterIDField);



        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((a)->{
            try (Statement s = program.dbConnection.createStatement()){
                String sqlStatement = MessageFormat.format("DELETE FROM `voter` WHERE `voter`.`voter_id` = {0}",voterIDField.getText());


                System.out.println(sqlStatement);
                s.execute(sqlStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        sidePanel.add(insertUi);
        sidePanel.add(deleteButton);
    }


}
