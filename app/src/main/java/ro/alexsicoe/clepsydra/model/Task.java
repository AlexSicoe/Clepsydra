package ro.alexsicoe.clepsydra.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Task implements Parcelable {
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private State state;
    @NonNull
    private Date createdAt;
    @Nullable
    private String description;
    @Nullable
    private List<String> assignedOwners; //emails
    @Nullable
    private List<TodoItem> todoItems;


    public Task() {

    }

    public Task(@NonNull String id, @NonNull String name, @NonNull State state) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.createdAt = new Date();
    }

    public static Creator<Task> getCREATOR() {
        return CREATOR;
    }

    @Nullable
    public List<String> getAssignedOwners() {
        return assignedOwners;
    }

    public Task setAssignedOwners(@Nullable List<String> assignedOwners) {
        this.assignedOwners = assignedOwners;
        return this;
    }

    @NonNull
    public State getState() {
        return state;
    }

    public Task setState(@NonNull State state) {
        this.state = state;
        return this;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public Task setId(@NonNull String id) {
        this.id = id;
        return this;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Task setName(@NonNull String name) {
        this.name = name;
        return this;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public Task setDescription(@Nullable String description) {
        this.description = description;
        return this;
    }

    @Nullable
    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public Task setTodoItems(@Nullable List<TodoItem> todoItems) {
        this.todoItems = todoItems;
        return this;
    }

    @NonNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public Task setCreatedAt(@NonNull Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public class TodoItem {
        private String description;
        private boolean isComplete;

        public TodoItem(String description, boolean isComplete) {
            this.description = description;
            this.isComplete = isComplete;
        }

        public String getDescription() {
            return description;
        }

        public TodoItem setDescription(String description) {
            this.description = description;
            return this;
        }

        public boolean isComplete() {
            return isComplete;
        }

        public TodoItem setComplete(boolean complete) {
            isComplete = complete;
            return this;
        }
    }


    public class State {
        String name;

        //TODO count progress bar

        public State() {

        }

        public State(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public State setName(String name) {
            this.name = name;
            return this;
        }
    }
}
