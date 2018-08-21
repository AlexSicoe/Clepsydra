package ro.alexsicoe.clepsydra.model;

import java.io.Serializable;

public class Project implements Serializable {
    private String name;

    //private List<Milestone> milestones;
    //private List<User> users;


    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
