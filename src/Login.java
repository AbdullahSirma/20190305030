import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField txtEmail;
    private JPanel pnlLogin;
    private JButton loginButton;
    private JPasswordField txtPassword;

    public Login(){
        add(pnlLogin);
        setSize(550,300);
        setLocation(100,100);
        setTitle("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String connectionUrl = "jdbc:sqlserver://DESKTOP-5CONCDT\\MSSQLSERVER01;databaseName=20190305030;user=sql_user;password=123;trustServerCertificate=true";
                String SQL = "SELECT COUNT(*) FROM Users WHERE Email = ? and Password = ? ";
                try (Connection con = DriverManager.getConnection(connectionUrl);
                     PreparedStatement  stmt = con.prepareStatement(SQL);) {

                    stmt.setString(1, txtEmail.getText());
                    stmt.setString(2, txtPassword.getText());

                    ResultSet rs = stmt.executeQuery();

                    // Iterate through the data in the result set and display it.
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        if(count > 0){
                            Menu menu = new Menu();
                            menu.setVisible(true);
                            setVisible(false);
                        }else{
                            JOptionPane.showMessageDialog( loginButton,"Email or password is incorrrect.");
                        }

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
