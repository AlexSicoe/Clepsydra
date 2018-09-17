package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class User {
    @Nullable
    private String name;
    @NonNull
    private String email;
    @Nullable
    private String position;
    @NonNull
    private String tokenId;
    private List<Task> tasks;


    public User(@Nullable String name, @NonNull String email, @Nullable String position, @NonNull String tokenId, List<Task> tasks) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.tokenId = tokenId;
        this.tasks = tasks;
    }


    public User() {
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Nullable
    public String getPosition() {
        return position;
    }

    public void setPosition(@Nullable String position) {
        this.position = position;
    }

    @NonNull
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(@NonNull String tokenId) {
        this.tokenId = tokenId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public User setTasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    public static class Group extends ExpandableGroup<Task> {
        public Group(String title, List<Task> items) {
            super(title, items);
        }
    }
}
