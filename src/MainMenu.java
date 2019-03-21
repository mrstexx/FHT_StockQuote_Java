import java.util.Scanner;

public class MainMenu {
    private HashTable hashTable;

    public MainMenu() {
        this.hashTable = new HashTable();
        printMainMenuHeader();
        printMainMenu();
        inputHandler();
    }

    private void printMainMenuHeader() {
        System.out.println("*************************************");
        System.out.println("| Willkommen zur Aktienkurs Manager |");
        System.out.println("*************************************");
        System.out.println();
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
        Scanner reader = new Scanner(System.in);
        while (menuOption != EMenuOptions.QUIT.getNumValue()) {
            System.out.print("Benutzer Eingabe (Nummer): ");
            try {
                userInput = reader.nextLine();
                menuOption = Integer.parseInt(userInput);
                if (menuOption < EMenuOptions.ADD.getNumValue() || menuOption > EMenuOptions.QUIT.getNumValue()) {
                    System.out.println("Falsche Eingabe! Versuchen Sie wieder.");
                }
            } catch (Exception c) {
                System.out.println("Falsche Eingabe! Eingabe muss ein Integer sein! Versuchen Sie wieder.");
                //reader.remove();
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
        Scanner reader = new Scanner(System.in);
        String stockName = "";
        String WKN = "";
        String stockShortcut = "";
        System.out.print("Aktionsname: ");
        stockName = reader.nextLine();
        System.out.print("WKN: ");
        WKN = reader.nextLine();
        System.out.print("Aktionskuerzel: ");
        stockShortcut = reader.nextLine();
        Stock newStock = new Stock(stockName, WKN, stockShortcut);
        this.hashTable.add(newStock);
    }

    private void deleteStock() {
        Scanner reader = new Scanner(System.in);
        String stockName = "";
        System.out.print("Aktionsname: ");
        stockName = reader.nextLine();
        this.hashTable.remove(stockName);
    }

    private void importStockQuote() {
        Scanner reader = new Scanner(System.in);
        String stockName = "";
        String courseFileName = "";
        System.out.print("Geben Sie Aktiensname ein: ");
        stockName = reader.nextLine();
        System.out.print("Geben Sie Aktienskurse Dateiname ein: ");
        courseFileName = reader.nextLine();
        Stock stock = this.hashTable.getStock(stockName);
        IOHandler io = new IOHandler(courseFileName);
        io.importCourseData(stock);
    }

    private void searchStock() {
        Scanner reader = new Scanner(System.in);
        String stockName = "";
        System.out.print("Aktiensname: ");
        stockName = reader.nextLine();
        if (!this.hashTable.search(stockName)) {
            System.out.println("Eingegeben Name/WKN existiert nicht.");
        }
    }

    private void plotStockQuote() {
        // temp
        this.hashTable.printList();
    }

    private void saveHashTable() {
        String fileName = "";
        Scanner reader = new Scanner(System.in);
        System.out.print("Geben Sie Datei Name ein: ");
        fileName = reader.nextLine();
        IOHandler io = new IOHandler(this.hashTable, fileName);
        io.saveHashTable();
    }

    private void loadHashTable() {
        String fileName = "";
        Scanner reader = new Scanner(System.in);
        System.out.print("Geben Sie Datei Name ein: ");
        fileName = reader.nextLine();
        IOHandler io = new IOHandler(fileName);
        this.hashTable = io.loadHashTable();
    }
}
