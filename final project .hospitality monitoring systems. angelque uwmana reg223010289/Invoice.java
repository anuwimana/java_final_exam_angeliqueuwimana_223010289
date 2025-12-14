
package com.hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class Invoice extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtAmount, txtDate, txtType, txtReference, txtStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private InvoiceDAO invoiceDAO;

    public Invoice() {
        setTitle("Invoice Monitoring");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        invoiceDAO = new InvoiceDAO();

        // Input Panel 
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        txtAmount = new JTextField();
        txtDate = new JTextField();
        txtType = new JTextField();
        txtReference = new JTextField();
        txtStatus = new JTextField();

        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(new JLabel("Date (yyyy-MM-dd)"));
        inputPanel.add(new JLabel("Type"));
        inputPanel.add(new JLabel("Reference"));
        inputPanel.add(new JLabel("Status"));

        inputPanel.add(txtAmount);
        inputPanel.add(txtDate);
        inputPanel.add(txtType);
        inputPanel.add(txtReference);
        inputPanel.add(txtStatus);

        add(inputPanel, BorderLayout.NORTH);

        // Table 
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Amount", "Date", "Type", "Reference", "Status"}, 0);
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

        // Load Button
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadInvoices();
            }
        });
        
        //Add Button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(txtAmount.getText());
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText());
                    InvoiceModel inv = new InvoiceModel(0, amount, date,
                            txtType.getText(), txtReference.getText(), txtStatus.getText());
                    if (invoiceDAO.addInvoice(inv)) loadInvoices();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Invoice.this, "Invalid input: " + ex.getMessage());
                }
            }
        });
        
        // Update Button
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int id = (int) tableModel.getValueAt(selectedRow, 0);
                        double amount = Double.parseDouble(txtAmount.getText());
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText());
                        InvoiceModel inv = new InvoiceModel(id, amount, date,
                                txtType.getText(), txtReference.getText(), txtStatus.getText());
                        if (invoiceDAO.updateInvoice(inv)) loadInvoices();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Invoice.this, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Invoice.this, "Select an invoice to update");
                }
            }
        });
         
        //Delete Button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    if (invoiceDAO.deleteInvoice(id)) loadInvoices();
                } else {
                    JOptionPane.showMessageDialog(Invoice.this, "Select an invoice to delete");
                }
            }
        });

        // table click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtAmount.setText(tableModel.getValueAt(r, 1).toString());
                txtDate.setText(tableModel.getValueAt(r, 2).toString());
                txtType.setText(tableModel.getValueAt(r, 3).toString());
                txtReference.setText(tableModel.getValueAt(r, 4).toString());
                txtStatus.setText(tableModel.getValueAt(r, 5).toString());
            }
        });

        setVisible(true);
        loadInvoices();
    }

    private void loadInvoices() {
        tableModel.setRowCount(0);
        List<InvoiceModel> list = invoiceDAO.getAllInvoices();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (InvoiceModel inv : list) {
            tableModel.addRow(new Object[]{
                    inv.getInvoiceID(),
                    inv.getAmount(),
                    df.format(inv.getDate()),
                    inv.getType(),
                    inv.getReference(),
                    inv.getStatus()
            });
        }
    }
}