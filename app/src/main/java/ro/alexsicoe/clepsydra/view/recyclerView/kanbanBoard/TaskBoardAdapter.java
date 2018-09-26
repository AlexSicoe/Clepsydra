package ro.alexsicoe.clepsydra.view.recyclerView.kanbanBoard;

import android.content.Context;
import android.view.View;

import com.allyants.boardview.BoardAdapter;
import com.allyants.boardview.Item;

import java.util.ArrayList;

import ro.alexsicoe.clepsydra.model.Project;

public class TaskBoardAdapter extends BoardAdapter {

    public TaskBoardAdapter(Context context) {
        super(context);
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public int getItemCount(int i) {
        return 0;
    }

    @Override
    public int maxItemCount(int i) {
        return 0;
    }

    @Override
    public Object createHeaderObject(int i) {
        return null;
    }

    @Override
    public Object createFooterObject(int i) {
        return null;
    }

    @Override
    public Object createItemObject(int i, int i1) {
        return null;
    }

    @Override
    public boolean isColumnLocked(int i) {
        return false;
    }

    @Override
    public boolean isItemLocked(int i) {
        return false;
    }

    @Override
    public View createItemView(Context context, Object o, Object o1, int i, int i1) {
        return null;
    }

    @Override
    public View createHeaderView(Context context, Object o, int i) {
        return null;
    }

    @Override
    public View createFooterView(Context context, Object o, int i) {
        return null;
    }

    public static class TaskColumn extends Column {
        public String title;

        public TaskColumn(Project.Phase phase, ArrayList<Object> items) {
            this.title = phase.getName();
            this.header_object = new Item(title);
            this.objects = items;
        }
    }
}
