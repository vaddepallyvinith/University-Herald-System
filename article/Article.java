class Article {
    String name;
    String content;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String toString() {
        return "Title: " + name + "\nContent: " + content;
    }
}