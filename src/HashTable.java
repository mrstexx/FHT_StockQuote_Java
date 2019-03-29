import java.io.Serializable;

public class HashTable implements Serializable {
    public static final int CAPACITY = 2003; // in form 4*j + 3
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
        // first check is same object already saved
        if (strictSearch(stock)) {
            System.out.println("**Diese Aktie existiert schon im Table!");
            return;
        }
        int hash = (int) generateHashCode(stock.getStockName());
        if (this.stocks[hash] == null) {
            this.stocks[hash] = stock;
            return;
        }
        int i = 1;
        while (i < (CAPACITY - 1) / 2) { // 2002 / 2 = 1001 -> which is exactly 1000 times to handle one action if required
            hash = (int) (generateHashCode(stock.getStockName()) +
                    Math.pow(-1, i + 1) * Math.pow(Math.round((double) i / 2), 2)) % CAPACITY; // alternating quadratic probing
            if (hash < 0) {
                hash += CAPACITY; // avoid negative hash numbers
            }
            if (this.stocks[hash] == null) {
                this.stocks[hash] = stock;
                return;
            }
            i++;
        }
        this.currentNumberOfStocks++;
        return;
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
        // TODO DO REFACTORING
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
     * Function used to check are two objects same
     *
     * @param stock Stock to be compared
     * @return True if newly created stock is equal to existing one in database.
     */
    private boolean strictSearch(Stock stock) {
        int i = 0;
        // TODO DO REFACTORING
        while (i < CAPACITY) {
            int hash = (int) (generateHashCode(stock.getStockName()) + Math.pow(i + 1, 2)) % CAPACITY;
            if (this.stocks[hash] != null) {
                if (this.stocks[hash].getStockName().equals(stock.getStockName()) &&
                        this.stocks[hash].getStockShortcut().equals(stock.getStockShortcut()) &&
                        this.stocks[hash].getWKN().equals(stock.getWKN())) {
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
        // TODO also could happen that does not work every time becuase of probing - also search for name and shortcut
        int hash = generateHashCode(name);
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
        // TODO Do magic for name and shorcut in one hash function
        for (int i = 0; i < name.length(); i++) {
            hash = hash * hashBase + name.charAt(i);
        }
        hash %= CAPACITY;
        if (hash < 0) {
            hash += CAPACITY; // in case that hash is negative number
        }
        return hash;
    }
}