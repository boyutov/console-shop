public class Shop {
    private static int nextId = 1;
    private int id;
    private String name;
    private String address;

    public Shop(String name, String address) {
        this.id = nextId++;
        this.name = name;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
}