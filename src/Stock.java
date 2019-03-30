import java.io.Serializable;
import java.util.ArrayList;

public class Stock implements Serializable {
    private String stockName = "";
    private String WKN = "";
    private String stockShortcut = "";
    private ArrayList<CourseData> courseData;

    // helping data for hashing
    private int nameHash;
    private int shortcutHash;

    public static final int MAX_COURSE_DATA = 30;

    public Stock(String stockName, String WKN, String stockShortcut) {
        this.stockName = stockName;
        this.WKN = WKN;
        this.stockShortcut = stockShortcut;
        this.courseData = new ArrayList<>();
    }

    /**
     * Function used to list all course data on searching
     */
    public void listCourseData() {
        if (this.courseData.size() == 0) {
            System.out.println("**Es gibt keine gespeicherte Aktienskursdaten.");
            return;
        }
        for (CourseData courseData : this.courseData) {
            System.out.println(courseData.getDate() + " - " +
                    courseData.getOpen() + " - " +
                    courseData.getHigh() + " - " +
                    courseData.getLow() + " - " +
                    courseData.getClose() + " - " +
                    courseData.getAdjClose() + " - " +
                    courseData.getVolume());
        }
    }

    /**
     * Function used for sorting close data of one stock
     *
     * @return sortedCloseData Returns sorted close data
     */
    public Double[] getSortedCloseData() {
        Double[] sortedCloseData = new Double[this.getCourseData().size()];
        for (int i = 0; i < this.getCourseData().size(); i++) {
            sortedCloseData[i] = this.getCourseData().get(i).getClose();
        }
        this.quickSort(sortedCloseData, 0, this.getCourseData().size() - 1);
        return sortedCloseData;
    }

    private void quickSort(Double[] data, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int pi = this.partition(data, startIndex, endIndex);
            this.quickSort(data, startIndex, pi - 1);
            this.quickSort(data, pi + 1, endIndex);
        }
    }

    private int partition(Double[] array, int startIndex, int endIndex) {
        double pivot = array[endIndex];
        int i = startIndex - 1;
        for (int j = startIndex; j <= endIndex - 1; j++) {
            // sort max->min
            if (array[j] > pivot) {
                i++;
                double temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        double temp = array[i + 1];
        array[i + 1] = array[endIndex];
        array[endIndex] = temp;
        return (i + 1);
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getWKN() {
        return WKN;
    }

    public void setWKN(String WKN) {
        this.WKN = WKN;
    }

    public String getStockShortcut() {
        return stockShortcut;
    }

    public void setStockShortcut(String stockShortcut) {
        this.stockShortcut = stockShortcut;
    }

    public ArrayList<CourseData> getCourseData() {
        return courseData;
    }

    public void setCourseData(ArrayList<CourseData> courseData) {
        this.courseData = courseData;
    }

    public int getNameHash() {
        return nameHash;
    }

    public void setNameHash(int nameHash) {
        this.nameHash = nameHash;
    }

    public int getShortcutHash() {
        return shortcutHash;
    }

    public void setShortcutHash(int shortcutHash) {
        this.shortcutHash = shortcutHash;
    }
}
