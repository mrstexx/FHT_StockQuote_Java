import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IOHandler {
    private HashTable hashTable;
    private String fileName;

    public IOHandler() {
    }

    public IOHandler(HashTable hashTable, String fileName) {
        this.hashTable = hashTable;
        this.fileName = fileName;
    }

    public IOHandler(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Function used to save HashTable as binary object using ObjectOutputStream (Serialisation)
     */
    public void saveHashTable() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName + ".bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.hashTable);
            fileOutputStream.close();
            objectOutputStream.close();
            System.out.println("**Die HashTable wird erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("**Ausgabe Fehler wurde gefunden. Versuchen Sie mit andere Dateiname!");
        }
    }

    /**
     * Function used to load binary file (Object) using ObjectInputStream (Deserialisation)
     *
     * @return Returns new HashTable as new object
     */
    public HashTable loadHashTable() {
        HashTable hashTable = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(this.fileName + ".bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            hashTable = (HashTable) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            System.out.println("**Die HashTable wird erfolgreich eingelesen.");
        } catch (IOException e) {
            System.out.println("**Die eingegebene Datei kann nicht gefunden werden.");
            return new HashTable();
        } catch (ClassNotFoundException e) {
            System.out.println("**Die gespeicherte Tabelle stimmt nicht mit Ihren Einstellungen ueberein.");
        }
        return hashTable;
    }

    /**
     * Function used for importing course data in one stock from hashtable
     *
     * @param stock Stock to be imported course data in
     */
    public void importCourseData(Stock stock) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName + ".csv"));
            String line = bufferedReader.readLine(); // empty first line
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null && counter < 30) {
                String[] values = line.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                CourseData courseData = new CourseData(
                        LocalDate.parse(values[0], formatter), // date
                        Double.parseDouble(values[1]), // open data
                        Double.parseDouble(values[2]), // high data
                        Double.parseDouble(values[3]), // low data
                        Double.parseDouble(values[4]), // close data
                        Double.parseDouble(values[5]), // adjClose data
                        Integer.parseInt(values[6])); // volume data
                stock.getCourseData().add(courseData);
                counter++;
            }
            bufferedReader.close();
            System.out.println("**Die Kursdaten werden erfolgreich importiert.");
        } catch (IOException e) {
            System.out.println("**Die Datei kann nich gefunden werden.");
        }
    }

    /**
     * Function used for writing (output) of close data of one stock from hashtable
     *
     * @param stock Stock from which is new graph image (.txt) created of close data
     */
    public void drawPlot(Stock stock) {
        try {
            // Output as .txt file
            FileWriter fileWriter = new FileWriter(new File(stock.getStockName() + ".txt"));
            DecimalFormat formatter = new DecimalFormat("000.000000");
            int stockSize = stock.getCourseData().size();
            // sort close data and get it as new double array in order to show sorted values on y-axis
            Double[] sortedCloseData = stock.getSortedCloseData();

            // writing dates and values on the graph
            for (int i = 0; i < stockSize; i++) {
                for (int j = 0; j < 6 * stockSize; j++) {
                    // 6 times because 5 is date length (DD.MM) - for 5 places on x-axis + 1 for '|'
                    if (j == 0) {
                        fileWriter.write(formatter.format(sortedCloseData[i]) + "|");
                    } else if (j == 6 * stockSize - 1) {
                        fileWriter.write("|");
                    } else if (j % 6 == 2 && sortedCloseData[i] == stock.getCourseData().get(j / 6).getClose()) {
                        fileWriter.write("+");
                    } else {
                        fileWriter.write(" ");
                    }
                }
                fileWriter.write("\n");
            }

            // empty space before dates at the bottom
            fileWriter.write("          ");
            // writing dates at the bottom
            for (int i = 0; i < stockSize; i++) {
                LocalDate localDate = stock.getCourseData().get(i).getDate();
                formatter = new DecimalFormat("00");
                String date = formatter.format(Double.valueOf(localDate.getDayOfMonth())) + "." +
                        formatter.format(Double.valueOf(localDate.getMonthValue()));
                fileWriter.write(date + "|");
            }
            fileWriter.flush();
            fileWriter.close();
            System.out.println("**Der Graph wird erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("**Ausgabe Fehler wurde gefunden. Versuchen Sie mit anderem Name!");
        }
    }
}
