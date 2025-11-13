import java.util.Arrays;

/**
 * Виконавчий клас для демонстрації сортування та пошуку автомобілів.
 * <p>
 * Створює масив об'єктів {@link Car}, виводить його у початковому порядку,
 * сортує за об'ємом двигуна (зростання) та ціною (спадання при рівних об'ємах),
 * шукає ідентичний об'єкт.
 * </p>
 *
 * @author Шевчук Вікторія ІП-31
 * @version 1.0
 * @since 2025-11-13
 */
public class CarSortingDemo {

    private static final String HEADER = "+-----------------+------+----------+-------+-----------+";
    private static final String FORMAT_ROW = "| %-15s | %4d | %8.0f | %5.1f | %9s |";

    public static void main(String[] args) {
        Car[] cars = {
                new Car("BMW X5",        2023, 65000.0, 3.0, false),
                new Car("Tesla Model 3", 2023, 45000.0, 0.0, true),
                new Car("Audi A4",       2021, 38000.0, 2.0, false),
                new Car("Tesla Model Y", 2023, 52000.0, 0.0, true),
                new Car("Honda Civic",   2021, 25000.0, 1.5, false),
                new Car("Ford Mustang",  2024, 55000.0, 5.0, false)
        };

        // === Початковий масив ===
        System.out.println("=== Початковий масив ===");
        printTable(cars);
        System.out.println();

        // === Сортування ===
        Arrays.sort(cars);

        // === Відсортований масив ===
        System.out.println("=== Відсортований масив (об'єм зростання, ціна спадання) ===");
        printTable(cars);
        System.out.println();

        // === Пошук: знайдено ===
        Car targetFound = new Car("Tesla Model 3", 2023, 45000.0, 0.0, true);
        System.out.println("=== Пошук (очікується: знайдено) ===");
        searchAndPrint(cars, targetFound);

        // === Пошук: не знайдено ===
        Car targetNotFound = new Car("Toyota Camry", 2022, 32000.0, 2.5, false);
        System.out.println("\n=== Пошук (очікується: не знайдено) ===");
        searchAndPrint(cars, targetNotFound);
    }

    private static void printTable(Car[] array) {
        System.out.println(HEADER);
        System.out.printf("| %-15s | %4s | %8s | %5s | %9s |%n", "Модель", "Рік", "Ціна", "Об'єм", "Електро?");
        System.out.println(HEADER);
        for (Car car : array) {
            System.out.printf(FORMAT_ROW,
                    car.getModel(),
                    car.getYear(),
                    car.getPrice(),
                    car.getEngineCapacity(),
                    car.isElectric() ? "так" : "ні");
            System.out.println();
        }
        System.out.println(HEADER);
    }

    private static void searchAndPrint(Car[] array, Car target) {
        System.out.println("Шуканий об'єкт:");
        System.out.println(HEADER);
        System.out.printf(FORMAT_ROW,
                target.getModel(),
                target.getYear(),
                target.getPrice(),
                target.getEngineCapacity(),
                target.isElectric() ? "так" : "ні");
        System.out.println();
        System.out.println(HEADER);
        System.out.println();

        int index = Arrays.binarySearch(array, target);
        if (index >= 0) {
            System.out.println("ЗНАЙДЕНО на позиції: " + index);
        } else {
            System.out.println("НЕ ЗНАЙДЕНО");
        }
    }
}