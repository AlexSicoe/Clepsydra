package ro.alexsicoe.clepsydra.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import ro.alexsicoe.clepsydra.util.DateUtil;

public class Taskk implements Parcelable {
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
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String ownerEmail;
    private boolean complete;
    @NonNull
    private Interval interval;
    @Nullable
    private List<Taskk> subTaskks;


    public Taskk() {
    }

    protected Taskk(Parcel in) {
        id = in.readString();
        name = in.readString();
        ownerEmail = in.readString();
        complete = in.readByte() != 0;
        subTaskks = in.createTypedArrayList(Taskk.CREATOR);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public Taskk setId(@NonNull String id) {
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

    @NonNull
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public Taskk setOwnerEmail(@NonNull String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(ownerEmail);
        dest.writeByte((byte) (complete ? 1 : 0));
        dest.writeTypedList(subTaskks);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Taskk taskk = (Taskk) o;

        return id.equals(taskk.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Taskk{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", complete=" + complete +
                ", interval=" + interval +
                ", subTaskks=" + subTaskks +
                '}';
    }

    public static class Builder {
        private Taskk taskk;

        public Builder(@NonNull String id, @NonNull String name, @NonNull String ownerEmail, @NonNull Interval interval) {
            this.taskk = new Taskk();
            this.taskk.id = id;
            this.taskk.name = name;
            this.taskk.interval = interval;
            this.taskk.ownerEmail = ownerEmail;
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
        @NonNull
        private Date start;
        @NonNull
        private Date finish;

        public Interval() {
        }

        public Interval(@NonNull Date start, @NonNull Date finish) {
            this.start = start;
            this.finish = finish;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(@NonNull Date start) {
            this.start = start;
        }

        public Date getFinish() {
            return finish;
        }

        public void setFinish(@NonNull Date finish) {
            this.finish = finish;
        }

        @Override
        public String toString() {
            DateFormat df = DateUtil.getDefaultDateTimeFormat();
//            DateFormat  df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            return "Interval{" +
                    "start=" + df.format(start) +
                    ", finish=" + df.format(finish) +
                    '}';
        }
    }
}
