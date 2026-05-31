package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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

        mainPanel.add(new HeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTabbedPanel(), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
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

    private class HeaderPanel extends JPanel {

        private Image logoImage;
        private Image leafImage;

        public HeaderPanel() {
            setPreferredSize(new Dimension(1200, 120));
            setBackground(new Color(250, 255, 250));
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 220, 210)));

            logoImage = loadImage("/images/greenloop_logo.png");
            leafImage = loadImage("/images/greenloop_leaf.png");
        }

        private Image loadImage(String path) {
            URL imageUrl = getClass().getResource(path);

            if (imageUrl == null) {
                System.out.println("Image not found: " + path);
                return null;
            }

            return new ImageIcon(imageUrl).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Soft background
            GradientPaint gradient = new GradientPaint(
                    0, 0, Color.WHITE,
                    getWidth(), getHeight(), new Color(245, 252, 245)
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Right leaf decoration
            if (leafImage != null) {
                g2.drawImage(leafImage, getWidth() - 330, -60, 330, 240, this);
            }

            // Left logo
            if (logoImage != null) {
                g2.drawImage(logoImage, 25, 25, 330, 75, this);
            } else {
                // fallback text if image is missing
                g2.setColor(DARK_GREEN);
                g2.setFont(new Font("Arial", Font.BOLD, 36));
                g2.drawString("GreenLoop", 40, 55);

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.drawString("Management System", 42, 85);
            }
        }
    }
}