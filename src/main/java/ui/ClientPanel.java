package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientPanel extends JPanel {

    private JTextField txtClientId;
    private JTextField txtClientName;
    private JTextField txtBusinessName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextArea txtAddress;
    private JTable clientTable;

    public ClientPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Client Details"));

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtClientId = new JTextField();
        txtClientName = new JTextField();
        txtBusinessName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtAddress = new JTextArea(3, 20);
        txtAddress.setLineWrap(true);

        fieldsPanel.add(new JLabel("Client ID:"));
        fieldsPanel.add(txtClientId);

        fieldsPanel.add(new JLabel("Client Name:"));
        fieldsPanel.add(txtClientName);

        fieldsPanel.add(new JLabel("Business Name:"));
        fieldsPanel.add(txtBusinessName);

        fieldsPanel.add(new JLabel("Phone:"));
        fieldsPanel.add(txtPhone);

        fieldsPanel.add(new JLabel("Email:"));
        fieldsPanel.add(txtEmail);

        fieldsPanel.add(new JLabel("Address:"));
        fieldsPanel.add(new JScrollPane(txtAddress));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(createButton("Add Client", new Color(46, 125, 50)));
        buttonPanel.add(createButton("Update", new Color(85, 139, 47)));
        buttonPanel.add(createButton("Delete", new Color(198, 40, 40)));
        buttonPanel.add(createButton("Search", new Color(80, 80, 80)));
        buttonPanel.add(createButton("Clear", new Color(100, 100, 100)));

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Client List"));

        String[] columns = {"ID", "Client Name", "Business Name", "Phone", "Email", "Address"};

        Object[][] data = {
                {"C001", "John Smith", "Smith Organics", "0771234567", "john@email.com", "Colombo"},
                {"C002", "Maria Perera", "Green Mart", "0779876543", "maria@email.com", "Kandy"},
                {"C003", "Eco Shop", "Eco Shop Lanka", "0712223333", "eco@email.com", "Galle"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        clientTable = new JTable(model);
        clientTable.setRowHeight(28);

        tablePanel.add(new JScrollPane(clientTable), BorderLayout.CENTER);

        return tablePanel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(130, 35));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}