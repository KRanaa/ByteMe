class Order{
    private static int idCounter=0;
    private int orderId;
    private String item;
    private String status;
    public boolean isVip;
    public int quantity;
    public Customer customer;

    public Order(String item,boolean isVip,int quantity,Customer customer){
        this.orderId=++idCounter;
        this.item=item;
        this.status="pending";
        this.isVip=isVip;
        this.quantity=quantity;
        this.customer=customer;
    }
    public int getOrderId(){ return orderId; }
    public String getItem(){ return item; }
    public String getStatus(){ return status; }
    public void setStatus(String status){this.status=status;}

    @Override
    public String toString(){return "Order ID: " + orderId + ", Item: " + item + ", Status: " + status;
    }
}