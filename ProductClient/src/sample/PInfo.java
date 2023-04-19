package sample;
import java.io.Serializable;


public class PInfo implements Serializable {
    String postNo;
    String productName;
    String uploaderId;
    String category;
    String cost;
    String tag;
    String time;
    String date;

    public PInfo(String postNo, String productName, String uploaderId, String category, String cost, String tag, String time, String date) {
        this.postNo = postNo;
        this.productName = productName;
        this.uploaderId = uploaderId;
        this.category = category;
        this.cost = cost;
        this.tag = tag;
        this.time = time;
        this.date = date;
    }
}
