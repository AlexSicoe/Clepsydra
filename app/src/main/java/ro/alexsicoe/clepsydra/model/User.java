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
    @NonNull
    private String tokenId;
    //TODO Bitmap profilePicture;
    //private List<Task> tasks;


    public User(@Nullable String name, @NonNull String email, @Nullable String position, @NonNull String tokenId) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.tokenId = tokenId;
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
}
