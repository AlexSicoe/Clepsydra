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
    private String name;
    private boolean isComplete;
    @NonNull
    private Interval interval;
    @Nullable
    private List<Task> subTasks;

    public Task() {

    }

    public Task(@NonNull String name, boolean isComplete, @NonNull Interval interval, List<Task> subTasks) {
        this.name = name;
        this.isComplete = isComplete;
        this.interval = interval;
        this.subTasks = subTasks;
    }

    private Task(Parcel in) {
        name = in.readString();
        isComplete = in.readByte() != 0;
        subTasks = in.createTypedArrayList(Task.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isComplete ? 1 : 0));
        dest.writeTypedList(subTasks);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
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

    public class Interval {
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
