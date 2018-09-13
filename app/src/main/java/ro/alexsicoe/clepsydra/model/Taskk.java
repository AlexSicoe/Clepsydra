package ro.alexsicoe.clepsydra.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Taskk implements Parcelable {

    @NonNull
    private String name;
    private boolean complete;
    @NonNull
    private Interval interval;
    @Nullable
    private List<Taskk> subTaskks;

    public Taskk() {
    }

    private Taskk(Parcel in) {
        name = in.readString();
        complete = in.readByte() != 0;
        subTaskks = in.createTypedArrayList(Taskk.CREATOR);
    }

    public static final Creator<Taskk> CREATOR = new Creator<Taskk>() {
        @Override
        public Taskk createFromParcel(Parcel in) {
            return new Taskk(in);
        }

        @Override
        public Taskk[] newArray(int size) {
            return new Taskk[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (complete ? 1 : 0));
        dest.writeTypedList(subTaskks);
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
    public List<Taskk> getSubTaskks() {
        return subTaskks;
    }

    public void setSubTaskks(@Nullable List<Taskk> subTaskks) {
        this.subTaskks = subTaskks;
    }

    public static class Builder {
        private Taskk taskk;

        public Builder(@NonNull String name, @NonNull Interval interval) {
            this.taskk = new Taskk();
            this.taskk.name = name;
            this.taskk.interval = interval;
        }

        public Builder isComplete() {
            this.taskk.complete = true;
            return this;
        }

        public Builder setSubTasks(List<Taskk> subTaskks) {
            this.taskk.subTaskks = subTaskks;
            return this;
        }

        public Taskk build() {
            return this.taskk;
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
