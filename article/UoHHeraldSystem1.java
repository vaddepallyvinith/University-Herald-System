import java.util.*;
import java.io.*;
public class UoHHeraldSystem1{
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> users = new HashMap<>();
    static List<Article> articles = new ArrayList<>();
    
    static final String USERS_FILE = "users.txt";
    static final String ARTICLES_FILE = "articles.txt";


    public static void main(String[] args) {
        //loadUsers();    //load users from file
        loadArticles(); //load articles from file

        
        // Sample users
        users.put("admin01", new User("admin01", "adminpass", "Admin"));
        users.put("fac123", new User("fac123", "facpass1", "Faculty"));
        users.put("fac456", new User("fac456", "facpass2", "Faculty"));

        System.out.println("Welcome to UoH Herald System");
        System.out.print("Enter your role (Admin / Faculty / Viewer): ");
        String role = sc.nextLine().trim();

        switch (role.toLowerCase()) {
            case "admin":
            case "faculty":
                authenticateAndLaunch(role);
                break;
            case "viewer":
                viewArticles();
                break;
            default:
                System.out.println("Invalid role. Exiting.");
        }
    }

    static void authenticateAndLaunch(String role) {
        System.out.print("Enter your User ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter your Password: ");
        String password = sc.nextLine().trim();

        User user = users.get(id);
        if (user != null && user.password.equals(password) && user.role.equalsIgnoreCase(role)) {
            System.out.println("Authenticated as " + user.role);
            menu(user);
        } else {
            System.out.println("Authentication failed. Access denied.");
        }
    }

    static void menu(User user) {
        while (true) {
            System.out.println("\n--- MENU (" + user.role + ") ---");
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
        System.out.print("Enter article title: ");
        String name = sc.nextLine();
        System.out.print("Enter article content: ");
        String content = sc.nextLine();
        articles.add(new Article(name, content));
        saveArticles();
        System.out.println("Article created successfully.");
    }


    static void editArticle() {
        System.out.print("Enter article title to edit: ");
        String name = sc.nextLine();
        for (Article article : articles) {
            if (article.name.equalsIgnoreCase(name)) {
                System.out.print("Enter new content: ");
                article.content = sc.nextLine();
                saveArticles();
                System.out.println("Article updated.");
                return;
            }
        }
        System.out.println("Article not found.");
    }


    static void deleteArticle() {
        System.out.print("Enter article title to delete: ");
        String name = sc.nextLine();
        Iterator<Article> iterator = articles.iterator();
        while (iterator.hasNext()) {
            Article article = iterator.next();
            if (article.name.equalsIgnoreCase(name)) {
                iterator.remove();
                saveArticles(); 
                System.out.println("Article deleted.");
                return;
            }
        }
        System.out.println("Article not found.");
    }
    

    static void viewArticles() {
        System.out.println("\n--- Articles ---");
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
        } else {
            for (Article article : articles) {
                System.out.println(article + "\n");
            }
        }
    }
    
    // ----------- FILE METHODS ------------

    /*static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.strip().split(",");
                if (parts.length == 3) {
                    users.put(parts[0], new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No users file found. Creating default users...");
            // Create some default users
            users.put("admin01", new User("admin01", "adminpass", "Admin"));
            users.put("fac123", new User("fac123", "facpass1", "Faculty"));
            users.put("fac456", new User("fac456", "facpass2", "Faculty"));
            saveUsers();
        }
    }

    static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                bw.write(user.id + "," + user.password + "," + user.role);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save users.");
        }
    }*/

    static void loadArticles() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARTICLES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.strip().split("\\|\\|\\|");
                if (parts.length == 2) {
                    articles.add(new Article(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No articles file found. Starting fresh.");
        }
    }

    static void saveArticles() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARTICLES_FILE))) {
            for (Article article : articles) {
                bw.write(article.name + "|||" + article.content);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save articles.");
        }
    }
}