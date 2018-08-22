package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class User {
    @Nullable
    private String name;
    @NonNull
    private String email;
    @Nullable
    private String position;
    //TODO Bitmap profilePicture;
    //private List<Task> tasks;

    public User(@Nullable String name, @NonNull String email, @Nullable String position) {
        this.name = name;
        this.email = email;
        this.position = position;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


}
