package database.examples.realtime;

import com.google.gson.annotations.Expose;
import database.TrackableObject;

public class Post extends TrackableObject {

    /**
     * Test class for showing off working of realtime database
     */

    @Expose private String myCreator;
    @Expose private String myContent;
    @Expose private int myValue;
    @Expose private double fixed;

    /**
     * Private empty constructor required so that DatabaseConnector
     * can use Reflection to create the object
     */
    private Post() {}


    public Post(String content, String creator, int myVal) {
        myCreator = creator;
        myContent = content;
        myValue = myVal;
        fixed = 10;
    }

    @Override
    public String toString() {
        return "Post: " + myContent + " - " + myCreator + ". Post Value: " + myValue + ". Fixed Value: " + fixed;
    }
}
