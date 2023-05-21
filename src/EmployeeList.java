import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeList extends JFrame{
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tblEmployees;
    private JPanel pnlEmployeeList;
    private JScrollPane scrollPane1;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;
    private String connectionUrl = "jdbc:sqlserver://DESKTOP-5CONCDT\\MSSQLSERVER01;databaseName=20190305030;user=sql_user;password=123;trustServerCertificate=true";

    private DefaultTableModel model;
    public EmployeeList(){

        add(pnlEmployeeList);
        setSize(600,600);
        setTitle("Employee List");


        String SQL = "SELECT Id,DepartmentId,FirstName,LastName,BirthDate,Salary FROM Employees";
        List<Employee> list = new ArrayList<Employee>();

        try (Connection con = DriverManager.getConnection(connectionUrl);
             PreparedStatement stmt = con.prepareStatement(SQL);) {
            ResultSet result = stmt.executeQuery();

            while(result.next()){
                Employee employee = new Employee();
                employee.setId(result.getInt("Id"));
                employee.setDepartmentId(result.getInt("DepartmentId"));
                employee.setFirstName(result.getString("FirstName"));
                employee.setLastName(result.getString("LastName"));
                employee.setBirthDate(result.getDate("BirthDate").toLocalDate());
                employee.setSalary(result.getDouble("Salary"));

                list.add(employee);
            }


        }
        // Handle any errors that may have occurred.
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        model = new DefaultTableModel();

        model.addColumn("Id");
        model.addColumn("Department Id");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Birth Date");
        model.addColumn("Salary");

        Object[] row = new Object[6];

        int size = list.size();

        for (int i = 0; i < size ; i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getDepartmentId();
            row[2] = list.get(i).getFirstName();
            row[3] = list.get(i).getLastName();
            row[4] = list.get(i).getBirthDate();
            row[5] = list.get(i).getSalary();

            model.addRow(row);
        }

        tblEmployees.setModel(model);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SQL = "SELECT Id,DepartmentId,FirstName,LastName,BirthDate,Salary FROM Employees " +
                        " WHERE FirstName LIKE '%' + ? +'%' OR LastName LIKE '%' + ? +'%' ";
                List<Employee> list = new ArrayList<Employee>();

                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {

                    stmt.setString(1, txtSearch.getText());
                    stmt.setString(2, txtSearch.getText());

                    ResultSet result = stmt.executeQuery();

                    while(result.next()){
                        Employee employee = new Employee();
                        employee.setId(result.getInt("Id"));
                        employee.setDepartmentId(result.getInt("DepartmentId"));
                        employee.setFirstName(result.getString("FirstName"));
                        employee.setLastName(result.getString("LastName"));
                        employee.setBirthDate(result.getDate("BirthDate").toLocalDate());
                        employee.setSalary(result.getDouble("Salary"));

                        list.add(employee);
                    }


                }
                // Handle any errors that may have occurred.
                catch (SQLException ex) {
                    ex.printStackTrace();
                }

                model = new DefaultTableModel();

                model.addColumn("Id");
                model.addColumn("Department Id");
                model.addColumn("First Name");
                model.addColumn("Last Name");
                model.addColumn("Birth Date");
                model.addColumn("Salary");

                Object[] row = new Object[6];

                int size = list.size();

                for (int i = 0; i < size ; i++) {
                    row[0] = list.get(i).getId();
                    row[1] = list.get(i).getDepartmentId();
                    row[2] = list.get(i).getFirstName();
                    row[3] = list.get(i).getLastName();
                    row[4] = list.get(i).getBirthDate();
                    row[5] = list.get(i).getSalary();

                    model.addRow(row);
                }
                tblEmployees.setModel(model);
            }
        });
        btnDeleteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = tblEmployees.getSelectedRow();

                if(selectedRowIndex == -1){
                    JOptionPane.showMessageDialog(btnDeleteEmployee,"Please select row to delete.");
                    return;
                }
                int decision = JOptionPane.showConfirmDialog(btnDeleteEmployee,"Do you really want to delete selected department");
                if (decision == 0){
                    String SQL = "DELETE FROM Employees WHERE Id = ?";


                    Employee selectedEmployee =  list.get(selectedRowIndex);

                    try (Connection con = DriverManager.getConnection(connectionUrl);
                         PreparedStatement stmt = con.prepareStatement(SQL);) {

                        stmt.setInt(1, selectedEmployee.getId());

                        int result = stmt.executeUpdate();

                        if(result == 1){
                            JOptionPane.showMessageDialog(btnDeleteEmployee,"Employee deleted successfully");
                        }
                    }
                    // Handle any errors that may have occurred.
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                List<Employee> list = new ArrayList<Employee>();
                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {
                    ResultSet result = stmt.executeQuery();

                    while(result.next()){
                        Employee employee = new Employee();
                        employee.setId(result.getInt("Id"));
                        employee.setDepartmentId(result.getInt("DepartmentId"));
                        employee.setFirstName(result.getString("FirstName"));
                        employee.setLastName(result.getString("LastName"));
                        employee.setBirthDate(result.getDate("BirthDate").toLocalDate());
                        employee.setSalary(result.getDouble("Salary"));

                        list.add(employee);
                    }


                }
                // Handle any errors that may have occurred.
                catch (SQLException ex) {
                    ex.printStackTrace();
                }

                model = new DefaultTableModel();

                model.addColumn("Id");
                model.addColumn("Department Id");
                model.addColumn("First Name");
                model.addColumn("Last Name");
                model.addColumn("Birth Date");
                model.addColumn("Salary");

                Object[] row = new Object[6];

                int size = list.size();

                for (int i = 0; i < size ; i++) {
                    row[0] = list.get(i).getId();
                    row[1] = list.get(i).getDepartmentId();
                    row[2] = list.get(i).getFirstName();
                    row[3] = list.get(i).getLastName();
                    row[4] = list.get(i).getBirthDate();
                    row[5] = list.get(i).getSalary();

                    model.addRow(row);
                }
                tblEmployees.setModel(model);
            }
        });
        btnUpdateEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRowIndex = tblEmployees.getSelectedRow();

                if(selectedRowIndex == -1){
                    JOptionPane.showMessageDialog(btnUpdateEmployee,"Please select row to update.");
                    return;
                }

                Employee selectedEmployee = list.get(selectedRowIndex);

                UpdateEmployee updateEmployee = new UpdateEmployee(selectedEmployee);
                updateEmployee.setVisible(true);
            }
        });
    }
}
