package database;

import com.google.gson.annotations.Expose;

public class Post {

    /**
     * TEST CLASS FOR DATABASE
     */

    @Expose
    private int id;
    @Expose
    private String myCreator;
    @Expose
    private String myContent;

    public Post(String content, String creator, Number id) {
        myCreator = creator;
        myContent = content;
        this.id = id.intValue();
    }

    @Override
    public String toString() {
        return "Post " + id + " :" + myContent + " -" + myCreator;
    }
}
