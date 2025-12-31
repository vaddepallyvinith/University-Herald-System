/*import java.util.*;

public class UoHHeraldSystem {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, String> facultyAccounts = new HashMap<>();
    static ArrayList<Article> articles = new ArrayList<>();

    public static void main(String[] args) {
        facultyAccounts.put("admin01", "Admin");
        facultyAccounts.put("fac123", "Faculty");
        facultyAccounts.put("fac456", "Faculty");

        System.out.println("Welcome to UoH Herald System");
        System.out.print("Enter your ID to authenticate: ");
        String id = sc.nextLine();

        if (facultyAccounts.containsKey(id)) {
            System.out.println("Authenticated as " + facultyAccounts.get(id));
            menu();
        } else {
            System.out.println("Authentication failed! Access denied.");
        }
    }

    static void menu() {
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Create Article");
            System.out.println("2. Edit Article");
            System.out.println("3. Delete Article");
            System.out.println("4. View Articles");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    createArticle();
                    break;
                case 2:
                    editArticle();
                    break;
                case 3:
                    deleteArticle();
                    break;
                case 4:
                    viewArticles();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    static void createArticle() {
        System.out.print("Enter article name: ");
        String name = sc.nextLine();
        System.out.print("Enter article content: ");
        String content = sc.nextLine();
        articles.add(new Article(name, content));
        System.out.println("Article added successfully.");
    }

    static void editArticle() {
        System.out.print("Enter article name to edit: ");
        String name = sc.nextLine();
        for (Article article : articles) {
            if (article.name.equalsIgnoreCase(name)) {
                System.out.print("Enter new content: ");
                String newContent = sc.nextLine();
                article.content = newContent;
                System.out.println("Article updated.");
                return;
            }
        }
        System.out.println("Article not found.");
    }

    static void deleteArticle() {
        System.out.print("Enter article name to delete: ");
        String name = sc.nextLine();
        Iterator<Article> iterator = articles.iterator();
        while (iterator.hasNext()) {
            Article article = iterator.next();
            if (article.name.equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("Article deleted.");
                return;
            }
        }
        System.out.println("Article not found.");
    }

    static void viewArticles() {
        if (articles.isEmpty()) {
            System.out.println("No articles to display.");
            return;
        }
        System.out.println("\n--- Articles ---");
        for (Article article : articles) {
            System.out.println(article + "\n");
        }
    }
}
*/