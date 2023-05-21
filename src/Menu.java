import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JButton btnNewDepartment;
    private JPanel pnlMenu;
    private JButton btnDepartmentList;
    private JButton btnNewEmployee;
    private JButton btnEmployeeList;

    public Menu(){
        add(pnlMenu);
        setSize(450,300);
        setLocation(100, 100);
        setTitle("Menu");
        btnNewDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewDepartment newDepartment = new NewDepartment();
                newDepartment.setVisible(true);
            }
        });
        btnDepartmentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepartmentList departmentList = new DepartmentList();
                departmentList.setVisible(true);
            }
        });
        btnNewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewEmployee newEmployee = new NewEmployee();
                newEmployee.setVisible(true);
            }
        });
        btnEmployeeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeList employeeList = new EmployeeList();
                employeeList.setVisible(true);
            }
        });
    }
}
