class Admin{
    private Menu menu;
    private OrderManager orderManager;

    public Admin(Menu menu,OrderManager orderManager){
        this.menu=menu;
        this.orderManager=orderManager;
    }

    public void addItem(String name,double price,String category,boolean isAvailable){
        menu.addItem(name,price,category,isAvailable);
    }
    public void updateItem(String name,double price,boolean isAvailable){
        menu.updateItem(name,price,isAvailable);
    }
    public void removeItem(String name){
        menu.removeItem(name);
        for(Order order:orderManager.vipOrders){
            if (order.getItem().equals(name)){
                order.setStatus("denied");
                System.out.println("VIP Order ID: "+order.getOrderId()+" status set to denied");
            }}
        for (Order order:orderManager.regularOrders){
            if (order.getItem().equals(name)){
                order.setStatus("denied");
                System.out.println("Regular Order ID: " + order.getOrderId() + " status set to denied");
            }}
    }
    public void viewMenu() {
        menu.displayMenu();
    }
}