package ro.alexsicoe.clepsydra.view.recyclerView.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;

public class TaskViewHolder extends ChildViewHolder {
    @BindView(R.id.tvTaskName)
    TextView tvTaskName;

    public TaskViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void onBind(Task task) {
        tvTaskName.setText(task.getName());
    }


    //TODO set subtasks?
}
