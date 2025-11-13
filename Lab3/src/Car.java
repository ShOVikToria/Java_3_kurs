/**
 * Клас представляє автомобіль із основними характеристиками.
 * <p>
 * Містить п'ять полів: модель, рік випуску, ціна, об'єм двигуна, чи є електричним.
 * Реалізує {@code Comparable} для сортування за об'ємом двигуна (зростання)
 * та, при рівних об'ємах, за ціною (спадання).
 * </p>
 *
 * @author Шевчук Вікторія ІП-31
 * @version 1.0
 * @since 2025-11-13
 */
public class Car implements Comparable<Car> {

    /** Назва моделі автомобіля. */
    private final String model;

    /** Рік випуску автомобіля. */
    private final int year;

    /** Ціна автомобіля в доларах США. */
    private final double price;

    /** Об'єм двигуна в літрах. */
    private final double engineCapacity;

    /** Вказує, чи є автомобіль електричним. */
    private final boolean isElectric;

    /**
     * Конструктор для створення об'єкта автомобіля.
     *
     * @param model          назва моделі
     * @param year           рік випуску
     * @param price          ціна в доларах США
     * @param engineCapacity об'єм двигуна в літрах
     * @param isElectric     {@code true}, якщо електричний
     */
    public Car(String model, int year, double price, double engineCapacity, boolean isElectric) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.engineCapacity = engineCapacity;
        this.isElectric = isElectric;
    }

    // --- Getters ---

    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public double getEngineCapacity() { return engineCapacity; }
    public boolean isElectric() { return isElectric; }

    /**
     * Порівнює автомобілі для сортування.
     * <p>
     * Порядок:
     * <ol>
     *   <li>За об'ємом двигуна — за зростанням</li>
     *   <li>Якщо об'єми рівні — за ціною — за спаданням</li>
     * </ol>
     * </p>
     *
     * @param other інший автомобіль
     * @return від'ємне, якщо цей менший; 0 — якщо рівні; додатне — якщо більший
     */
    @Override
    public int compareTo(Car other) {
        int capacityComp = Double.compare(this.engineCapacity, other.engineCapacity);
        if (capacityComp != 0) {
            return capacityComp;
        }
        return Double.compare(other.price, this.price); // спадання
    }

    /**
     * Перевіряє рівність двох автомобілів.
     * <p>
     * Два автомобілі рівні, якщо всі поля збігаються.
     * </p>
     *
     * @param obj об'єкт для порівняння
     * @return {@code true}, якщо рівні
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Car)) return false;
        Car other = (Car) obj;
        return this.year == other.year &&
                Double.compare(this.price, other.price) == 0 &&
                Double.compare(this.engineCapacity, other.engineCapacity) == 0 &&
                this.isElectric == other.isElectric &&
                this.model.equals(other.model);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + model.hashCode();
        result = 31 * result + year;
        long p = Double.doubleToLongBits(price);
        result = 31 * result + (int) (p ^ (p >>> 32));
        long c = Double.doubleToLongBits(engineCapacity);
        result = 31 * result + (int) (c ^ (c >>> 32));
        result = 31 * result + (isElectric ? 1 : 0);
        return result;
    }

    /**
     * Повертає рядкове представлення автомобіля.
     *
     * @return рядок у зрозумілому форматі
     */
    @Override
    public String toString() {
        return String.format("Автомобіль{модель='%s', рік=%d, ціна=%.0f, об'єм=%.1f, електро=%s}",
                model, year, price, engineCapacity, isElectric ? "так" : "ні");
    }
}