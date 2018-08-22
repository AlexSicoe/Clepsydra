package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable, Cloneable {


    private String name;
    private Date start;
    //private List<Milestone> milestones;
    //private List<User> users;

    public Project() {
    }

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

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", start=" + start +
                '}';
    }

    @Override
    public Project clone() throws CloneNotSupportedException {
        return (Project)super.clone();
    }
}
