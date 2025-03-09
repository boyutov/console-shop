public class Salesman extends User {
    public Salesman(String login, String password, String name) {
        super(login, password, name, "Продавец");
    }

    @Override
    public void info() {
        System.out.println("Продавец: ID=" + getId() + ", Имя=" + getName());
    }
}