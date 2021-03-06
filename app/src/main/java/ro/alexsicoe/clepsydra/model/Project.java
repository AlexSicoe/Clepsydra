package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Project implements Serializable {
    @NonNull
    private String id;
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

    public Project(@NonNull String id, @NonNull String name, @NonNull String createdBy, @NonNull Date start) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.start = start;
    }

    public Project(@NonNull String id, @NonNull String name, @NonNull String createdBy) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.start = new Date();
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

    @NonNull
    public String getId() {
        return id;
    }

    public Project setId(@NonNull String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", start=" + start +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
