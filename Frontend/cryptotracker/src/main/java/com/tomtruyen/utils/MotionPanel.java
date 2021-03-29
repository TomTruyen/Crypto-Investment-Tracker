package com.tomtruyen.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MotionPanel extends JPanel {
    private Point click;
    private JFrame parent;

    public MotionPanel(final JFrame parent) {
        this.parent = parent;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                click = e.getPoint();
                getComponentAt(click);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Get Window Location
                int locationX = parent.getLocation().x;
                int locationY = parent.getLocation().y;

                // Determine how much the mouse moved
                int xMoved = e.getX() - click.x;
                int yMoved = e.getY() - click.y;

                // Move Window
                int x = locationX + xMoved;
                int y = locationY + yMoved;
                parent.setLocation(x ,y);
            }
        });
    }
}
