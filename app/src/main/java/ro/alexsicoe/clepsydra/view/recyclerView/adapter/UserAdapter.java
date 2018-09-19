package ro.alexsicoe.clepsydra.view.recyclerView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablerecyclerview.ExpandableListUtils;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.controller.fragment.UserTaskListFragment;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.model.User;
import ro.alexsicoe.clepsydra.view.recyclerView.viewHolder.TaskViewHolder;
import ro.alexsicoe.clepsydra.view.recyclerView.viewHolder.UserViewHolder;

import java.util.List;

public class UserAdapter extends ExpandableRecyclerViewAdapter<UserViewHolder, TaskViewHolder> {

    private Context context;
    private UserTaskListFragment.AddTaskListener addTaskListener;


    public UserAdapter(List<? extends ExpandableGroup> groups, Context context, UserTaskListFragment.AddTaskListener addTaskListener) {
        super(groups);
        this.context = context;
        this.addTaskListener = addTaskListener;
    }


    @Override
    public UserViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view, addTaskListener);
    }

    @Override
    public TaskViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TaskViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Task task = ((User.GroupItem) group).getItems().get(childIndex);
        holder.onBind(task);
    }

    @Override
    public void onBindGroupViewHolder(UserViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setViewHolder((User.GroupItem) group);
    }

    public void addAll(List<User.GroupItem> groupItems) {
        ((List<User.GroupItem>) getGroups()).addAll(groupItems);
        ExpandableListUtils.notifyGroupDataChanged(this);
        notifyDataSetChanged();
    }
}
