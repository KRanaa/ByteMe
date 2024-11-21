import javafx.application.Application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FoodOrderingSystem{
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        Map<String, Customer> customers=new HashMap<>();
        Customer activeCustomer;
        Menu menu=new Menu();
        OrderManager orderManager=new OrderManager();
        Admin admin=new Admin(menu,orderManager);

        menu.addItem("pizza",500,"meal",true);
        menu.addItem("coffee",30,"beverage",true);
        menu.addItem("maggi",20,"snacks",true);
        menu.addItem("fries",60,"snacks",true);
        while(true){
            //Application.launch(test.class);
            System.out.println("\nWelcome to Byte ME!!!");
            System.out.println("1. Admin  2. Customer  3. Exit");
            int c=scanner.nextInt();
            scanner.nextLine();

            switch(c){
                case 1:
                    boolean isAdmin=true;
                    while(isAdmin){
                        System.out.println("Admin Menu: \n1. Menu Management \n2. Order Management \n3. Report Generation \n4. Back to main menu");
                        int option=Integer.parseInt(scanner.nextLine().trim());
                        switch(option){
                        case 1->{
                        boolean adminMenu=true;
                        while(adminMenu){
                        System.out.println("Menu management options:\n1. Add Item\n2. Update Item\n3. Remove Item\n4. View Menu\n5. Back to Admin Menu");
                        int choice=Integer.parseInt(scanner.nextLine().trim());

                        switch(choice){
                            case 1->{
                                System.out.println("Enter item name:");
                                String name=scanner.nextLine();
                                System.out.println("Enter price:");
                                double price=Double.parseDouble(scanner.nextLine());
                                System.out.println("Enter category:");
                                String category=scanner.nextLine();
                                System.out.println("Is it available? (true/false)");
                                boolean isAvailable=Boolean.parseBoolean(scanner.nextLine());
                                admin.addItem(name,price,category,isAvailable);
                            }
                            case 2->{
                                System.out.println("Enter item name to update:");
                                String name=scanner.nextLine();
                                System.out.println("Enter new price:");
                                double price=Double.parseDouble(scanner.nextLine());
                                System.out.println("Is it available? (true/false)");
                                boolean isAvailable=Boolean.parseBoolean(scanner.nextLine());
                                admin.updateItem(name,price,isAvailable);
                            }
                            case 3->{
                                System.out.println("Enter item name to remove:");
                                String name=scanner.nextLine();
                                admin.removeItem(name);
                            }
                            case 4->admin.viewMenu();
                            case 5->adminMenu=false;
                            default->System.out.println("Invalid choice");}}
                        }
                        case 2 -> {
                            boolean orderManagement=true;
                            while(orderManagement){
                            System.out.println("Order Management Options:\n1. View Pending Orders\n2. Update Order Status\n3. Process Refund \n4. Handle special requests \n5. Exit Menu");
                            int choice=scanner.nextInt();
                            scanner.nextLine();

                             switch(choice){
                                        case 1->{
                                            orderManager.viewPendingOrders();
                                            }
                                        case 2->{
                                            System.out.println("Enter new status (Preparing/Out for Delivery/Completed):");
                                            String newStatus=scanner.nextLine();
                                            orderManager.updateOrderStatus(newStatus);
                                        }
                                        case 3->{
                                            orderManager.viewPendingRefunds();
                                            System.out.println("Enter Order ID to process refund:");
                                            int refundOrderId=scanner.nextInt();
                                            scanner.nextLine();
                                            orderManager.grantRefund(refundOrderId);
                                        }
                                        case 4->{ orderManager.handleSpecialRequests();}
                                        case 5-> orderManagement = false;
                                        default -> System.out.println("Invalid choice.");
                                    }}}
                            case 3->{orderManager.DailyReport(menu);}
                            case 4->{
                                isAdmin=false;
                            }
                            default->{
                                System.out.println("Invalid input");
                            }
                        }}
                    break;
                case 2:
                    System.out.println("Please enter your name:");
                    String name=scanner.nextLine();
                    activeCustomer=customers.get(name);
                    if (activeCustomer==null){
                        activeCustomer=new Customer(menu,orderManager,name);
                        customers.put(name,activeCustomer);
                        System.out.println("Welcome new customer!");
                    }else{
                        System.out.println("Welcome back, " + name + "!");
                    }

                    boolean isCustomer=true;
                    while(isCustomer){
                        System.out.println("Customer Menu:");
                        System.out.println("1. Browse Menu\n2. Cart Operations\n3. Get VIP \n4. Order Tracking \n5. Back to Main Menu");
                        int choice=Integer.parseInt(scanner.nextLine().trim());
                        switch(choice){
                            case 1->{
                            boolean browsing=true;
                            while(browsing){
                                System.out.println("Browse Menu:");
                                System.out.println("1. View Menu\n2. Search Item\n3. Filter by Category\n4. Sort by Price\n5. Back to Customer Menu");
                                int browseChoice=Integer.parseInt(scanner.nextLine().trim());

                                switch(browseChoice){
                                case 1 ->activeCustomer.browseMenu();
                                case 2 -> {
                                    System.out.println("Enter keyword to search:");
                                    String keyword=scanner.nextLine();
                                    activeCustomer.searchMenu(keyword);
                                }
                                case 3 ->{
                                    menu.displayCategories();
                                    System.out.println("Enter category to filter:");
                                    String category=scanner.nextLine();
                                    activeCustomer.filterMenuByCategory(category);
                                }
                                case 4 -> {
                                    System.out.println("Sort by price: 1. Ascending  2. Descending");
                                    int sortOrder=Integer.parseInt(scanner.nextLine());
                                    menu.sortByPrice(sortOrder==1);
                                }
                                case 5->browsing=false;
                                default->System.out.println("Invalid choice");
                            }}}
                            case 2->{
                            boolean inCart=true;
                            while(inCart){
                                System.out.println("Cart Options:");
                                System.out.println("1. Add Item\n2. Modify Item Quantity\n3. Remove Item\n4. View Cart\n5. View Total\n6. Checkout\n7. Back to Customer Menu");
                                int cartChoice = Integer.parseInt(scanner.nextLine().trim());
                                switch(cartChoice){
                                case 1 -> {
                                    System.out.println("Enter item name to add to cart:");
                                    String itemName = scanner.nextLine();
                                    System.out.println("Enter quantity:");
                                    int quantity = Integer.parseInt(scanner.nextLine());
                                    activeCustomer.addToCart(itemName, quantity);
                                }
                                case 2 -> {
                                    System.out.println("Enter item name to modify quantity:");
                                    String itemName = scanner.nextLine();
                                    System.out.println("Enter new quantity:");
                                    int quantity = Integer.parseInt(scanner.nextLine());
                                    activeCustomer.updateCart(itemName, quantity);
                                }
                                case 3 -> {
                                    System.out.println("Enter item name to remove:");
                                    String itemName = scanner.nextLine();
                                    activeCustomer.removeFromCart(itemName);
                                }
                                case 4 -> activeCustomer.viewCart();
                                case 5 -> activeCustomer.viewTotal();
                                case 6 -> activeCustomer.placeOrder();
                                case 7 -> inCart = false;
                                default -> System.out.println("Invalid choice");
                            }}}
                        case 3 -> {
                            System.out.println("Pay Rs 200 to get VIP");
                            System.out.println("Proceed with payment: true/false");
                            boolean amount=Boolean.parseBoolean(scanner.nextLine());
                            activeCustomer.becomeVip(amount);
                        }
                        case 4->{
                            boolean tracking=true;
                            while (tracking){
                                System.out.println("Order Tracking Options:");
                                System.out.println("1. View Order Status\n2. View Order History\n3. Cancel Order\n4. Back to Customer Menu");
                                int track=Integer.parseInt(scanner.nextLine().trim());

                                switch(track){
                                    case 1 -> {
                                        System.out.println("Enter Order ID to view status:");
                                        int orderId = Integer.parseInt(scanner.nextLine());
                                        activeCustomer.viewOrderStatus(orderId);
                                    }
                                    case 2 -> activeCustomer.viewOrderHistory();
                                    case 3 -> {
                                        System.out.println("Enter Order ID to cancel:");
                                        int orderId = Integer.parseInt(scanner.nextLine());
                                        activeCustomer.cancelOrder(orderId);
                                    }
                                    case 4 -> tracking = false;
                                    default -> System.out.println("Invalid choice");
                                }
                            }
                        }
                        case 5->isCustomer=false;
                        default->System.out.println("Invalid choice");
                        }}
                    break;
                case 3:
                    System.out.println("Exiting system...");
                    scanner.close();
                    saveMenuToFile(menu);
                    saveOrdersToFile(orderManager);
                    System.exit(0);
                default:
                    System.out.println("Invalid input");
            }}}

    private static void saveMenuToFile(Menu menu) {
        String filePath="menu.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, HashMap<String, Object>> entry : menu.items.entrySet()) {
                String itemName = entry.getKey();
                HashMap<String, Object> itemDetails = entry.getValue();
                String line = itemName + "," +
                        itemDetails.get("price") + "," +
                        itemDetails.get("category") + "," +
                        itemDetails.get("availability");
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Menu saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving menu to file: " + e.getMessage());
        }
    }

    private static void saveOrdersToFile(OrderManager orderManager){
        String ORDERS_FILE="orders.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            for(Order order:orderManager.regularOrders){
                String line = order.getOrderId() + "," + order.getItem() + "," + order.quantity + "," + order.getStatus() + "," + order.isVip;
                if(orderManager.specialRequests.containsKey(order.getItem())){
                    line += "," + orderManager.specialRequests.get(order.getItem());
                }
                line += "," +order.customer.customerName;
                writer.write(line);
                writer.newLine();
            }

            for (Order order : orderManager.vipOrders) {
                String line = order.getOrderId() + "," + order.getItem() + "," + order.quantity + "," + order.getStatus() + "," + order.isVip;
                if(orderManager.specialRequests.containsKey(order.getItem())) {
                    line += "," + orderManager.specialRequests.get(order.getItem());
                }
                line += "," +order.customer.customerName;
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Orders saved successfully to " + ORDERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving orders to file: " + e.getMessage());
        }
    }
}
