import java.io.Serializable;
import java.util.ArrayList;

public class Stock implements Serializable {
    private String stockName = "";
    private String WKN = "";
    private String stockShortcut = "";
    private ArrayList<CourseData> courseData = new ArrayList<>();

    public Stock() {
    }

    public Stock(String stockName, String WKN, String stockShortcut) {
        this.stockName = stockName;
        this.WKN = WKN;
        this.stockShortcut = stockShortcut;
    }

    public void listCourseData() {
        if (this.courseData.size() == 0) {
            System.out.println("Es gibt keine gespeicherte Aktienskursdaten.");
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
}
