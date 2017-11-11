package database;

public class Post {

    /**
     * TEST CLASS FOR DATABASE
     */

    private int id;
    private String myCreator;
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
