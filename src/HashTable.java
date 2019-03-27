import java.io.Serializable;

public class HashTable implements Serializable {
    public static final int CAPACITY = 1000;
    private Stock[] stocks;

    public HashTable() {
        this.stocks = new Stock[CAPACITY];
    }

    public void add(Stock stock) {
        // TODO implement not more space
        int i = 1;
        int hash = (int) (generateHashCode(stock.getStockName()) + Math.pow(i, 2)) % CAPACITY;
        while (this.stocks[hash] != null) {
            i++;
            hash = (int) (generateHashCode(stock.getStockName()) + Math.pow(i, 2)) % CAPACITY;
        }
        this.stocks[hash] = stock;
    }

    public void remove(String name) {
        int i = 0;
        // TODO instead of capacity, use number of added stocks
        while (i < CAPACITY) {
            int hash = (int) (generateHashCode(name) + Math.pow(i + 1, 2)) % CAPACITY;
            if (this.stocks[hash] != null) {
                if (this.stocks[hash].getStockName().equals(name)) {
                    this.stocks[hash] = null;
                    break;
                }
            }
        }
    }

    public boolean search(String name, boolean toPrintList) {
        int i = 0;
        // TODO break loop if first not exist (after correction implementation)
        while (i < CAPACITY) {
            int hash = (int) (generateHashCode(name) + Math.pow(i + 1, 2)) % CAPACITY;
            if (this.stocks[hash] != null) {
                if (this.stocks[hash].getStockName().equals(name)) {
                    if (toPrintList == true) {
                        stocks[hash].listCourseData();
                    }
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    public Stock getStock(String name) {
        int hash = generateHashCode(name) + 1;
        return this.stocks[hash];
    }

    private int generateHashCode(String name) {
        int hash = 0;
        int hashBase = 31;
        // TODO +1, -1, +4, -4, ... https://de.wikipedia.org/wiki/Hashtabelle#Quadratisches_Sondieren
        // TODO Do magic for name and shorcut in one hash function
        for (int i = 0; i < name.length(); i++) {
            hash = hash * hashBase + name.charAt(i);
        }
        hash %= CAPACITY;
        if (hash < 0) {
            hash += CAPACITY;
        }
        return hash;
    }
}
