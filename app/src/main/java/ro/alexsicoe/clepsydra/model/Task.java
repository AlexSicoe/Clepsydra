package ro.alexsicoe.clepsydra.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Task implements Parcelable {

    @NonNull
    private String id;
    @NonNull
    private String name;
    private boolean complete;
    @NonNull
    private Interval interval;
    @Nullable
    private List<Task> subTasks;

    public Task() {
    }


    protected Task(Parcel in) {
        id = in.readString();
        name = in.readString();
        complete = in.readByte() != 0;
        subTasks = in.createTypedArrayList(Task.CREATOR);
    }

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

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @NonNull
    public Interval getInterval() {
        return interval;
    }

    public void setInterval(@NonNull Interval interval) {
        this.interval = interval;
    }

    @Nullable
    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(@Nullable List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (complete ? 1 : 0));
        dest.writeTypedList(subTasks);
    }

    public static class Builder {
        private Task task;

        public Builder(@NonNull String id, @NonNull String name, @NonNull Interval interval) {
            this.task = new Task();
            this.task.id = id;
            this.task.name = name;
            this.task.interval = interval;
        }

        public Builder isComplete() {
            this.task.complete = true;
            return this;
        }

        public Builder setSubTasks(List<Task> subTasks) {
            this.task.subTasks = subTasks;
            return this;
        }

        public Task build() {
            return this.task;
        }
    }

    public static class Interval {
        private Date start;
        private Date finish;

        public Interval() {
        }

        public Interval(Date start, Date finish) {
            this.start = start;
            this.finish = finish;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getFinish() {
            return finish;
        }

        public void setFinish(Date finish) {
            this.finish = finish;
        }
    }
}