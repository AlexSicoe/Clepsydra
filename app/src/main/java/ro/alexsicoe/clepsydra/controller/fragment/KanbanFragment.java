package ro.alexsicoe.clepsydra.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.view.recyclerView.kanbanBoard.TaskRecyclerViewAdapter;

public class KanbanFragment extends Fragment {
    private static final String ARG_PROJECT_ID = "param1";

    private String projectId;
    @BindView(R.id.recyclerViewKanban)
    RecyclerView recyclerView;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        readMockTasks();


        return view;
    }

    private void readTasks() {
        //TODO
    }

    private void readMockTasks() {
        tasks.clear();
        for (int i = 0; i < 5; i++) {
            Task.Phase phase = new Task.Phase("In progress");
            Task task = new Task("000", "Task1", phase).setDescription(getResources().getString(R.string.lorem_ipsum));
            tasks.add(task);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
