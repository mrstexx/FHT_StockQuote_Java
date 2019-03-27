import java.util.Scanner;

public class MainMenu {
    private HashTable hashTable;
    private Scanner reader;

    public MainMenu() {
        this.hashTable = new HashTable();
        this.reader = new Scanner(System.in);
        printMainMenuHeader();
        printMainMenu();
        inputHandler();
    }

    private void printMainMenuHeader() {
        System.out.println("*************************************");
        System.out.println("| Willkommen zur Aktienkurs Manager |");
        System.out.println("*************************************");
    }

    private void printMainMenu() {
        System.out.println("Verfuegbare Menueoptionen:");
        System.out.println("1. ADD - Eine Aktie mit Namen, WKN und Kuerzel wird hinzugefuegt");
        System.out.println("2. DEL - Aktie wird geloescht.");
        System.out.println("3. IMPORT - Kurswerte fuer eine Aktie werden aus einer csv Datei importiert");
        System.out.println("4. SEARCH - Eine Aktie wird in der Hashtabelle gesucht");
        System.out.println("5. PLOT - Die Schlusskurse der letzten 30 Tage einer Aktie werden als ASCII Grafik ausgegeben");
        System.out.println("6. SAVE - Programm speichert die Hashtabelle in eine Datei ab");
        System.out.println("7. LOAD - Programm laedt die Hashtabelle aus einer Datei");
        System.out.println("8. QUIT - Programm wird beendet");
        System.out.println();
    }

    private void inputHandler() {
        String userInput = "";
        int menuOption = 0;
        while (menuOption != EMenuOptions.QUIT.getNumValue()) {
            System.out.print("Benutzer Eingabe (Nummer): ");
            try {
                userInput = this.reader.nextLine();
                menuOption = Integer.parseInt(userInput);
                // input cannot be smaller than 1 and bigger than 8
                if (menuOption < EMenuOptions.ADD.getNumValue() || menuOption > EMenuOptions.QUIT.getNumValue()) {
                    System.out.println("**Falsche Eingabe! Versuchen Sie wieder.");
                }
            } catch (Exception c) {
                System.out.println("**Falsche Eingabe! Eingabe muss ein Integer sein! Versuchen Sie wieder.");
            }
            if (menuOption == EMenuOptions.ADD.getNumValue()) {
                addNewStock();
            } else if (menuOption == EMenuOptions.DEL.getNumValue()) {
                deleteStock();
            } else if (menuOption == EMenuOptions.IMPORT.getNumValue()) {
                importStockQuote();
            } else if (menuOption == EMenuOptions.SEARCH.getNumValue()) {
                searchStock();
            } else if (menuOption == EMenuOptions.PLOT.getNumValue()) {
                plotStockQuote();
            } else if (menuOption == EMenuOptions.SAVE.getNumValue()) {
                saveHashTable();
            } else if (menuOption == EMenuOptions.LOAD.getNumValue()) {
                loadHashTable();
            } else if (menuOption == EMenuOptions.QUIT.getNumValue()) {
                break;
            }
        }
    }

    private void addNewStock() {
        String stockName = "";
        String WKN = "";
        String stockShortcut = "";
        System.out.print("Aktiensname: ");
        stockName = this.reader.nextLine();
        System.out.print("WKN: ");
        WKN = this.reader.nextLine();
        System.out.print("Aktienskuerzel: ");
        stockShortcut = this.reader.nextLine();
        Stock newStock = new Stock(stockName, WKN, stockShortcut);
        this.hashTable.add(newStock);
    }

    private void deleteStock() {
        String stockName = "";
        System.out.print("Zu löschender Aktiensname/kuerzel: ");
        stockName = this.reader.nextLine();
        if (this.hashTable.search(stockName, false)) {
            this.hashTable.remove(stockName);
            return;
        }
        System.out.println("**Aktien mit eingegebenem Name/Kuerzel kann nicht gefunden werden.");
    }

    private void importStockQuote() {
        String stockName = "";
        String courseFileName = "";
        System.out.print("Kursdaten importieren in (Aktiensname/kuerzel): ");
        stockName = this.reader.nextLine();
        System.out.print("Geben Sie Dateiname mit Kursendata ein (ohne .csv): ");
        courseFileName = this.reader.nextLine();
        if (this.hashTable.search(stockName, false)) {
            Stock stock = this.hashTable.getStock(stockName);
            IOHandler io = new IOHandler(courseFileName);
            io.importCourseData(stock);
            return;
        }
        System.out.println("**Aktien mit eingegebenem Name/Kuerzel kann nicht gefunden werden.");
    }

    private void searchStock() {
        String stockName = "";
        System.out.print("Aktiensname/kuerzel: ");
        stockName = this.reader.nextLine();
        if (!this.hashTable.search(stockName, true)) {
            System.out.println("**Aktien mit eingegebenem Name/Kuerzel kann nicht gefunden werden.");
        }
    }

    private void plotStockQuote() {
        String stockName = "";
        System.out.print("Geben sie Aktiensname/kuerzel ein: ");
        stockName = this.reader.nextLine();
        if (this.hashTable.search(stockName, false)) {
            Stock stock = this.hashTable.getStock(stockName);
            IOHandler io = new IOHandler();
            io.drawPlot(stock);
            return;
        }
        System.out.println("**Aktien mit eingegebenem Name/Kuerzel kann nicht gefunden werden.");
    }

    private void saveHashTable() {
        String fileName = "";
        System.out.print("Geben Sie gewünschter Dateiname ein (ohne Dateiextension): ");
        fileName = this.reader.nextLine();
        IOHandler io = new IOHandler(this.hashTable, fileName);
        io.saveHashTable();
    }

    private void loadHashTable() {
        String fileName = "";
        System.out.print("Geben Sie gespeicherte Dateiname ein (ohne Dateiextension): ");
        fileName = this.reader.nextLine();
        IOHandler io = new IOHandler(fileName);
        this.hashTable = io.loadHashTable();
    }
}
