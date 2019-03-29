import java.io.Serializable;

public class HashTable implements Serializable {
    public static final int CAPACITY = 1000;
    private int currentNumberOfStocks = 0;
    private Stock[] stocks;

    public HashTable() {
        this.stocks = new Stock[CAPACITY];
    }

    /**
     * Adding new stocks to the hashtable
     *
     * @param stock New stock to be added in hashtable
     */
    public void add(Stock stock) {
        if (this.currentNumberOfStocks != CAPACITY) {
            int i = 1;
            int hash = (int) (generateHashCode(stock.getStockName()) + Math.pow(i, 2)) % CAPACITY;
            while (this.stocks[hash] != null) {
                i++;
                hash = (int) (generateHashCode(stock.getStockName()) + Math.pow(i, 2)) % CAPACITY;
            }
            this.stocks[hash] = stock;
            this.currentNumberOfStocks++;
            return;
        }
        System.out.println("**Es gibt nicht genug frei Plaetze.");
    }

    /**
     * Remove stock from hashtable
     *
     * @param name Stock Name/Shortcut to be deleted
     */
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
        this.currentNumberOfStocks--;
    }

    /**
     * Function used for searching hashtable
     *
     * @param name        Name/Shortcut of stock to be searched for
     * @param toPrintList True if want to print course data - search case from menu option.
     *                    False if we use this function to check does stock exist in order to avoid program errors
     * @return boolean True if stock with name/shortcut was found. False if it isn't
     */
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

    /**
     * Function used to get stock from hashtable
     *
     * @param name Name/Shortcut for wanted stock
     * @return Stock from hashtable
     */
    public Stock getStock(String name) {
        int hash = generateHashCode(name) + 1;
        return this.stocks[hash];
    }

    /**
     * Function used to generate hash code from name/shortcut for one stock
     *
     * @param name Name/Shortcut from which will be hash code generated
     * @return hash code
     */
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
