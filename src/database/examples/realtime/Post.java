package database.examples.realtime;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;

public class Post extends TrackableObject {

    /**
     * Test class for showing off working of realtime database
     */

    @Expose private String myContent;
    @Expose private int myValue;
    @Expose private double fixed;
    @Expose public PostMetaData metadata;

    /**
     * Private empty constructor required so that DatabaseConnector
     * can use Reflection to create the object
     */
    private Post() {}


    public Post(String content, String creator, int myVal) {
        myContent = content;
        myValue = myVal;
        fixed = 10;
        metadata = new PostMetaData(creator);
    }

    public void setFixed(int val) {
        fixed = val;
    }

    @Override
    public String toString() {
        return myContent + metadata.toString();
    }

    @Override
    public void initialize() {

    }
}
