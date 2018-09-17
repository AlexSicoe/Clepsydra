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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    public static class GroupItem extends ExpandableGroup<Task> {
        private User user;

        public GroupItem(User user) {
            super(user.getName(), user.getTasks());
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public GroupItem setUser(User user) {
            this.user = user;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GroupItem groupItem = (GroupItem) o;
            return user.equals(groupItem.user);
        }

        @Override
        public int hashCode() {
            return user.hashCode();
        }
    }
}
