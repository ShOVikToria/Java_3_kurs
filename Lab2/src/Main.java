public class Main {
    public static void main(String[] args) {
        try {
            TextOperations operations = new TextOperations();
            operations.run();
        } catch (Exception e) {
            System.err.println("Критична помилка при запуску програми: " + e.getMessage());
            e.printStackTrace();
        }
    }
}