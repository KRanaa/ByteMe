import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Customer{
    public boolean isVip;
    private Cart cart;
    private OrderManager orderManager;
    private Menu menu;
    public List<Order> orderHistory;
    Scanner sc=new Scanner(System.in);
    public String customerName;

    public Customer(Menu menu,OrderManager orderManager, String customerName){
        this.menu=menu;
        this.orderManager=orderManager;
        this.cart=new Cart(menu,orderManager);
        this.isVip=false;
        this.orderHistory=new ArrayList<>();
        this.customerName=customerName;
    }
    public void becomeVip(boolean amount){
        if(amount){
            isVip=true;
            System.out.println("You are now a VIP!");
        }else{
            System.out.println("No VIP upgrade.");
    }}
    public void placeOrder(){
        if(cart.checkout()){
        for(Map.Entry<String,Integer> entry:cart.items.entrySet()){
            String item=entry.getKey();
            int quantity=entry.getValue();
            Order order=orderManager.createOrder(item,isVip,quantity,menu,this);
            orderHistory.add(order);
        }}
        cart.items.clear();
    }
    public void browseMenu(){
        menu.displayMenu();
    }
    public void searchMenu(String keyword) {
        menu.searchItem(keyword);
    }
    public void filterMenuByCategory(String category){
        menu.filterByCategory(category);
    }
    public void addToCart(String item, int quantity) {
        System.out.println("Would you like to add a special request for "+item+"? (yes/no)");
        String ans=sc.nextLine();
        String specialRequest=null;

        if(ans.equals("yes")){
            System.out.println("Enter your special request for " +item+ ":");
            specialRequest=sc.nextLine();
        }
        cart.addItem(item,quantity,specialRequest);
    }
    public void updateCart(String item, int quantity) {
        cart.updateQuantity(item, quantity);
    }
    public void removeFromCart(String item) {
        cart.removeItem(item);
    }
    public void viewCart() {
        cart.displayCart();
    }
    public void viewTotal() {
        System.out.println("Total: Rs." + cart.calculateTotal());
    }
    public void viewOrderStatus(int orderId) {
        Order order=orderManager.getOrderById(orderId);
        if (order != null) {
            System.out.println("Order ID: " + orderId + ", Status: " + order.getStatus()+ ", Item: " + order.getItem() + ", Quantity"+order.quantity);
        }else{
            System.out.println("Order not found.");
        }
    }
    public void cancelOrder(int orderId){
        Order order=orderManager.getOrderById(orderId);
        if (order!=null && (order.getStatus().equals("pending"))){
            orderManager.cancelOrder(orderId);
            orderManager.pendingRefunds.put(orderId,order);
            System.out.println("Order ID: " + orderId + "has been cancelled. Refund initiated");
        }else{
            System.out.println("Order cannot be cancelled.");
        }
    }
    /*public void viewOrderHistory(){
        System.out.println("Order History:");
        for (Order order:orderHistory){
            System.out.println("Order ID: " + order.getOrderId() + ", Item: " + order.getItem() + ", Quantity"+order.quantity);
        }
    }*/

    public void viewOrderHistory(){
        System.out.println("Order History for " + this.customerName + ":");
        String ORDERS_FILE="orders.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))){
            String line;
            while((line=reader.readLine())!=null){
                String[] details=line.split(",");
                String orderCustomerName=details[6];
                boolean a=this.customerName.equalsIgnoreCase(orderCustomerName);
                if(a){
                    int orderId=Integer.parseInt(details[0]);
                    String item=details[1];
                    int quantity=Integer.parseInt(details[2]);
                    String status=details[3];
                    String isVip=details[4];
                    String specialRequest=details[5];

                    System.out.println("Order ID: " + orderId + ", Item: " + item + ", Quantity: " + quantity +
                            ", Status: " + status + ", Special Request: " + specialRequest + ", VIP Status: " + isVip);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
    }
}
