import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUI{
    private static final String MENU_FILE_NAME = "menu.txt";
    private static final String ORDERS_FILE_NAME = "orders.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Restaurant Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            JButton viewMenuButton = new JButton("View Menu");
            JButton viewPendingOrdersButton = new JButton("View Pending Orders");

            JTable table = new JTable();
            JScrollPane scrollPane = new JScrollPane(table);

            viewMenuButton.addActionListener(e -> {
                Object[][] menuData = readMenuData();
                String[] columnNames = {"Name", "Price", "Category", "Availability"};
                DefaultTableModel tableModel = new DefaultTableModel(menuData, columnNames);
                table.setModel(tableModel);
            });

            viewPendingOrdersButton.addActionListener(e -> {
                List<String[]> pendingOrders = readPendingOrders();
                String[] columnNames = {"Order ID", "Item", "Quantity", "Status", "Vip/Regular","Special Requests"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (String[] order : pendingOrders) {
                    tableModel.addRow(order);
                }

                table.setModel(tableModel);
            });

            panel.add(viewMenuButton);
            panel.add(viewPendingOrdersButton);
            frame.add(panel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
    private static Object[][] readMenuData() {
        List<Object[]> menuData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                String name = details[0];
                double price = Double.parseDouble(details[1]);
                String category = details[2];
                boolean isAvailable = Boolean.parseBoolean(details[3]);

                menuData.add(new Object[]{name, price, category, isAvailable});
            }
        } catch (IOException e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }

        return menuData.toArray(new Object[0][]);
    }

    private static List<String[]> readPendingOrders() {
        List<String[]> pendingOrders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                int orderId = Integer.parseInt(details[0]);
                String item = details[1];
                int quantity = Integer.parseInt(details[2]);
                String status = details[3];
                String isVip= "false".equalsIgnoreCase(details[4]) ? "Regular" : "Vip";
                String specialRequest = details[5];

                if ("Pending".equalsIgnoreCase(status)) {
                    pendingOrders.add(new String[]{
                            String.valueOf(orderId), item, String.valueOf(quantity), status, isVip, specialRequest
                    });
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file: " + e.getMessage());
        }
        return pendingOrders;
    }
}
