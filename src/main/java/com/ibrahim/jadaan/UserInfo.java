package com.ibrahim.jadaan;

import com.ibrahim.jadaan.SQLConnection.SQLServerConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInfo extends JFrame {
    private JPanel mainPanel;
    private JButton closeButton;
    private JTextField txtUsername;
    private JTextField txtMail;
    private JRadioButton showPasswordRButton;
    private JPasswordField txtPassword;
    private JButton submitButton;

    // To add an icon to close button
    private  final ImageIcon closeButtonIcon = new ImageIcon("C:\\Users\\himoo\\IdeaProjects\\ParallelProgrammingFinal\\src\\main\\java\\com\\ibrahim\\jadaan\\Sources\\closeButtonIcon.png");

    SQLServerConnector connector = new SQLServerConnector();

    //**************************************************************

    private String userName;
    private String mail;
    private String password;

    //********************************************************
    public UserInfo() {

        add(mainPanel);
        setTitle("User Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        ImageIcon image = new ImageIcon("");
        setIconImage(image.getImage());
        setSize(420, 450);
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        setLocation(dx, dy);

        //**************************************************************************

        closeButton.setIcon(closeButtonIcon);
        if(closeButton.getIcon() != null){ closeButton.setText("");}


        // Setting default text in the text fields

        txtUsername.setForeground(Color.LIGHT_GRAY);
        txtUsername.setText("Username");

        txtMail.setForeground(Color.LIGHT_GRAY);
        txtMail.setText("E-Mail");

        txtPassword.setForeground(Color.LIGHT_GRAY);
        txtPassword.setText("Password");


        showPasswordRButton.setSelected(true);
        txtPassword.setEchoChar((char) 0);


        // * * * * * * * * * * * * * * * * *    BUTTON LISTENERS

        closeButton.addActionListener(e -> dispose());

        txtUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtUsername.getForeground() == Color.LIGHT_GRAY) {
                    txtUsername.setText("");
                    txtUsername.setForeground(Color.BLACK);
                }
            }
        });


        txtMail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtMail.getForeground() == Color.LIGHT_GRAY) {
                    txtMail.setText("");
                    txtMail.setForeground(Color.BLACK);
                }
            }
        });


        txtPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtPassword.getForeground() == Color.LIGHT_GRAY) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.BLACK);
                    showPasswordRButton.setSelected(false);
                    txtPassword.setEchoChar('•');
                }
            }
        });


        showPasswordRButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showPasswordRButton.isSelected()) {
                    txtPassword.setEchoChar((char) 0);
                } else {
                    txtPassword.setEchoChar('•');
                }
            }
        });

        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtUsername.getText().length() >= 25) {
                    e.consume();
                    txtUsername.setText(txtUsername.getText().substring(0, 24));
                }
            }
        });


        txtMail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtMail.getText().length() >= 25) {
                    e.consume();
                    txtMail.setText(txtMail.getText().substring(0, 24));
                }
            }
        });


        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtPassword.getText().length() >= 15) {
                    e.consume();
                    txtPassword.setText(txtPassword.getText().substring(0, 14));
                }
            }
        });


        // Checking entered information (if empty or aren't entered) before saving to database
        submitButton.addActionListener(e -> {

            userName = txtUsername.getText();
            mail = txtMail.getText();
            password = txtPassword.getText();

            boolean isEmpty = userName.equals("") || mail.equals("") || password.equals("");
            boolean isDefault = userName.equals("Username") || mail.equals("E-Mail") || password.equals("Password");
            boolean isTooShort = userName.length() < 5 || mail.length() < 6 || password.length() < 8;

            if (isDefault || isEmpty) {
                JOptionPane.showMessageDialog(null, "  Enter User information before submitting  ");
            }

            // Check if less than wanted length
            else if (isTooShort) {
                JOptionPane.showMessageDialog(null, "\n\n  Username should be more than 4 character    \n\n" +
                        "  E-mail should be more than 5 character    \n\n" +
                        "  Password should be more than 7 character    \n\n");
            }

            // After validate the information save it to database
            else {
                saveUserInfo (userName, mail, password); // to the database
                dispose();

                // Second Thread (secretInformationThread)
                ParallelService secretInformationThread = new ParallelService();
                secretInformationThread.secretInformationWindow();

                secretInformationThread.start();
                try {
                    secretInformationThread.join();
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

            } // else statement end

        });

    } // UserInfo Constructor end


    // this method's purpose is adding entered username, mail and password to database
    private void saveUserInfo(String userName, String mail, String password) {

            String insQuery = "INSERT INTO `user_info` (`user_name`, `mail`, `password`) VALUES (?, ?, ?)";
            try {
                Connection connection = connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insQuery);
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, mail);
                preparedStatement.setString(3, password);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            // Show Message Dialog after information saved to database
            JOptionPane.showMessageDialog(null, "Registration completed successfully ");
    }


}  // InfoRegister Class end

