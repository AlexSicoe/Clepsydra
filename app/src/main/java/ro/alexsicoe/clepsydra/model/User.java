package ro.alexsicoe.clepsydra.model;

public class User {
    private String name;
    private String position;
    //TODO Bitmap profilePicture;
    //private List<Task> tasks;

    public User(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
