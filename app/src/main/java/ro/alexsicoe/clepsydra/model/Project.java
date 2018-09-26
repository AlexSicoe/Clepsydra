package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Project implements Serializable {
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String createdBy;
    @NonNull
    private Date createdAt;
    @Nullable
    private List<Phase> phases;
    //private List<Milestone> milestones;
    //private List<User> users;

    public Project() {
    }

    public Project(@NonNull String id, @NonNull String name, @NonNull String createdBy) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = new Date();
        //TODO factory
        this.phases = new ArrayList<>();
        phases.add(new Phase("Todo"));
        phases.add(new Phase("In progress"));
        phases.add(new Phase("Done"));
    }

    @NonNull
    public String getId() {
        return id;
    }

    public Project setId(@NonNull String id) {
        this.id = id;
        return this;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Project setName(@NonNull String name) {
        this.name = name;
        return this;
    }

    @NonNull
    public String getCreatedBy() {
        return createdBy;
    }

    public Project setCreatedBy(@NonNull String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @NonNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public Project setCreatedAt(@NonNull Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Nullable
    public List<Phase> getPhases() {
        return phases;
    }

    public Project setPhases(@Nullable List<Phase> phases) {
        this.phases = phases;
        return this;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", phases=" + phases +
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

    public static class Phase {
        String name;

        //TODO count progress bar

        public Phase() {

        }

        public Phase(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Phase setName(String name) {
            this.name = name;
            return this;
        }
    }
}
