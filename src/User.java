public abstract class User {
    private static int nextId = 1;
    private int id;
    private String login;
    private String password;
    private String name;
    private String role;

    public User(String login, String password, String name, String role) {
        this.id = nextId++;
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getRole() { return role; }

    public abstract void info();
}