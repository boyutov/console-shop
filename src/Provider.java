public class Provider extends User {
    public Provider(String login, String password, String name) {
        super(login, password, name, "Поставщик");
    }

    @Override
    public void info() {
        System.out.println("Поставщик: ID=" + getId() + ", Имя=" + getName());
    }
}