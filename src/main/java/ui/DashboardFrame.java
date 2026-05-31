package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final Color DARK_GREEN = new Color(27, 94, 32);
    private final Color LIGHT_GREEN = new Color(232, 245, 233);

    public DashboardFrame() {
        setTitle("GreenLoop Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTabbedPanel(), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1200, 120));
        headerPanel.setBackground(new Color(250, 255, 250));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 220, 210)));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        leftPanel.setOpaque(false);

        JLabel logoLabel = new JLabel("♻");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 55));
        logoLabel.setForeground(DARK_GREEN);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("GreenLoop");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(DARK_GREEN);

        JLabel subtitleLabel = new JLabel("Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(new Color(76, 145, 65));

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        leftPanel.add(logoLabel);
        leftPanel.add(titlePanel);

        JLabel rightLabel = new JLabel("Eco Packaging Management");
        rightLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rightLabel.setForeground(new Color(100, 120, 100));
        rightLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JTabbedPane createTabbedPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));
        tabbedPane.setBackground(LIGHT_GREEN);
        tabbedPane.setForeground(DARK_GREEN);

        tabbedPane.addTab("Products", new ProductPanel());
        tabbedPane.addTab("Clients", new ClientPanel());
        tabbedPane.addTab("Inventory", new InventoryPanel());

        return tabbedPane;
    }
}