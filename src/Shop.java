import java.util.ArrayList;

public class Shop {
    private String name;
    private String address;
    private ArrayList<Item> items = new ArrayList<>();
    private int id;
    private static int nextId = 1;

    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
        this.id = nextId++;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getId() { return id; }
    public ArrayList<Item> getItems() { return items; }
}