package com.tomtruyen;

import com.tomtruyen.utils.MotionPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        //Remove Title Bar
        setUndecorated(true);

        // Minimum Size
        setMinimumSize(new Dimension(1200, 800));

        // Panel
        JPanel panel = new MotionPanel(this);

        // "Padding"
        panel.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Panel Styling
        panel.setBackground(new Color(30, 35, 50));

        // Components
        JLabel title = new JLabel("Welcome Back");
        title.setForeground(Color.white);
        title.setFont(new Font("Dialog", Font.PLAIN, 28));

        JLabel subtitle = new JLabel("CryptoTracker Desktop Release 1.0.0");
        subtitle.setForeground(Color.lightGray);
        subtitle.setFont(new Font("Dialog", Font.PLAIN, 16));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> System.exit(0));

        // TODO
        // Align All Components to Center of Screen!
        // Add Input Fields (Email & Password)
        // Add Button To Enter (Exodus)
        // Add Toggle Eye For Password
        // Custom Quit Button (Exodus)
        // Switch to Registration Page (Exodus)

        // Add Components
        panel.add(title);
        panel.add(subtitle);
        panel.add(closeButton);


        // Layout
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add panel
        add(panel);

        // Show
        setVisible(true);
    }
}
