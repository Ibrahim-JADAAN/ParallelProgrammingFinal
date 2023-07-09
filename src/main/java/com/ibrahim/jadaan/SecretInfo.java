package com.ibrahim.jadaan;

import com.ibrahim.jadaan.SQLConnection.SQLServerConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class SecretInfo extends JFrame {

    private JPanel secretPanel;
    private JButton closeButton;
    private JTextField txtSecret;
    private JButton submitButton;
    private String secretKey;

    // To add an icon to close button
    private  final ImageIcon closeButtonIcon = new ImageIcon("C:\\Users\\himoo\\IdeaProjects\\ParallelProgrammingFinal\\src\\main\\java\\com\\ibrahim\\jadaan\\Sources\\closeButtonIcon.png");
    SQLServerConnector connector = new SQLServerConnector();
    private int lastInsertedId;


    public SecretInfo(){


        add(secretPanel);
        setTitle("Secret Information");
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

        //*************************************************************************

        closeButton.setIcon(closeButtonIcon);
        if(closeButton.getIcon() != null){ closeButton.setText("");}


        // Setting default text in the text fields
        txtSecret.setForeground(Color.LIGHT_GRAY);
        txtSecret.setText("Secret key");


        // * * * * * * * * * * * * * * * * *    BUTTON LISTENERS

        txtSecret.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(txtSecret.getForeground() == Color.LIGHT_GRAY){
                    txtSecret.setText("");
                    txtSecret.setForeground(Color.BLACK);
                }
            }
        });


        // Save entered information to the database
        submitButton.addActionListener(e -> {

            secretKey = txtSecret.getText();

            boolean isEmpty = secretKey.equals("");
            boolean isDefault = secretKey.equals("Secret key");
            boolean isTooShort = secretKey.length() < 5;

            if(isDefault || isEmpty){
                JOptionPane.showMessageDialog(null, "  Enter Secret key information before submitting  ");
            }

            else if (isTooShort){
                JOptionPane.showMessageDialog(null, "\n\n  Username should be at least 5 character   ");
            }

            else {
                saveSecretInfo();
                dispose();
            }

        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }  // SecretInfo Constructor end


    // this method's purpose is adding entered secret key to database
    private void saveSecretInfo() {

        lastInsertedId = connector.getLastId();
        String insQuery = "UPDATE  `user_info` SET `secret_key` = ' " + secretKey + " ' WHERE (`user_id` = ' " + lastInsertedId + " ');";
        try {
            Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Show Message Dialog after information saved to database
        JOptionPane.showMessageDialog(null, "Secret key addition successfully  ");

    }



} // SecretInfo Class end
