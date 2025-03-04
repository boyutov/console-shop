public abstract class User {
    private String login;
    private String password;
    private String name;
    private int id;
    private static int nextId = 1;

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.id = nextId++;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract String getRole();
    public abstract void info();
}