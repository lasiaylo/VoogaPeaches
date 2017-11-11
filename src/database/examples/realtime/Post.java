package database.examples.realtime;

import com.google.gson.annotations.Expose;

public class Post {

    /**
     * Test class for showing off working of realtime database
     */
    private static int counter = 1;
    @Expose private int id;
    @Expose private String myCreator;
    @Expose private String myContent;


    public Post(String content, String creator) {
        myCreator = creator;
        myContent = content;
        id = counter;
        counter++;
    }

    /**
     * This is the constructor the database will use when creating the object from JSON data.
     * This constructor can be either public or private but it has to follow these guidelines:
     *  1. It must contain params for all the instance variables marked by @Expose
     *  2. Params with common types must be grouped together alphabetically by name, but
     *      the order of parameter types in the declaration does not matter
     *  3. All instance variables that are numeric (ie. double, int, long, etc) must have their
     *  parameter marked with the type Number
     */
    private Post(String myContent, String myCreator, Number id) {
        this.myCreator = myCreator;
        this.myContent = myContent;
        this.id = id.intValue();
    }

    @Override
    public String toString() {
        return "Post " + id + ": " + myContent + " - " + myCreator;
    }
}
