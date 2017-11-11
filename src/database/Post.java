package database;

import com.google.gson.annotations.Expose;

public class Post {

    /**
     * TEST CLASS FOR DATABASE
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

    private Post(String myContent, String myCreator, Number id) {
        this.myCreator = myCreator;
        this.myContent = myContent;
        this.id = id.intValue();
    }

    @Override
    public String toString() {
        return "Post " + id + " :" + myContent + " -" + myCreator;
    }
}
