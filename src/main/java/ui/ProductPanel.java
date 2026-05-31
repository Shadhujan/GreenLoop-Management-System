package ui;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {

    private JTextField txtProductId;
    private JTextField txtProductName;
    private JTextField txtCategory;
    private JTextField txtPrice;
    private JComboBox<String> cmbEcoRating;
    private JTextArea txtDescription;
    private JTable productTable;
    private DefaultTableModel tableModel;

    private final ProductDAO productDAO = new ProductDAO();

    public ProductPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadProducts();
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
        txtDescription.setWrapStyleWord(true);

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

        JButton btnAdd = createButton("Add Product", new Color(46, 125, 50));
        JButton btnUpdate = createButton("Update", new Color(85, 139, 47));
        JButton btnDelete = createButton("Delete", new Color(198, 40, 40));
        JButton btnSearch = createButton("Search", new Color(80, 80, 80));
        JButton btnClear = createButton("Clear", new Color(100, 100, 100));
        JButton btnRefresh = createButton("Refresh", new Color(30, 110, 70));

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnSearch.addActionListener(e -> searchProduct());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadProducts());

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
        tablePanel.setBorder(BorderFactory.createTitledBorder("Product List"));

        String[] columns = {"ID", "Name", "Category", "Price", "Eco Rating", "Description"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setRowHeight(28);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });

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

    private void addProduct() {
        try {
            Product product = getProductFromForm();

            if (productDAO.productIdExists(product.getProductId())) {
                JOptionPane.showMessageDialog(this, "Product ID already exists.");
                return;
            }

            productDAO.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully.");

            loadProducts();
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateProduct() {
        try {
            Product product = getProductFromForm();

            if (!productDAO.productIdExists(product.getProductId())) {
                JOptionPane.showMessageDialog(this, "Product ID not found.");
                return;
            }

            productDAO.updateProduct(product);
            JOptionPane.showMessageDialog(this, "Product updated successfully.");

            loadProducts();
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteProduct() {
        String productId = txtProductId.getText().trim();

        if (productId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select or enter Product ID.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            productDAO.deleteProduct(productId);
            JOptionPane.showMessageDialog(this, "Product deleted successfully.");

            loadProducts();
            clearForm();
        }
    }

    private void searchProduct() {
        String keyword = JOptionPane.showInputDialog(this, "Enter product ID, name, category, or description:");

        if (keyword == null) {
            return;
        }

        keyword = keyword.trim();

        if (keyword.isEmpty()) {
            loadProducts();
            return;
        }

        List<Product> products = productDAO.searchProducts(keyword);
        loadTable(products);
    }

    private void loadProducts() {
        List<Product> products = productDAO.getAllProducts();
        loadTable(products);
    }

    private void loadTable(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getEcoRating(),
                    product.getDescription()
            });
        }
    }

    private Product getProductFromForm() {
        String productId = txtProductId.getText().trim();
        String name = txtProductName.getText().trim();
        String category = txtCategory.getText().trim();
        String priceText = txtPrice.getText().trim();
        int ecoRating = Integer.parseInt(cmbEcoRating.getSelectedItem().toString());
        String description = txtDescription.getText().trim();

        if (productId.isEmpty()) {
            throw new RuntimeException("Product ID is required.");
        }

        if (name.isEmpty()) {
            throw new RuntimeException("Product name is required.");
        }

        if (category.isEmpty()) {
            throw new RuntimeException("Category is required.");
        }

        if (priceText.isEmpty()) {
            throw new RuntimeException("Price is required.");
        }

        double price;

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Price must be a valid number.");
        }

        if (price <= 0) {
            throw new RuntimeException("Price must be greater than 0.");
        }

        return new Product(productId, name, category, price, ecoRating, description);
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow >= 0) {
            txtProductId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtProductName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtCategory.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
            cmbEcoRating.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
            txtDescription.setText(tableModel.getValueAt(selectedRow, 5).toString());
        }
    }

    private void clearForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        cmbEcoRating.setSelectedIndex(0);
        txtDescription.setText("");
        productTable.clearSelection();
    }
}