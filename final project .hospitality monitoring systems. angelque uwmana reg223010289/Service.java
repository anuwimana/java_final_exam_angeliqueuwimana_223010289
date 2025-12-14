package com.hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class Service extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtDescription, txtCategory, txtPrice, txtStatus, txtCreatedAt;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private ServiceDAO serviceDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Service() {
        setTitle("Service Monitoring");
        setSize(900, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        serviceDAO = new ServiceDAO();

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        txtName = new JTextField();
        txtDescription = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        txtStatus = new JTextField();
        txtCreatedAt = new JTextField();

        inputPanel.add(new JLabel("Name"));
        inputPanel.add(new JLabel("Description"));
        inputPanel.add(new JLabel("Category"));
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(new JLabel("Status"));
        inputPanel.add(new JLabel("Created At"));

        inputPanel.add(txtName);
        inputPanel.add(txtDescription);
        inputPanel.add(txtCategory);
        inputPanel.add(txtPrice);
        inputPanel.add(txtStatus);
        inputPanel.add(txtCreatedAt);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Description", "Category", "Price", "Status", "CreatedAt"}, 0);
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
                loadServices();
            }
        });
        
        // Add button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ServiceModel s = new ServiceModel(
                            0,
                            txtName.getText(),
                            txtDescription.getText(),
                            txtCategory.getText(),
                            Double.parseDouble(txtPrice.getText()),
                            txtStatus.getText(),
                            dateFormat.parse(txtCreatedAt.getText())
                    );
                    if (serviceDAO.addService(s)) loadServices();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Service.this, "Error adding service: " + ex.getMessage());
                }
            }
        });
        
        // Update button
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    try {
                        int id = (Integer) tableModel.getValueAt(row, 0);
                        ServiceModel s = new ServiceModel(
                                id,
                                txtName.getText(),
                                txtDescription.getText(),
                                txtCategory.getText(),
                                Double.parseDouble(txtPrice.getText()),
                                txtStatus.getText(),
                                dateFormat.parse(txtCreatedAt.getText())
                        );
                        if (serviceDAO.updateService(s)) loadServices();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Service.this, "Error updating service: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Service.this, "Select a service to update");
                }
            }
        });
        
        // Delete button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (Integer) tableModel.getValueAt(row, 0);
                    if (serviceDAO.deleteService(id)) loadServices();
                } else {
                    JOptionPane.showMessageDialog(Service.this, "Select a service to delete");
                }
            }
        });

        // table click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtName.setText(tableModel.getValueAt(r, 1).toString());
                txtDescription.setText(tableModel.getValueAt(r, 2).toString());
                txtCategory.setText(tableModel.getValueAt(r, 3).toString());
                txtPrice.setText(tableModel.getValueAt(r, 4).toString());
                txtStatus.setText(tableModel.getValueAt(r, 5).toString());
                txtCreatedAt.setText(tableModel.getValueAt(r, 6).toString());
            }
        });

        setVisible(true);
        loadServices();
    }

    private void loadServices() {
        tableModel.setRowCount(0);
        List<ServiceModel> list = serviceDAO.getAllServices();
        for (ServiceModel s : list) {
            tableModel.addRow(new Object[]{
                    s.getServiceID(),
                    s.getName(),
                    s.getDescription(),
                    s.getCategory(),
                    s.getPriceOrValue(),
                    s.getStatus(),
                    dateFormat.format(s.getCreatedAt())
            });
        }
    }
}
