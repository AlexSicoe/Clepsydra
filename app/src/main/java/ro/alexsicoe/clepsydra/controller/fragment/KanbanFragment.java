package ro.alexsicoe.clepsydra.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.allyants.boardview.BoardView;
import com.allyants.boardview.SimpleBoardAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.view.recyclerView.kanbanBoard.TaskRecyclerViewAdapter;

public class KanbanFragment extends Fragment {
    private final static String TAG = KanbanFragment.class.getSimpleName();
    private static final String ARG_PROJECT_ID = "projectId";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.boardView)
    BoardView boardView;

    private String projectId;
    private Unbinder unbinder;
    private FirebaseFirestore db;
    private List<Task> tasks;
    private TaskRecyclerViewAdapter adapter;

    public KanbanFragment() {
        // Required empty public constructor
    }

    public static KanbanFragment newInstance(String projectId) {
        KanbanFragment fragment = new KanbanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getString(ARG_PROJECT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kanban, container, false);
        Context context = view.getContext();
        unbinder = ButterKnife.bind(this, view);
        //getAccountDetails(context);


        db = FirebaseFirestore.getInstance();
        tasks = new ArrayList<>();
        adapter = new TaskRecyclerViewAdapter(getContext(), tasks);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//        readMockTasks();

        mockBoard();

        return view;
    }

    private void mockBoard() {
        List<Project.Phase> mockPhases = new ArrayList<>();
        mockPhases.add(new Project.Phase("Todo"));
        mockPhases.add(new Project.Phase("In progress"));
        mockPhases.add(new Project.Phase("Done"));


        ArrayList<SimpleBoardAdapter.SimpleColumn> data = new ArrayList<>();
        List<Task> tasks = readMockTasks(mockPhases);
        for (Project.Phase phase : mockPhases) {
            ArrayList<Object> columnData = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getPhase() == phase) {
                    columnData.add(task);
                }
            }
            data.add(new SimpleBoardAdapter.SimpleColumn(phase.getName(), columnData));
        }

        SimpleBoardAdapter boardAdapter = new SimpleBoardAdapter(getContext(), data);
        boardView.setAdapter(boardAdapter);


        boardView.setOnDragColumnListener(new BoardView.DragColumnStartCallback() {
            @Override
            public void startDrag(View view, int startColumnPos) {
                Log.d(TAG, "startDrag() called with: view = [" + view + "], startColumnPos = [" + startColumnPos + "]");
            }

            @Override
            public void changedPosition(View view, int startColumnPos, int newColumnPos) {

            }

            @Override
            public void dragging(View itemView, MotionEvent event) {

            }

            @Override
            public void endDrag(View view, int startColumnPos, int endColumnPos) {
                Log.d(TAG, "endDrag() called with: view = [" + view + "], startColumnPos = [" + startColumnPos + "], endColumnPos = [" + endColumnPos + "]");
            }
        });

        boardView.setOnDragItemListener(new BoardView.DragItemStartCallback() {
            @Override
            public void startDrag(View view, int startItemPos, int startColumnPos) {
                Log.d(TAG, "startDrag() called with: view = [" + view + "], startItemPos = [" + startItemPos + "], startColumnPos = [" + startColumnPos + "]");
            }

            @Override
            public void changedPosition(View view, int startItemPos, int startColumnPos, int newItemPos, int newColumnPos) {

            }

            @Override
            public void dragging(View itemView, MotionEvent event) {

            }

            @Override
            public void endDrag(View view, int startItemPos, int startColumnPos, int endItemPos, int endColumnPos) {
                Log.d(TAG, "endDrag() called with: view = [" + view + "], startItemPos = [" + startItemPos
                        + "], startColumnPos = [" + startColumnPos + "], endItemPos = [" + endItemPos + "], endColumnPos = [" + endColumnPos + "]");
            }
        });
    }

    private void readTasks() {
        //TODO
    }


    private List<Task> readMockTasks(List<Project.Phase> phases) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Task task = new Task("000", "TaskA" + i + 1, phases.get(0)).setDescription(getResources().getString(R.string.lorem_ipsum));
            tasks.add(task);
        }
        for (int i = 0; i < 5; i++) {
            Task task = new Task("001", "TaskB" + i + 1, phases.get(1)).setDescription(getResources().getString(R.string.lorem_ipsum));
            tasks.add(task);
        }
        for (int i = 0; i < 2; i++) {
            Task task = new Task("002", "TaskC" + i + 1, phases.get(2)).setDescription(getResources().getString(R.string.lorem_ipsum));
            tasks.add(task);
        }
//        recyclerView.getAdapter().notifyDataSetChanged();
        return tasks;
    }

}
