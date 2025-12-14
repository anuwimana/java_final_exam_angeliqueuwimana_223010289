package com.hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Room extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtDescription, txtCategory, txtPrice, txtStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private RoomDAO roomDAO;

    public Room() {
        setTitle("Room Monitoring");
        setSize(900, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        roomDAO = new RoomDAO();

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        txtName = new JTextField();
        txtDescription = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        txtStatus = new JTextField();

        inputPanel.add(new JLabel("Name"));
        inputPanel.add(new JLabel("Description"));
        inputPanel.add(new JLabel("Category"));
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(new JLabel("Status"));

        inputPanel.add(txtName);
        inputPanel.add(txtDescription);
        inputPanel.add(txtCategory);
        inputPanel.add(txtPrice);
        inputPanel.add(txtStatus);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Description", "Category", "Price", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons 
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
                loadRooms();
            }
        });
       
        // Add button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    RoomModel r = new RoomModel(
                        0,
                        txtName.getText(),
                        txtDescription.getText(),
                        txtCategory.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        txtStatus.getText()
                    );
                    if (roomDAO.addRoom(r)) {
                        loadRooms();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Room.this, "Error adding room: " + ex.getMessage());
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
                        RoomModel r = new RoomModel(
                            id,
                            txtName.getText(),
                            txtDescription.getText(),
                            txtCategory.getText(),
                            Double.parseDouble(txtPrice.getText()),
                            txtStatus.getText()
                        );
                        if (roomDAO.updateRoom(r)) {
                            loadRooms();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Room.this, "Error updating room: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Room.this, "Select a room to update");
                }
            }
        });
        // Delete button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    if (roomDAO.deleteRoom(id)) {
                        loadRooms();
                    }
                } else {
                    JOptionPane.showMessageDialog(Room.this, "Select a room to delete");
                }
            }
        });

        //Table click 
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtName.setText(tableModel.getValueAt(r, 1).toString());
                txtDescription.setText(tableModel.getValueAt(r, 2).toString());
                txtCategory.setText(tableModel.getValueAt(r, 3).toString());
                txtPrice.setText(tableModel.getValueAt(r, 4).toString());
                txtStatus.setText(tableModel.getValueAt(r, 5).toString());
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        loadRooms();
    }

    private void loadRooms() {
        tableModel.setRowCount(0);
        List<RoomModel> list = roomDAO.getAllRooms();
        for (RoomModel r : list) {
            tableModel.addRow(new Object[]{
                r.getRoomID(),
                r.getName(),
                r.getDescription(),
                r.getCategory(),
                r.getPriceOrValue(),
                r.getStatus()
            });
        }
    }
}
