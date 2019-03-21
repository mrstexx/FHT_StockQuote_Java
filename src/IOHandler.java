import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IOHandler {
    private HashTable hashTable;
    private String fileName;

    public IOHandler(HashTable hashTable, String fileName) {
        this.hashTable = hashTable;
        this.fileName = fileName;
    }

    public IOHandler(String fileName) {
        this.fileName = fileName;
    }

    public void saveHashTable() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName + ".bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.hashTable);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            //System.out.println("Die Datei kann nicht gefunden werden.");
            e.printStackTrace();
        }
    }

    public HashTable loadHashTable() {
        HashTable hashTable = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(this.fileName + ".bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            hashTable = (HashTable) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Die Datei kann nicht gefunden werden.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hashTable;
    }

    public void importCourseData(Stock stock) {
        String open = "";
        String high = "";
        String low = "";
        String close = "";
        String adjClose = "";
        String volume = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName + ".csv"));
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                CourseData courseData = new CourseData(
                        sdf.parse(values[0]),
                        Double.parseDouble(values[1]),
                        Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]),
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]),
                        Integer.parseInt(values[6]));
                stock.getCourseData().add(courseData);
            }
        } catch (IOException e) {
            System.out.println("Die Datei kann nich gefunden werden.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
