import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextOperations {

    // Голосні: українські + латинські
    private static final String VOWELS = "аеиіоуюєяїАЕИІОУЮЄЯЇAEYUIOaeyuio";
    private static final String PROMPT = "Введіть текст (порожній рядок (подвійний Enter) — кінець введення):\n> ";

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            // Ввід тексту
            StringBuilder inputBuilder = new StringBuilder();
            System.out.print(PROMPT);
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                inputBuilder.append(line).append("\n");
                System.out.print("> ");
            }

            if (inputBuilder.length() == 0) {
                System.out.println("Текст не введено. Завершення.");
                return;
            }

            StringBuffer textBuffer = new StringBuffer(inputBuilder.toString());
            String originalText = textBuffer.toString();

            System.out.println("\nВведений текст:");
            System.out.println(originalText.trim());
            System.out.println();

            // Витягуємо слова та розділові знаки ДО сортування
            List<String> words = new ArrayList<>();
            List<String> separators = new ArrayList<>();

            // РЕГУЛЯРНИЙ ВИРАЗ: слово = букви, дефіс/апостроф ТІЛЬКИ всередині
            Pattern wordPattern = Pattern.compile("\\p{L}+(?:[-']\\p{L}+)*");
            Matcher matcher = wordPattern.matcher(textBuffer);

            int lastEnd = 0;

            // Перед першим словом
            if (matcher.find()) {
                if (matcher.start() > 0) {
                    separators.add(originalText.substring(0, matcher.start()));
                } else {
                    separators.add("");
                }
            } else {
                System.out.println("У тексті немає слів для обробки.");
                return;
            }

            // Проходимо по всіх словам
            while (true) {
                String word = matcher.group();
                words.add(word);

                int currentEnd = matcher.end();
                if (matcher.find()) {
                    separators.add(originalText.substring(currentEnd, matcher.start()));
                } else {
                    separators.add(originalText.substring(currentEnd));
                    break;
                }
            }

            if (words.isEmpty()) {
                System.out.println("У тексті немає слів для обробки.");
                return;
            }

            // Сортування на кількістю голосних літер
            List<WordWithCount> sortedWords = new ArrayList<>();
            for (String word : words) {
                sortedWords.add(new WordWithCount(word));
            }

            sortedWords.sort(Comparator.comparingInt(WordWithCount::vowelCount));

            // Оновлений текст
            StringBuffer newTextBuffer = new StringBuffer();
            newTextBuffer.append(separators.get(0)); // перед першим словом

            for (int i = 0; i < sortedWords.size(); i++) {
                newTextBuffer.append(sortedWords.get(i).word);
                newTextBuffer.append(separators.get(i + 1));
            }

            // Виведення результату
            System.out.println("=== Новий текст (слова в порядку зростання кількості голосних) ===");
            System.out.println(newTextBuffer.toString().trim());
            System.out.println();

            System.out.println("=== Слова, відсортовані за кількістю голосних ===");
            for (WordWithCount wc : sortedWords) {
                System.out.printf("%-20s : %d голосних%n", wc.word, wc.vowelCount());
            }
            System.out.println();

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Допоміжний клас: слово + кількість голосних */
    private static class WordWithCount {
        final String word;
        private final int vowelCount;

        WordWithCount(String word) {
            this.word = word;
            this.vowelCount = countVowels(word);
        }

        private int countVowels(String s) {
            int cnt = 0;
            for (int i = 0; i < s.length(); i++) {
                if (VOWELS.indexOf(s.charAt(i)) != -1) {
                    cnt++;
                }
            }
            return cnt;
        }

        int vowelCount() {
            return vowelCount;
        }
    }
}