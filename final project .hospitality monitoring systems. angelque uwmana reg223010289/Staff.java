package com.hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class Staff extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtIdentifier, txtStatus, txtLocation, txtContact, txtAssignedSince;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private StaffDAO staffDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Staff() {
        setTitle("Staff Monitoring");
        setSize(900, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        staffDAO = new StaffDAO();

        //Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        txtName = new JTextField(); 
        txtIdentifier = new JTextField(); 
        txtStatus = new JTextField();
        txtLocation = new JTextField(); 
        txtContact = new JTextField(); 
        txtAssignedSince = new JTextField();

        inputPanel.add(new JLabel("Name")); 
        inputPanel.add(new JLabel("Identifier")); 
        inputPanel.add(new JLabel("Status"));
        inputPanel.add(new JLabel("Location")); 
        inputPanel.add(new JLabel("Contact")); 
        inputPanel.add(new JLabel("Assigned Since"));

        inputPanel.add(txtName); 
        inputPanel.add(txtIdentifier); 
        inputPanel.add(txtStatus);
        inputPanel.add(txtLocation); 
        inputPanel.add(txtContact); 
        inputPanel.add(txtAssignedSince);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID","Name","Identifier","Status","Location","Contact","Assigned Since"},0);
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
                loadStaff();
            }
        });
        
        // Add button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    StaffModel s = new StaffModel(0, txtName.getText(), txtIdentifier.getText(), txtStatus.getText(),
                            txtLocation.getText(), txtContact.getText(), dateFormat.parse(txtAssignedSince.getText()));
                    if(staffDAO.addStaff(s)) loadStaff();
                } catch(Exception ex){ 
                    JOptionPane.showMessageDialog(Staff.this, "Error adding staff: " + ex.getMessage());
                    ex.printStackTrace(); 
                }
            }
        });
        
        // Update button
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row != -1){
                    try {
                        int id = ((Number)tableModel.getValueAt(row,0)).intValue();
                        StaffModel s = new StaffModel(id, txtName.getText(), txtIdentifier.getText(), txtStatus.getText(),
                                txtLocation.getText(), txtContact.getText(), dateFormat.parse(txtAssignedSince.getText()));
                        if(staffDAO.updateStaff(s)) loadStaff();
                    } catch(Exception ex){ 
                        JOptionPane.showMessageDialog(Staff.this, "Error updating staff: " + ex.getMessage());
                        ex.printStackTrace(); 
                    }
                } else { 
                    JOptionPane.showMessageDialog(Staff.this,"Select a staff to update"); 
                }
            }
        });
        
        // Delete button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row != -1){
                    int id = ((Number)tableModel.getValueAt(row,0)).intValue();
                    if(staffDAO.deleteStaff(id)) loadStaff();
                } else { 
                    JOptionPane.showMessageDialog(Staff.this,"Select a staff to delete"); 
                }
            }
        });

        // Table click 
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if(r != -1){
                    txtName.setText(tableModel.getValueAt(r,1).toString());
                    txtIdentifier.setText(tableModel.getValueAt(r,2).toString());
                    txtStatus.setText(tableModel.getValueAt(r,3).toString());
                    txtLocation.setText(tableModel.getValueAt(r,4).toString());
                    txtContact.setText(tableModel.getValueAt(r,5).toString());
                    txtAssignedSince.setText(tableModel.getValueAt(r,6).toString());
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        loadStaff();
    }
    
    private void loadStaff(){
        tableModel.setRowCount(0);
        List<StaffModel> list = staffDAO.getAllStaff();
        for(StaffModel s : list){
            tableModel.addRow(new Object[]{
                s.getStaffID(), 
                s.getName(), 
                s.getIdentifier(), 
                s.getStatus(), 
                s.getLocation(), 
                s.getContact(), 
                dateFormat.format(s.getAssignedSince())
            });
        }
    }
}
