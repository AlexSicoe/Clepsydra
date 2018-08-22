package ro.alexsicoe.clepsydra.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Task {
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
