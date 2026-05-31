package ui;

import dao.InventoryDAO;
import model.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {

    private JTextField txtInventoryId;
    private JTextField txtProductName;
    private JTextField txtQuantity;
    private JTextField txtReorderLevel;
    private JTextField txtSupplierName;

    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    private final InventoryDAO inventoryDAO = new InventoryDAO();

    public InventoryPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadInventory();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Inventory Details"));

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtInventoryId = new JTextField();
        txtProductName = new JTextField();
        txtQuantity = new JTextField();
        txtReorderLevel = new JTextField();
        txtSupplierName = new JTextField();

        fieldsPanel.add(new JLabel("Inventory ID:"));
        fieldsPanel.add(txtInventoryId);

        fieldsPanel.add(new JLabel("Product Name:"));
        fieldsPanel.add(txtProductName);

        fieldsPanel.add(new JLabel("Quantity on Hand:"));
        fieldsPanel.add(txtQuantity);

        fieldsPanel.add(new JLabel("Reorder Level:"));
        fieldsPanel.add(txtReorderLevel);

        fieldsPanel.add(new JLabel("Supplier Name:"));
        fieldsPanel.add(txtSupplierName);

        fieldsPanel.add(new JLabel("Stock Status:"));
        fieldsPanel.add(new JLabel("Auto calculated"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAdd = createButton("Add Stock", new Color(46, 125, 50));
        JButton btnUpdate = createButton("Update", new Color(85, 139, 47));
        JButton btnDelete = createButton("Delete", new Color(198, 40, 40));
        JButton btnSearch = createButton("Search", new Color(80, 80, 80));
        JButton btnLowStock = createButton("Check Low Stock", new Color(230, 126, 34));
        JButton btnClear = createButton("Clear", new Color(100, 100, 100));
        JButton btnRefresh = createButton("Refresh", new Color(30, 110, 70));

        btnAdd.addActionListener(e -> addInventory());
        btnUpdate.addActionListener(e -> updateInventory());
        btnDelete.addActionListener(e -> deleteInventory());
        btnSearch.addActionListener(e -> searchInventory());
        btnLowStock.addActionListener(e -> checkLowStock());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadInventory());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnLowStock);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Inventory List"));

        String[] columns = {"ID", "Product Name", "Quantity", "Reorder Level", "Supplier", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.setRowHeight(28);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        inventoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });

        tablePanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        return tablePanel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(145, 35));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void addInventory() {
        try {
            Inventory inventory = getInventoryFromForm();

            if (inventoryDAO.inventoryIdExists(inventory.getInventoryId())) {
                JOptionPane.showMessageDialog(this, "Inventory ID already exists.");
                return;
            }

            inventoryDAO.addInventory(inventory);
            JOptionPane.showMessageDialog(this, "Inventory added successfully.");

            if (inventory.getQuantity() <= inventory.getReorderLevel()) {
                JOptionPane.showMessageDialog(this, "Warning: This product is low stock.");
            }

            loadInventory();
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateInventory() {
        try {
            Inventory inventory = getInventoryFromForm();

            if (!inventoryDAO.inventoryIdExists(inventory.getInventoryId())) {
                JOptionPane.showMessageDialog(this, "Inventory ID not found.");
                return;
            }

            inventoryDAO.updateInventory(inventory);
            JOptionPane.showMessageDialog(this, "Inventory updated successfully.");

            if (inventory.getQuantity() <= inventory.getReorderLevel()) {
                JOptionPane.showMessageDialog(this, "Warning: This product is low stock.");
            }

            loadInventory();
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteInventory() {
        String inventoryId = txtInventoryId.getText().trim();

        if (inventoryId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select or enter Inventory ID.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this inventory record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            inventoryDAO.deleteInventory(inventoryId);
            JOptionPane.showMessageDialog(this, "Inventory deleted successfully.");

            loadInventory();
            clearForm();
        }
    }

    private void searchInventory() {
        String keyword = JOptionPane.showInputDialog(this, "Enter inventory ID, product name, supplier, or status:");

        if (keyword == null) {
            return;
        }

        keyword = keyword.trim();

        if (keyword.isEmpty()) {
            loadInventory();
            return;
        }

        List<Inventory> inventoryList = inventoryDAO.searchInventory(keyword);
        loadTable(inventoryList);
    }

    private void checkLowStock() {
        List<Inventory> lowStockItems = inventoryDAO.getLowStockItems();

        if (lowStockItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No low-stock items found.");
            loadInventory();
            return;
        }

        loadTable(lowStockItems);

        StringBuilder message = new StringBuilder("Low-stock items:\n\n");

        for (Inventory item : lowStockItems) {
            message.append(item.getInventoryId())
                    .append(" - ")
                    .append(item.getProductName())
                    .append(" | Quantity: ")
                    .append(item.getQuantity())
                    .append(" | Reorder Level: ")
                    .append(item.getReorderLevel())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(this, message.toString());
    }

    private void loadInventory() {
        List<Inventory> inventoryList = inventoryDAO.getAllInventory();
        loadTable(inventoryList);
    }

    private void loadTable(List<Inventory> inventoryList) {
        tableModel.setRowCount(0);

        for (Inventory inventory : inventoryList) {
            tableModel.addRow(new Object[]{
                    inventory.getInventoryId(),
                    inventory.getProductName(),
                    inventory.getQuantity(),
                    inventory.getReorderLevel(),
                    inventory.getSupplierName(),
                    inventory.getStatus()
            });
        }
    }

    private Inventory getInventoryFromForm() {
        String inventoryId = txtInventoryId.getText().trim();
        String productName = txtProductName.getText().trim();
        String quantityText = txtQuantity.getText().trim();
        String reorderLevelText = txtReorderLevel.getText().trim();
        String supplierName = txtSupplierName.getText().trim();

        if (inventoryId.isEmpty()) {
            throw new RuntimeException("Inventory ID is required.");
        }

        if (productName.isEmpty()) {
            throw new RuntimeException("Product name is required.");
        }

        if (quantityText.isEmpty()) {
            throw new RuntimeException("Quantity is required.");
        }

        if (reorderLevelText.isEmpty()) {
            throw new RuntimeException("Reorder level is required.");
        }

        if (supplierName.isEmpty()) {
            throw new RuntimeException("Supplier name is required.");
        }

        int quantity;
        int reorderLevel;

        try {
            quantity = Integer.parseInt(quantityText);
            reorderLevel = Integer.parseInt(reorderLevelText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Quantity and reorder level must be valid numbers.");
        }

        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative.");
        }

        if (reorderLevel < 0) {
            throw new RuntimeException("Reorder level cannot be negative.");
        }

        return new Inventory(inventoryId, productName, quantity, reorderLevel, supplierName);
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = inventoryTable.getSelectedRow();

        if (selectedRow >= 0) {
            txtInventoryId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtProductName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtQuantity.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtReorderLevel.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtSupplierName.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    private void clearForm() {
        txtInventoryId.setText("");
        txtProductName.setText("");
        txtQuantity.setText("");
        txtReorderLevel.setText("");
        txtSupplierName.setText("");
        inventoryTable.clearSelection();
    }
}