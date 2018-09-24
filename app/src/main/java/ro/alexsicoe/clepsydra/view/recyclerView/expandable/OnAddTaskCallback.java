package ro.alexsicoe.clepsydra.view.recyclerView.expandable;

import com.firebase.ui.auth.data.model.User;

@FunctionalInterface
public interface OnAddTaskCallback {
    void onAddTask(User user);
}
