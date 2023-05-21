import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDepartment extends JFrame{
    private JTextField txtDepartmentName;
    private JButton btnSave;
    private JTextField txtMaxNumber;
    private JPanel pnlUpdateDepartment;

    public UpdateDepartment(Department department){
        add(pnlUpdateDepartment);
        setSize(550,300);
        setTitle("Update Department");

        txtDepartmentName.setText(department.getDepartmentName());
        txtMaxNumber.setText(String.valueOf(department.getMaxNumberOfStaff()));

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String connectionUrl = "jdbc:sqlserver://MYCOMP\\SQLEXPRESS:1433;databaseName=20190305030;user=sql_user;password=123;trustServerCertificate=true";
                String SQL = "UPDATE Departments SET DepartmentName = ?, MaxNumberOfStaff = ? WHERE Id = ?";
                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {

                    stmt.setString(1, txtDepartmentName.getText());
                    stmt.setInt(2, Integer.valueOf(txtMaxNumber.getText()));
                    stmt.setInt(3, department.getId());
                    int result = stmt.executeUpdate();

                    if(result == 1){
                        JOptionPane.showMessageDialog(btnSave,"Department updated successfully");
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
