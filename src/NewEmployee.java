import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewEmployee extends JFrame{
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtBirthDate;
    private JTextField txtSalary;
    private JComboBox cmbDepartments;
    private JButton btnSave;
    private JPanel pnlNewEmployee;
    private String connectionUrl = "jdbc:sqlserver://MYCOMP\\SQLEXPRESS:1433;" +
            "databaseName=20190305030;user=sql_user;password=123;trustServerCertificate=true";
    public NewEmployee(){
        add(pnlNewEmployee);
        setSize(500,400);
        setTitle("New Employee");

        String SQL = "SELECT * FROM Departments";
        List<Department> list = new ArrayList<Department>();

        try (Connection con = DriverManager.getConnection(connectionUrl);
             PreparedStatement stmt = con.prepareStatement(SQL);) {
            ResultSet result = stmt.executeQuery();

            while(result.next()){


                Department department = new Department();
                department.setId(result.getInt("Id"));
                department.setDepartmentName(result.getString("DepartmentName"));
                department.setMaxNumberOfStaff(result.getInt("MaxNumberOfStaff"));

                list.add(department);
            }


        }
        // Handle any errors that may have occurred.
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        for(Department d:list){
            cmbDepartments.addItem(d.getDepartmentName());
        }
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SQL = "INSERT INTO Employees(DepartmentId,FirstName,LastName,BirthDate,Salary) " +
                        "VALUES(?,?,?,?,?)";
                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {

                    int departmentId = 0;

                    for (Department d : list) {
                        if(d.getDepartmentName().equals(cmbDepartments.getSelectedItem().toString())){
                            departmentId = d.getId();
                            break;
                        }
                    }

                    stmt.setInt(1, departmentId);
                    stmt.setString(2, txtFirstName.getText());
                    stmt.setString(3,txtLastName.getText());
                    LocalDate birthDate = LocalDate.parse(txtBirthDate.getText());
                    stmt.setDate(4, Date.valueOf(birthDate));
                    stmt.setDouble(5, Double.valueOf(txtSalary.getText()));

                    int result = stmt.executeUpdate();

                    if(result == 1){
                        JOptionPane.showMessageDialog(btnSave,"New employee saved successfully");
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
