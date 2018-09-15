package ro.alexsicoe.clepsydra.view.recyclerView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.view.recyclerView.viewHolder.TaskViewHolder;
import ro.alexsicoe.clepsydra.view.recyclerView.viewHolder.UserViewHolder;
import ro.alexsicoe.clepsydra.model.User;

public class UserAdapter extends ExpandableRecyclerViewAdapter<UserViewHolder, TaskViewHolder> {

    Context context;

    public UserAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public TaskViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TaskViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Task task = ((User.Group) group).getItems().get(childIndex);
        holder.onBind(task);
    }

    @Override
    public void onBindGroupViewHolder(UserViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group);
    }
}
