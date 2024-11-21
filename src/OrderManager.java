import java.util.*;

class OrderManager{
    public Queue<Order> regularOrders=new LinkedList<>();
    public Queue<Order> vipOrders=new LinkedList<>();
    public Map<Integer, Order> pendingRefunds= new HashMap<>();
    public double totalSales;
    public Map<String, String> specialRequests=new HashMap<>();

    public Order createOrder(String item,boolean isVip,int quantity, Menu menu, Customer customer){
        Order order=new Order(item,isVip,quantity,customer);
        if(isVip){
            vipOrders.add(order);
        }else{
            regularOrders.add(order);
        }
        return order;
    }

    public void updateOrderStatus(String newStatus){
        for(Order order:vipOrders){
            if(!order.getStatus().equals("Completed")&&
                    !order.getStatus().equals("denied")&&
                    !order.getStatus().equals("Cancelled")){

                order.setStatus(newStatus);
                System.out.println("VIP Order ID: "+order.getOrderId()+" status updated to "+newStatus);
                return;
            }}
        for(Order order:regularOrders){
            if(!order.getStatus().equals("Completed")&&
                    !order.getStatus().equals("denied")&&
                    !order.getStatus().equals("Cancelled"));
                order.setStatus(newStatus);
                System.out.println("Regular Order ID: " + order.getOrderId() + " status updated to " + newStatus);
                return;
            }
        System.out.println("No pending orders available to update.");
    }
    public void displayOrders(){
        System.out.println("VIP Orders:");
        vipOrders.forEach(order -> System.out.println("Order ID: " + order.getOrderId() + ", Item: " + order.getItem()));
        System.out.println("Regular Orders:");
        regularOrders.forEach(order -> System.out.println("Order ID: " + order.getOrderId() + ", Item: " + order.getItem()));
    }
    public void processNextOrder(){
        Order nextOrder=!vipOrders.isEmpty()?vipOrders.poll():regularOrders.poll();
        if(nextOrder!=null){
            System.out.println("Processed Order ID: " + nextOrder.getOrderId() + " for " + nextOrder.getItem() + " (VIP: " + ")");
        }else{
            System.out.println("No more orders to process.");
        }
    }
    public void viewPendingOrders() {
        System.out.println("Pending Orders: ");
        System.out.println("VIP Orders:");
        boolean vipOrdersPending=false;
        for(Order vipOrder:vipOrders){
            if (vipOrder.getStatus().equalsIgnoreCase("Pending")){
                System.out.println("Order ID: " + vipOrder.getOrderId() + ", Item: " + vipOrder.getItem()+ ", Item: " + ", Quantity"+vipOrder.quantity);
                vipOrdersPending = true;
            }
        }
        if(!vipOrdersPending){
            System.out.println("No pending VIP orders.");
        }
        System.out.println("Regular Orders:");
        boolean regularOrdersPending=false;
        for(Order regularOrder:regularOrders){
            if (regularOrder.getStatus().equalsIgnoreCase("Pending")) {
                System.out.println("Order ID: "+regularOrder.getOrderId()+", Item: "+regularOrder.getItem() +", Quantity"+regularOrder.quantity);
                regularOrdersPending=true;
            }
        }
        if (!regularOrdersPending) {
            System.out.println("No pending regular orders.");
        }
    }
    public Order getOrderById(int orderId) {
        for (Order order : vipOrders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        for (Order order : regularOrders) {
            if (order.getOrderId()==orderId){
                return order;
            }
        }
        return null;
    }
    public void cancelOrder(int orderId){
        Order order=getOrderById(orderId);
        if(order!=null){
            order.setStatus("Cancelled");
        }
    }
    public void viewPendingRefunds() {
        if (pendingRefunds.isEmpty()) {
            System.out.println("No pending refunds.");
            return;
        }
        System.out.println("Pending Refunds:");
        for (Order order : pendingRefunds.values()) {
            System.out.println("Order ID: " + order.getOrderId());
        }
    }
    public void grantRefund(int orderId){
        Order order = pendingRefunds.get(orderId);
        if (order != null) {
            pendingRefunds.remove(orderId);
            System.out.println("Refund granted for Order ID: " + orderId);
        } else {
            System.out.println("Invalid Order ID or refund has already been processed.");
        }
    }

    public void DailyReport(Menu menu){
        int totalOrders=0;
        System.out.println("Daily Sales Report:");
        for(Order order:vipOrders){
            if(!order.getStatus().equalsIgnoreCase("denied") && !order.getStatus().equalsIgnoreCase("Cancelled")){
                double price=menu.getItemPrice(order.getItem());
                totalSales+=price*(order.quantity);
                totalOrders++;
            }
        }
        for(Order order:regularOrders){
            if(!order.getStatus().equalsIgnoreCase("denied") && !order.getStatus().equalsIgnoreCase("Cancelled")){
                double price=menu.getItemPrice(order.getItem());
                totalSales+=price*(order.quantity);
                totalOrders++;
            }
        }
        System.out.println("Total Sales Amount: Rs."+totalSales);

        System.out.println("Total Orders: "+totalOrders);

        Map<String, Integer> itemPopularity=new HashMap<>();
        for (Order order:vipOrders){
            if(!order.getStatus().equalsIgnoreCase("denied") && !order.getStatus().equalsIgnoreCase("Cancelled")){
            itemPopularity.put(order.getItem(),itemPopularity.getOrDefault(order.getItem(), 0) + order.quantity);
        }}
        for (Order order : regularOrders){
            if(!order.getStatus().equalsIgnoreCase("denied") && !order.getStatus().equalsIgnoreCase("Cancelled")){
            itemPopularity.put(order.getItem(),itemPopularity.getOrDefault(order.getItem(), 0) + order.quantity);
        }}

        List<String> popularItems=new ArrayList<>();
        int maxQuantity=0;
        for(Map.Entry<String, Integer>entry:itemPopularity.entrySet()){
            int quantity=entry.getValue();
            if(quantity>maxQuantity){
                maxQuantity=quantity;
                popularItems.clear();
                popularItems.add(entry.getKey());
            }else if(quantity==maxQuantity){
                popularItems.add(entry.getKey());
        }}
        System.out.println("Most Popular Item(s): "+(popularItems.isEmpty()?"None":String.join(", ",popularItems)) + " (Quantity: " +maxQuantity+ ")");
}
    public void handleSpecialRequests(){
        System.out.println("Orders with Special Requests:");
        if (!specialRequests.isEmpty()){
            for(Map.Entry<String, String> entry : specialRequests.entrySet()){
                String name= entry.getKey();
                String request=entry.getValue();
                System.out.println(name+ ": Special Request- " + request);
            }
        }else{
            System.out.println("No special requests found.");
        }
    }

    /*private void saveOrderToFile(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            writer.write(order.getOrderId() + "," + order.getItem() + "," + order.isVip + "," + order.quantity + "," + order.getStatus());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to orders file: " + e.getMessage());
        }
    }*/
}