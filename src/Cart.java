import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Cart {
    public Map<String,Integer> items=new HashMap<>();
    private Menu menu;
    private OrderManager orderManager;

    public Cart(Menu menu,OrderManager orderManager){
        this.menu=menu;
        this.orderManager=orderManager;
    }
    public void addItem(String name,int quantity,String request){
        if(menu.itemExists(name)){
            items.put(name,items.getOrDefault(name,0)+quantity);
            if(request!=null){
                orderManager.specialRequests.put(name,request);
            }else{
                orderManager.specialRequests.put(name,"No special request");
            }
            System.out.println("Added "+quantity+" of "+name+" to cart.");
        }else{
            System.out.println("Item not found in menu.");
        }
    }
    public void updateQuantity(String name,int quantity){
        if(items.containsKey(name)){
            items.put(name,quantity);
            System.out.println("Updated quantity of " + name + " to " + quantity + ".");
        }else{
            System.out.println("Item not in cart.");
        }
    }
    public void removeItem(String name) {
        if (items.remove(name) != null) {
            System.out.println(name + " removed from cart.");
        } else {
            System.out.println("Item not in cart.");
        }
    }
    public double calculateTotal(){
        double total=0;
        for(Map.Entry<String, Integer> entry:items.entrySet()){
            total+=menu.getItemPrice(entry.getKey())*entry.getValue();
        }
        return total;
    }
    public void displayCart(){
        if(items.isEmpty()){
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Cart items:");
            items.forEach((name, quantity) -> System.out.println(name + " x " + quantity));
        }
    }
    public boolean checkout() {
        if(items.isEmpty()){
            System.out.println("Cart is empty");
            return false;
        }
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter payment method (Credit Card/Debit Card/UPI/Cash):");
        String paymentMethod = scanner.nextLine();
        System.out.println("Enter payment confirmation (yes/no):");
        String paymentConfirmation=scanner.nextLine();

        if (!paymentConfirmation.equalsIgnoreCase("yes")){
            System.out.println("Checkout cancelled.");
            return false;
        }
        System.out.println("Enter delivery address(Hostel and room no.):");
        String address = scanner.nextLine();
        System.out.println("Enter contact number:");
        String contactNumber = scanner.nextLine();

        System.out.println("Order Summary:");
        displayCart();
        System.out.println("Total Amount: Rs." + calculateTotal());
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Delivery Address: " + address);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("\nOrder placed successfully!");
        return true;
    }
}