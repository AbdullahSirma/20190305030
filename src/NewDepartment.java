import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NewDepartment extends JFrame{
    private JTextField txtDepartmentName;
    private JTextField txtMaxNumber;
    private JButton btnSave;
    private JPanel pnlNewDepartment;

    public NewDepartment(){
        add(pnlNewDepartment);
        setSize(550,300);
        setTitle("New Department");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String connectionUrl = "jdbc:sqlserver://DESKTOP-5CONCDT\\MSSQLSERVER01;databaseName=20190305030;user=sql_user;password=123;trustServerCertificate=true";
                String SQL = "INSERT INTO Departments VALUES(?,?)";
                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {

                    stmt.setString(1, txtDepartmentName.getText());
                    stmt.setInt(2, Integer.valueOf(txtMaxNumber.getText()));

                   int result = stmt.executeUpdate();

                   if(result == 1){
                       JOptionPane.showMessageDialog(btnSave,"New department saved successfully");
                   }
                }
                // Handle any errors that may have occurred.
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
