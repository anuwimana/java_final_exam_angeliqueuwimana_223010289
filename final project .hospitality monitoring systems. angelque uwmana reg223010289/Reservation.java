package com.hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class Reservation extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtOrderNumber, txtDate, txtStatus, txtTotalAmount, txtPaymentMethod, txtNotes, txtGuestID, txtRoomID;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private ReservationDAO reservationDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Reservation() {
        setTitle("Reservation Monitoring");
        setSize(1000, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        reservationDAO = new ReservationDAO();

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 8, 5, 5));
        txtOrderNumber = new JTextField();
        txtDate = new JTextField();
        txtStatus = new JTextField();
        txtTotalAmount = new JTextField();
        txtPaymentMethod = new JTextField();
        txtNotes = new JTextField();
        txtGuestID = new JTextField();
        txtRoomID = new JTextField();

        inputPanel.add(new JLabel("Order#"));
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(new JLabel("Status"));
        inputPanel.add(new JLabel("Total"));
        inputPanel.add(new JLabel("Payment"));
        inputPanel.add(new JLabel("Notes"));
        inputPanel.add(new JLabel("GuestID"));
        inputPanel.add(new JLabel("RoomID"));

        inputPanel.add(txtOrderNumber);
        inputPanel.add(txtDate);
        inputPanel.add(txtStatus);
        inputPanel.add(txtTotalAmount);
        inputPanel.add(txtPaymentMethod);
        inputPanel.add(txtNotes);
        inputPanel.add(txtGuestID);
        inputPanel.add(txtRoomID);

        add(inputPanel, BorderLayout.NORTH);

        // Table 
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Order#", "Date", "Status", "Total", "Payment", "Notes", "GuestID", "RoomID"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //  Buttons
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnLoad = new JButton("Load");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnLoad);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load button
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadReservations();
            }
        });
        
        //Add button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ReservationModel r = new ReservationModel(
                        0,
                        txtOrderNumber.getText(),
                        dateFormat.parse(txtDate.getText()),
                        txtStatus.getText(),
                        Double.parseDouble(txtTotalAmount.getText()),
                        txtPaymentMethod.getText(),
                        txtNotes.getText(),
                        Integer.parseInt(txtGuestID.getText()),
                        Integer.parseInt(txtRoomID.getText())
                    );
                    if (reservationDAO.addReservation(r)) loadReservations();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Reservation.this, "Invalid input: " + ex.getMessage());
                }
            }
        });
        
        // Update button
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    try {
                        int id = (int) tableModel.getValueAt(row, 0);
                        ReservationModel r = new ReservationModel(
                            id,
                            txtOrderNumber.getText(),
                            dateFormat.parse(txtDate.getText()),
                            txtStatus.getText(),
                            Double.parseDouble(txtTotalAmount.getText()),
                            txtPaymentMethod.getText(),
                            txtNotes.getText(),
                            Integer.parseInt(txtGuestID.getText()),
                            Integer.parseInt(txtRoomID.getText())
                        );
                        if (reservationDAO.updateReservation(r)) loadReservations();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Reservation.this, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Reservation.this, "Select a reservation to update");
                }
            }
        });
         
        // Delete button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    if (reservationDAO.deleteReservation(id)) loadReservations();
                } else {
                    JOptionPane.showMessageDialog(Reservation.this, "Select a reservation to delete");
                }
            }
        });

        // Table Click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtOrderNumber.setText(tableModel.getValueAt(r, 1).toString());
                txtDate.setText(tableModel.getValueAt(r, 2).toString());
                txtStatus.setText(tableModel.getValueAt(r, 3).toString());
                txtTotalAmount.setText(tableModel.getValueAt(r, 4).toString());
                txtPaymentMethod.setText(tableModel.getValueAt(r, 5).toString());
                txtNotes.setText(tableModel.getValueAt(r, 6).toString());
                txtGuestID.setText(tableModel.getValueAt(r, 7).toString());
                txtRoomID.setText(tableModel.getValueAt(r, 8).toString());
            }
        });

        setVisible(true);
        loadReservations();
    }

    private void loadReservations() {
        tableModel.setRowCount(0);
        List<ReservationModel> list = reservationDAO.getAllReservations();
        for (ReservationModel r : list) {
            tableModel.addRow(new Object[]{
                r.getReservationID(),
                r.getOrderNumber(),
                dateFormat.format(r.getDate()),
                r.getStatus(),
                r.getTotalAmount(),
                r.getPaymentMethod(),
                r.getNotes(),
                r.getGuestID(),
                r.getRoomID()
            });
        }
    }
}
