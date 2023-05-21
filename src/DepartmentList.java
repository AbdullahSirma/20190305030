import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentList extends JFrame {
    private JTextField txtDepartment;
    private JButton btnSearch;
    private JTable tblDepartments;
    private JPanel pnlDepartmentList;
    private JButton btnUpdateDepartment;
    private JButton btnDeleteDepartment;
    private String connectionUrl = "jdbc:sqlserver://MYCOMP\\SQLEXPRESS:1433;" +
            "databaseName=20190305030;user=sql_user;password=123;trustServerCertificate=true";

    private DefaultTableModel model;

    public DepartmentList(){

        add(pnlDepartmentList);
        setSize(600,600);
        setTitle("Department List");


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

        model = new DefaultTableModel();

        model.addColumn("Id");
        model.addColumn("Department Name");
        model.addColumn("Max. Number Of Staff");

        Object[] row = new Object[3];

        int size = list.size();

        for (int i = 0; i < size ; i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getDepartmentName();
            row[2] = list.get(i).getMaxNumberOfStaff();

            model.addRow(row);
        }

        tblDepartments.setModel(model);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SQL = "SELECT * FROM Departments WHERE DepartmentName LIKE '%' + ? + '%'";
                List<Department> list = new ArrayList<Department>();

                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {
                    stmt.setString(1,txtDepartment.getText());
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

                model = new DefaultTableModel();

                model.addColumn("Id");
                model.addColumn("Department Nmae");
                model.addColumn("Max. Number Of Staff");

                Object[] row = new Object[3];

                int size = list.size();

                for (int i = 0; i < size ; i++) {
                    row[0] = list.get(i).getId();
                    row[1] = list.get(i).getDepartmentName();
                    row[2] = list.get(i).getMaxNumberOfStaff();

                    model.addRow(row);
                }
                tblDepartments.setModel(model);
            }
        });
        btnUpdateDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int selectedRowIndex = tblDepartments.getSelectedRow();
                if(selectedRowIndex == -1){
                    JOptionPane.showMessageDialog(btnDeleteDepartment,"Please select row to update.");
                    return;
                }

               Department selectedDepartment =  list.get(selectedRowIndex);

               UpdateDepartment updateDepartment = new UpdateDepartment(selectedDepartment);
               updateDepartment.setVisible(true);
            }
        });
        btnDeleteDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = tblDepartments.getSelectedRow();

                if(selectedRowIndex == -1){
                    JOptionPane.showMessageDialog(btnDeleteDepartment,"Please select row to delete.");
                    return;
                }
                int decision = JOptionPane.showConfirmDialog(btnDeleteDepartment,"Do you really want to delete selected department");
            if (decision == 0){
                String SQL = "DELETE FROM Departments WHERE Id = ?";


                Department selectedDepartment =  list.get(selectedRowIndex);

                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement stmt = con.prepareStatement(SQL);) {

                    stmt.setInt(1, selectedDepartment.getId());

                    int result = stmt.executeUpdate();

                    if(result == 1){
                        JOptionPane.showMessageDialog(btnDeleteDepartment,"Department deleted successfully");
                    }
                }
                // Handle any errors that may have occurred.
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


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

                model = new DefaultTableModel();

                model.addColumn("Id");
                model.addColumn("Department Nmae");
                model.addColumn("Max. Number Of Staff");

                Object[] row = new Object[3];

                int size = list.size();

                for (int i = 0; i < size ; i++) {
                    row[0] = list.get(i).getId();
                    row[1] = list.get(i).getDepartmentName();
                    row[2] = list.get(i).getMaxNumberOfStaff();

                    model.addRow(row);
                }

                tblDepartments.setModel(model);
            }
        });
    }
}
