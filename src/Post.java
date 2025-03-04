public class Post {
    private String content;
    private String tag;
    private User author;

    public Post(String content, String tag, User author) {
        this.content = content;
        this.tag = tag;
        this.author = author;
    }

    public String getContent() { return content; }
    public String getTag() { return tag; }
    public User getAuthor() { return author; }
}