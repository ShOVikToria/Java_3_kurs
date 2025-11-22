import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Клас {@code Text} представляє текст як сукупність речень.
 * Забезпечує парсинг рядка, очищення від зайвих пробілів та виконання
 * операцій над словами тексту згідно з варіантом завдання.
 */
public class Text {

    /** Список речень, з яких складається текст. */
    private final List<Sentence> sentences;

    /**
     * Конструктор класу Text.
     * Виконує очищення тексту від зайвих пробілів і табуляцій
     * та розбиває його на окремі речення.
     *
     * @param rawText вхідний рядок із текстом.
     */
    public Text(String rawText) {
        this.sentences = new ArrayList<>();
        // Заміна множинних пробілів та табуляцій на один пробіл
        String cleanedText = rawText.replaceAll("[\\t\\s]+", " ").trim();
        parseText(cleanedText);
    }

    /**
     * Розбиває текст на речення, використовуючи розділові знаки (. ! ?).
     * Використовує lookbehind regex, щоб зберегти розділовий знак у кінці речення.
     *
     * @param text очищений текст для розбору.
     */
    private void parseText(String text) {
        String[] rawSentences = text.split("(?<=[.!?])\\s+");
        for (String s : rawSentences) {
            sentences.add(new Sentence(s));
        }
    }

    /**
     * Виводить у консоль слова тексту, відсортовані за зростанням кількості голосних літер.
     * Слова збираються з усіх речень тексту в єдиний список перед сортуванням.
     */
    public void printSortedWordsByVowels() {
        List<Word> allWords = new ArrayList<>();
        for (Sentence sentence : sentences) {
            allWords.addAll(sentence.getWords());
        }

        Collections.sort(allWords);

        for (Word word : allWords) {
            System.out.printf("%-20s : %d голосних%n", word, word.getVowelCount());
        }
    }

    /**
     * Повертає рядкове представлення тексту.
     *
     * @return текст, зібраний із речень, розділених пробілами.
     */
    @Override
    public String toString() {
        return sentences.stream()
                .map(Sentence::toString)
                .collect(Collectors.joining(" "));
    }
}

/* --- Допоміжні класи --- */

/**
 * Клас {@code Letter} представляє окрему літеру слова.
 * Містить методи для перевірки властивостей літери (наприклад, чи є вона голосною).
 */
class Letter {
    /** Символьне значення літери. */
    private final char value;

    /** Рядок, що містить усі голосні літери (українські та латинські). */
    private static final String VOWELS = "аеиіоуюєяїАЕИІОУЮЄЯЇAEYUIOaeyuio";

    /**
     * Створює об'єкт літери.
     *
     * @param value символ літери.
     */
    public Letter(char value) {
        this.value = value;
    }

    /**
     * Перевіряє, чи є літера голосною.
     *
     * @return {@code true}, якщо літера голосна; інакше {@code false}.
     */
    public boolean isVowel() {
        return VOWELS.indexOf(value) != -1;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

/**
 * Інтерфейс {@code SentenceMember} представляє будь-який елемент речення.
 * Реалізується класами {@link Word} та {@link Punctuation}.
 */
interface SentenceMember {
    @Override
    String toString();
}

/**
 * Клас {@code Punctuation} представляє розділові знаки в реченні.
 */
class Punctuation implements SentenceMember {
    private final String symbols;

    /**
     * Створює об'єкт розділового знаку.
     *
     * @param symbols рядок символів розділового знаку.
     */
    public Punctuation(String symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return symbols;
    }
}

/**
 * Клас {@code Word} представляє слово як послідовність літер ({@link Letter}).
 * Реалізує інтерфейс {@link Comparable} для сортування за кількістю голосних.
 */
class Word implements SentenceMember, Comparable<Word> {
    private final List<Letter> letters;

    /**
     * Створює об'єкт слова з рядка.
     *
     * @param wordString рядок, що містить слово.
     */
    public Word(String wordString) {
        letters = new ArrayList<>();
        for (char c : wordString.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    /**
     * Підраховує кількість голосних літер у слові.
     *
     * @return кількість голосних.
     */
    public int getVowelCount() {
        int count = 0;
        for (Letter l : letters) {
            if (l.isVowel()) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : letters) {
            sb.append(letter);
        }
        return sb.toString();
    }

    /**
     * Порівнює поточне слово з іншим на основі кількості голосних літер.
     *
     * @param other інше слово для порівняння.
     * @return від'ємне число, нуль або додатне число, якщо кількість голосних
     * у цьому слові менша, рівна або більша за кількість в іншому.
     */
    @Override
    public int compareTo(Word other) {
        return Integer.compare(this.getVowelCount(), other.getVowelCount());
    }
}

/**
 * Клас {@code Sentence} представляє речення.
 * Складається зі списку елементів речення (слів та розділових знаків).
 */
class Sentence {
    private final List<SentenceMember> members;

    /**
     * Створює об'єкт речення, розбиваючи вхідний рядок на слова та пунктуацію.
     *
     * @param rawSentence рядок з реченням.
     */
    public Sentence(String rawSentence) {
        members = new ArrayList<>();
        parseSentence(rawSentence);
    }

    /**
     * Парсить рядок речення, виділяючи слова та розділові знаки за допомогою регулярних виразів.
     *
     * @param rawSentence рядок для парсингу.
     */
    private void parseSentence(String rawSentence) {
        // Група 1: Слова (літери, можливо з дефісом/апострофом всередині)
        // Група 2: Будь-які символи, що не є літерами або пробілами (пунктуація)
        Pattern pattern = Pattern.compile("([\\p{L}]+(?:[-'][\\p{L}]+)*)|([^\\p{L}\\s]+)");
        Matcher matcher = pattern.matcher(rawSentence);

        while (matcher.find()) {
            String wordGroup = matcher.group(1);
            String punctGroup = matcher.group(2);

            if (wordGroup != null) {
                members.add(new Word(wordGroup));
            } else if (punctGroup != null) {
                members.add(new Punctuation(punctGroup));
            }
        }
    }

    /**
     * Повертає список тільки слів з речення, ігноруючи розділові знаки.
     *
     * @return список об'єктів {@link Word}.
     */
    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (SentenceMember member : members) {
            if (member instanceof Word) {
                words.add((Word) member);
            }
        }
        return words;
    }

    /**
     * Формує рядкове представлення речення, коректно розставляючи пробіли між словами.
     * Пробіли не ставляться перед розділовими знаками.
     *
     * @return рядок речення.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            sb.append(members.get(i));
            // Додаємо пробіл тільки між двома словами
            if (i < members.size() - 1) {
                if (members.get(i) instanceof Word && members.get(i + 1) instanceof Word) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}