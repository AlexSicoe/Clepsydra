package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Project implements Serializable, Cloneable {

    @NonNull
    private String name;
    @NonNull
    private String createdBy;
    @NonNull
    private Date start;
    //private List<Milestone> milestones;
    //private List<User> users;

    public Project() {
    }

    public Project(@NonNull String name, @NonNull String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.start = new Date();
    }

    public Project(@NonNull String name, @NonNull String createdBy, @NonNull Date start) {
        this.name = name;
        this.createdBy = createdBy;
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
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(@NonNull String createdBy) {
        this.createdBy = createdBy;
    }

    @NonNull
    public Date getStart() {
        return start;
    }

    public void setStart(@NonNull Date start) {
        this.start = start;
    }
}
