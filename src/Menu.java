import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

class Menu implements Serializable{
    public HashMap<String,HashMap<String,Object>> items=new HashMap<>();

    public void addItem(String name,double price,String category,boolean isAvailable){
        HashMap<String, Object> itemDetails=new HashMap<>();
        itemDetails.put("price",price);
        itemDetails.put("category",category);
        itemDetails.put("availability",isAvailable);
        items.put(name,itemDetails);
        System.out.println("Item added to the menu.");
    }
    public void updateItem(String name, double newPrice, boolean isAvailable) {
        if (items.containsKey(name.toLowerCase())) {
            HashMap<String, Object> itemDetails = items.get(name.toLowerCase());
            itemDetails.put("price", newPrice);
            itemDetails.put("availability", isAvailable);
            System.out.println("Item updated.");
        } else {
            System.out.println("Item not found.");
        }
    }
    public void removeItem(String name) {
        if (items.remove(name.toLowerCase()) != null) {
            System.out.println("Item removed.");
        } else {
            System.out.println("Item not found.");
        }
    }
    public void displayMenu(){
        if(items.isEmpty()){
            System.out.println("No items in the menu");
        }else{
        items.forEach((name,itemDetails)->{
            System.out.println("Name: " + name + ", Price: Rs." + itemDetails.get("price") +
                    ", Category: " + itemDetails.get("category") +
                    ", Available: " + itemDetails.get("availability"));
        });
    }}

    public boolean itemExists(String name) {
        return items.containsKey(name.toLowerCase());
    }
    public boolean isItemAvailable(String name) {
        if (items.containsKey(name.toLowerCase())) {
            return (boolean) items.get(name.toLowerCase()).get("availability");
        }
        return false;
    }
    public void filterByCategory(String category){
        boolean categoryExists = items.values().stream()
                .anyMatch(item -> category.equalsIgnoreCase((String) item.get("category")));
        if (!categoryExists) {
            System.out.println("Category doesn't exist.");
            return;
        }
        items.entrySet().stream()
                .filter(entry -> category.equalsIgnoreCase((String) entry.getValue().get("category")))
                .forEach(entry -> System.out.println("Name: " + entry.getKey() + ", Price: Rs." + entry.getValue().get("price")));
    }
    public void displayCategories(){
        Set<String> categories = items.values().stream()
                .map(item -> (String) item.get("category"))
                .collect(Collectors.toSet());

        System.out.println("Available Categories:");
        categories.forEach(System.out::println);
    }
    public void searchItem(String keyword) {
        items.entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyword.toLowerCase()))
                .forEach(entry -> System.out.println("Name: " + entry.getKey() + ", Price: $" + entry.getValue().get("price")));
    }
    public double getItemPrice(String name) {
        return (double) items.get(name.toLowerCase()).get("price");
    }

    public void sortByPrice(boolean ascending){
        if(items.isEmpty()){
            System.out.println("No items in teh menu");
        }else{
        List<Map.Entry<String, HashMap<String, Object>>> sortedItems=items.entrySet().stream()
                .sorted((entry1,entry2)->{
                    double price1=(double) entry1.getValue().get("price");
                    double price2=(double) entry2.getValue().get("price");
                    return ascending ? Double.compare(price1, price2) : Double.compare(price2, price1);
                })
                .collect(Collectors.toList());

        System.out.println("Menu sorted by price (" + (ascending ? "Ascending" : "Descending") + "):");
        sortedItems.forEach(entry ->{
            HashMap<String, Object> itemDetails = entry.getValue();
            System.out.println("Name: "+entry.getKey()+
                    ", Price: Rs."+itemDetails.get("price") +
                    ", Category: "+itemDetails.get("category") +
                    ", Available: "+itemDetails.get("availability"));
        });
    }}

    /*private void saveItemToFile(String name, double price, String category, boolean isAvailable) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) { // Append mode
            writer.write(name + "," + price + "," + category + "," + isAvailable + System.lineSeparator());
            System.out.println("Item details saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving item to file: " + e.getMessage());
        }
    }*/
}
