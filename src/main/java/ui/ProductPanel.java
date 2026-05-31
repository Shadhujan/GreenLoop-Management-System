package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {

    private JTextField txtProductId;
    private JTextField txtProductName;
    private JTextField txtCategory;
    private JTextField txtPrice;
    private JComboBox<String> cmbEcoRating;
    private JTextArea txtDescription;
    private JTable productTable;

    public ProductPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtProductId = new JTextField();
        txtProductName = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        cmbEcoRating = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);

        fieldsPanel.add(new JLabel("Product ID:"));
        fieldsPanel.add(txtProductId);

        fieldsPanel.add(new JLabel("Product Name:"));
        fieldsPanel.add(txtProductName);

        fieldsPanel.add(new JLabel("Category:"));
        fieldsPanel.add(txtCategory);

        fieldsPanel.add(new JLabel("Price:"));
        fieldsPanel.add(txtPrice);

        fieldsPanel.add(new JLabel("Eco Rating:"));
        fieldsPanel.add(cmbEcoRating);

        fieldsPanel.add(new JLabel("Description:"));
        fieldsPanel.add(new JScrollPane(txtDescription));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(createButton("Add Product", new Color(46, 125, 50)));
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
        tablePanel.setBorder(BorderFactory.createTitledBorder("Product List"));

        String[] columns = {"ID", "Name", "Category", "Price", "Eco Rating", "Description"};

        Object[][] data = {
                {"P001", "Biodegradable Food Wrap", "Wraps", "12.50", "5", "Plant-based food wrap"},
                {"P002", "Recycled Cardboard Box", "Boxes", "8.75", "4", "Recycled cardboard box"},
                {"P003", "Compostable Trash Bag", "Bags", "18.50", "5", "Compostable trash bags"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        productTable = new JTable(model);
        productTable.setRowHeight(28);

        tablePanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

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