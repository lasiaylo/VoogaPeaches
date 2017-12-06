package database.examples.realtime;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class PostMetaData extends TrackableObject {

    @Expose private String creator;
    @Expose private String timestamp;
    @Expose private List<String> stuff;

    private PostMetaData() {}

    /**
     * Test class for showing off working of realtime database
     */

    public PostMetaData(String creator) {
        timestamp = createFormattedTime();
        this.creator = creator;
        stuff = new ArrayList<>();
        stuff.add("test");
        stuff.add("test2");
    }

    public void changeCreator(String creator) {
        this.creator = creator;
    }

    private String createFormattedTime() {
        DateTime dt = new DateTime();
        return dt.getDayOfWeek() + ", " + dt.getMonthOfYear() + " " + dt.getDayOfMonth() + ", " + dt.getYear();
    }

    @Override
    public String toString() {
        return " - " + creator + " on " + timestamp;
    }

    @Override
    public void initialize() {

    }
}
