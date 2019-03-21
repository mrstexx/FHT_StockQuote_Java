import java.io.Serializable;

public class HashTable implements Serializable {
    public static final int CAPACITY = 1000;
    private Stock[] stocks;

    public HashTable() {
        this.stocks = new Stock[CAPACITY];
    }

    public void add(Stock stock) {
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

    public boolean search(String name) {
        int i = 0;
        while (i < CAPACITY) {
            int hash = (int) (generateHashCode(name) + Math.pow(i + 1, 2)) % CAPACITY;
            if (this.stocks[hash] != null) {
                if (this.stocks[hash].getStockName().equals(name)) {
                    stocks[hash].listCourseData();
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

    // temp
    public void printList() {
        for (int i = 0; i < this.stocks.length; i++) {
            if (this.stocks[i] != null) {
                System.out.println(this.stocks[i].getStockName());
            }
        }
    }

    private int generateHashCode(String name) {
        int hash = 0;
        int hashBase = 31;
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
