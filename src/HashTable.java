import java.io.Serializable;

public class HashTable implements Serializable {
    public static final int CAPACITY = 1003; // in form 4*j + 3
    private int currentNumberOfStocks = 0;
    private Stock[] stocks;
    private Stock[] stocksShortcut;

    public HashTable() {
        this.stocks = new Stock[CAPACITY]; // data that will be saved per name
        this.stocksShortcut = new Stock[CAPACITY]; // data will be saved per shortcut
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
        // Handling for names
        addStock(stock, this.stocks, true);
        // Handling for shortcuts
        addStock(stock, this.stocksShortcut, false);
    }

    /**
     * Helper function for adding new stock in arrays
     *
     * @param stock      Stock that needs to be imported.
     * @param pointStock Stock to be data imported in.
     * @param isName     True if we handle name case array.
     *                   Fasle if we handle shortcut case array.
     */
    private void addStock(Stock stock, Stock[] pointStock, boolean isName) {
        int hash = isName ? (int) generateHashCode(stock.getStockName()) :
                (int) generateHashCode(stock.getStockShortcut());
        if (pointStock[hash] == null) {
            if (isName) {
                stock.setNameHash(hash);
                this.currentNumberOfStocks++;
            } else {
                stock.setShortcutHash(hash);
            }
            pointStock[hash] = stock;
            return;
        }
        int i = 1;
        while (i < CAPACITY) {  // it can 1002 times loop
            hash = isName ? (int) (generateHashCode(stock.getStockName()) +
                    Math.pow(-1, i + 1) * Math.pow(Math.round((double) i / 2), 2)) % CAPACITY :
                    (int) (generateHashCode(stock.getStockName()) +
                            Math.pow(-1, i + 1) * Math.pow(Math.round((double) i / 2), 2)) % CAPACITY; // alternating quadratic probing
            if (hash < 0) {
                hash += CAPACITY; // avoid negative hash numbers
            }
            if (this.stocks[hash] == null) {
                if (isName) {
                    stock.setNameHash(hash);
                    this.currentNumberOfStocks++;
                } else {
                    stock.setShortcutHash(hash);
                }
                pointStock[hash] = stock;
                break;
            }
            i++;
        }
    }

    /**
     * Function used for searching hashtable
     *
     * @param userInput   Name/Shortcut of stock to be searched for
     * @param toPrintList True if want to print course data - search case from menu option.
     *                    False if we use this function to check does stock exist in order to avoid program errors
     * @return boolean True if stock with name/shortcut was found. False if it isn't
     */
    public Stock search(String userInput, boolean toPrintList) {
        // first check does O(1) exist for name
        int hash = (int) generateHashCode(userInput);
        if (this.stocks[hash] != null) {
            if (this.stocks[hash].getStockName().equals(userInput)) {
                if (toPrintList == true) {
                    this.stocks[hash].listCourseData();
                }
                return this.stocks[hash];
            }
        }
        // second check does O(1) exist for shortcut
        else if (this.stocksShortcut[hash] != null) {
            if (this.stocksShortcut[hash].getStockShortcut().equals(userInput)) {
                if (toPrintList == true) {
                    this.stocksShortcut[hash].listCourseData();
                }
                return this.stocksShortcut[hash];
            }
        }
        // third go for quadratic probing search until stock is found
        int i = 1;
        while (i < CAPACITY) {
            hash = (int) (generateHashCode(userInput) +
                    Math.pow(-1, i + 1) * Math.pow(Math.round((double) i / 2), 2)) % CAPACITY;
            if (hash < 0) {
                hash += CAPACITY; // avoid negative hash numbers
            }
            if (this.stocks[hash] != null) {
                if (this.stocks[hash].getStockName().equals(userInput)) {
                    if (toPrintList == true) {
                        this.stocks[hash].listCourseData();
                    }
                    return stocks[hash];
                }
            } else if (this.stocksShortcut[hash] != null) {
                if (this.stocksShortcut[hash].getStockShortcut().equals(userInput)) {
                    if (toPrintList == true) {
                        this.stocksShortcut[hash].listCourseData();
                    }
                    return this.stocksShortcut[hash];
                }
            }
            i++;
        }
        return null;
    }

    /**
     * Helper function used to check does same object exists in hashtable
     *
     * @param stock Stock to be compared
     * @return True if newly created stock is equal to existing one in database. False if newly created stock can be created
     */
    private boolean strictSearch(Stock stock) {
        if (search(stock.getStockName(), false) != null && search(stock.getStockShortcut(), false) != null) {
            // it is possible to create stock if name or shortcut is different than existing one in hashtable
            return true;
        }
        return false;
    }

    /**
     * Remove stock from hashtable
     *
     * @param stockToDelete Stock to be deleted
     */
    public void remove(Stock stockToDelete) {
        // remove from name array
        this.stocks[stockToDelete.getNameHash()] = null;
        // remove from shortcut array
        this.stocksShortcut[stockToDelete.getShortcutHash()] = null;
        this.currentNumberOfStocks--;
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