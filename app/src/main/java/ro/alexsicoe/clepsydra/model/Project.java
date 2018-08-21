package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable {

    @NonNull
    private String name;
    @NonNull
    private Date start;
    //private List<Milestone> milestones;
    //private List<User> users;


    public Project(@NonNull String name) {
        this.name = name;
        this.start = new Date();
    }

    public Project(@NonNull String name, @NonNull Date start) {
        this.name = name;
        this.start = start;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Date getStart() {
        return start;
    }

    public void setStart(@NonNull Date start) {
        this.start = start;
    }
}
