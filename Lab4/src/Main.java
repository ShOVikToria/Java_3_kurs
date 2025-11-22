import java.util.Scanner;

/**
 * Головний клас програми для виконання лабораторної роботи №4.
 * Реалізує взаємодію з користувачем через консоль: зчитування тексту
 * та виклик методів обробки тексту.
 */
public class Main {

    /**
     * Точка входу в програму.
     * Зчитує текст із стандартного потоку введення до моменту введення порожнього рядка,
     * створює об'єкт структури Text та виводить результат сортування.
     *
     * @param args аргументи командного рядка (не використовуються).
     */
    public static void main(String[] args) {
        try {
            // Використовуємо Scanner для зручного зчитування рядків
            Scanner scanner = new Scanner(System.in, "UTF-8");
            System.out.println("Введіть текст (натисніть Enter двічі для завершення введення):");

            StringBuilder sb = new StringBuilder();

            // Цикл читає рядки, доки користувач не введе порожній рядок
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Якщо рядок порожній (користувач просто натиснув Enter) — виходимо з циклу
                if (line.isEmpty()) {
                    break;
                }

                sb.append(line).append(" ");
            }

            String rawText = sb.toString();

            // Перевірка, чи було введено хоч щось окрім пробілів
            if (rawText.trim().isEmpty()) {
                System.out.println("Текст не введено.");
                return;
            }

            // Ініціалізація моделі тексту (логіка розбору знаходиться у класі Text)
            Text text = new Text(rawText);

            System.out.println("\n=== Відформатований текст ===");
            System.out.println(text);

            System.out.println("\n=== Результат сортування слів за кількістю голосних ===");
            text.printSortedWordsByVowels();

        } catch (Exception e) {
            System.err.println("Виникла критична помилка під час виконання програми: " + e.getMessage());
            e.printStackTrace();
        }
    }
}