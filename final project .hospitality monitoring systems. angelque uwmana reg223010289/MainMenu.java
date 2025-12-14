package com.hms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.hms.Guest;
import com.hms.Room;
import com.hms.Service;
import com.hms.Invoice;
import com.hms.Staff;
import com.hms.Reservation;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Hospitality Monitoring System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 3, 10, 10));

        JButton btnGuest = new JButton("Guest");
        JButton btnReservation = new JButton("Reservation");
        JButton btnRoom = new JButton("Room");
        JButton btnService = new JButton("Service");
        JButton btnInvoice = new JButton("Invoice");
        JButton btnStaff = new JButton("Staff");

        add(btnGuest);
        add(btnReservation);
        add(btnRoom);
        add(btnService);
        add(btnInvoice);
        add(btnStaff);

        
        btnGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Guest().setVisible(true);
            }
        });

        btnReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Reservation().setVisible(true);
            }
        });

        btnRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Room().setVisible(true);
            }
        });

        btnService.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Service().setVisible(true);
            }
        });

        btnInvoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Invoice().setVisible(true);
            }
        });

        btnStaff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Staff().setVisible(true);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}