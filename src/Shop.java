import java.util.ArrayList;

public class Shop {
    private String name;
    private String address;
    private ArrayList<Item> items = new ArrayList<>(); // Список товаров
    private int employeeCount;
    private double averageRevenue;
    private int id;

    // Конструктор
    public Shop(String name, String address, int employeeCount, double averageRevenue, int id) {
        this.name = name;
        this.address = address;
        this.employeeCount = employeeCount;
        this.averageRevenue = averageRevenue;
        this.id = id;
    }

    public Shop(String shopName, String address) {
    }

    // Геттеры для получения информации
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getItemCount() { return items.size(); }
    public int getEmployeeCount() { return employeeCount; }
    public double getAverageRevenue() { return averageRevenue; }
    public int getId() { return  id; }
}