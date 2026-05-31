package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryPanel extends JPanel {

    private JTextField txtInventoryId;
    private JTextField txtProductName;
    private JTextField txtQuantity;
    private JTextField txtReorderLevel;
    private JTextField txtSupplierName;
    private JTable inventoryTable;

    public InventoryPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
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

        buttonPanel.add(createButton("Add Stock", new Color(46, 125, 50)));
        buttonPanel.add(createButton("Update", new Color(85, 139, 47)));
        buttonPanel.add(createButton("Delete", new Color(198, 40, 40)));
        buttonPanel.add(createButton("Search", new Color(80, 80, 80)));
        buttonPanel.add(createButton("Check Low Stock", new Color(230, 126, 34)));
        buttonPanel.add(createButton("Clear", new Color(100, 100, 100)));

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Inventory List"));

        String[] columns = {"ID", "Product Name", "Quantity", "Reorder Level", "Supplier", "Status"};

        Object[][] data = {
                {"INV001", "Biodegradable Food Wrap", "320", "100", "GreenGoods Co.", "Available"},
                {"INV002", "Recycled Cardboard Box", "45", "50", "EcoPack Solutions", "Low Stock"},
                {"INV003", "Compostable Trash Bag", "150", "50", "EcoPack Solutions", "Available"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        inventoryTable = new JTable(model);
        inventoryTable.setRowHeight(28);

        tablePanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        return tablePanel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 35));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}