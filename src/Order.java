import java.util.ArrayList;

public class Order {
    private static int nextId = 1;
    private int id;
    private Customer customer;
    private ArrayList<Item> items;

    public Order(Customer customer, ArrayList<Item> items) {
        this.id = nextId++;
        this.customer = customer;
        this.items = new ArrayList<>(items);
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public ArrayList<Item> getItems() { return items; }
}