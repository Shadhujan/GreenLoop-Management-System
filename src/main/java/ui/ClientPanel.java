package ui;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientPanel extends JPanel {

    private JTextField txtClientId;
    private JTextField txtClientName;
    private JTextField txtBusinessName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextArea txtAddress;
    private JTable clientTable;
    private DefaultTableModel tableModel;

    private final ClientDAO clientDAO = new ClientDAO();

    public ClientPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadClients();
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
        txtAddress.setWrapStyleWord(true);

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

        JButton btnAdd = createButton("Add Client", new Color(46, 125, 50));
        JButton btnUpdate = createButton("Update", new Color(85, 139, 47));
        JButton btnDelete = createButton("Delete", new Color(198, 40, 40));
        JButton btnSearch = createButton("Search", new Color(80, 80, 80));
        JButton btnClear = createButton("Clear", new Color(100, 100, 100));
        JButton btnRefresh = createButton("Refresh", new Color(30, 110, 70));

        btnAdd.addActionListener(e -> addClient());
        btnUpdate.addActionListener(e -> updateClient());
        btnDelete.addActionListener(e -> deleteClient());
        btnSearch.addActionListener(e -> searchClient());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadClients());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Client List"));

        String[] columns = {"ID", "Client Name", "Business Name", "Phone", "Email", "Address"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        clientTable = new JTable(tableModel);
        clientTable.setRowHeight(28);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });

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

    private void addClient() {
        try {
            Client client = getClientFromForm();

            if (clientDAO.clientIdExists(client.getClientId())) {
                JOptionPane.showMessageDialog(this, "Client ID already exists.");
                return;
            }

            clientDAO.addClient(client);
            JOptionPane.showMessageDialog(this, "Client added successfully.");

            loadClients();
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateClient() {
        try {
            Client client = getClientFromForm();

            if (!clientDAO.clientIdExists(client.getClientId())) {
                JOptionPane.showMessageDialog(this, "Client ID not found.");
                return;
            }

            clientDAO.updateClient(client);
            JOptionPane.showMessageDialog(this, "Client updated successfully.");

            loadClients();
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteClient() {
        String clientId = txtClientId.getText().trim();

        if (clientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select or enter Client ID.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this client?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            clientDAO.deleteClient(clientId);
            JOptionPane.showMessageDialog(this, "Client deleted successfully.");

            loadClients();
            clearForm();
        }
    }

    private void searchClient() {
        String keyword = JOptionPane.showInputDialog(this, "Enter client ID, name, business name, phone, email, or address:");

        if (keyword == null) {
            return;
        }

        keyword = keyword.trim();

        if (keyword.isEmpty()) {
            loadClients();
            return;
        }

        List<Client> clients = clientDAO.searchClients(keyword);
        loadTable(clients);
    }

    private void loadClients() {
        List<Client> clients = clientDAO.getAllClients();
        loadTable(clients);
    }

    private void loadTable(List<Client> clients) {
        tableModel.setRowCount(0);

        for (Client client : clients) {
            tableModel.addRow(new Object[]{
                    client.getClientId(),
                    client.getClientName(),
                    client.getBusinessName(),
                    client.getPhone(),
                    client.getEmail(),
                    client.getAddress()
            });
        }
    }

    private Client getClientFromForm() {
        String clientId = txtClientId.getText().trim();
        String clientName = txtClientName.getText().trim();
        String businessName = txtBusinessName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        if (clientId.isEmpty()) {
            throw new RuntimeException("Client ID is required.");
        }

        if (clientName.isEmpty()) {
            throw new RuntimeException("Client name is required.");
        }

        if (businessName.isEmpty()) {
            throw new RuntimeException("Business name is required.");
        }

        if (phone.isEmpty()) {
            throw new RuntimeException("Phone number is required.");
        }

        if (email.isEmpty()) {
            throw new RuntimeException("Email is required.");
        }

        if (!email.contains("@")) {
            throw new RuntimeException("Email must contain @.");
        }

        return new Client(clientId, clientName, businessName, phone, email, address);
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = clientTable.getSelectedRow();

        if (selectedRow >= 0) {
            txtClientId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtClientName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtBusinessName.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhone.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtAddress.setText(tableModel.getValueAt(selectedRow, 5).toString());
        }
    }

    private void clearForm() {
        txtClientId.setText("");
        txtClientName.setText("");
        txtBusinessName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        clientTable.clearSelection();
    }
}