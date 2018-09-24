package ro.alexsicoe.clepsydra.view.recyclerView.expandable;


import ro.alexsicoe.clepsydra.model.User;

@FunctionalInterface
public interface OnAddTaskCallback {
    void onAddTask(User user);
}
