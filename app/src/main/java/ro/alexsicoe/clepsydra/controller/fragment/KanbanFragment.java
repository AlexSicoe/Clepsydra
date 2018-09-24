package ro.alexsicoe.clepsydra.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;
import ro.alexsicoe.clepsydra.view.recyclerView.kanbanBoard.CardRecyclerViewAdapter;

public class KanbanFragment extends Fragment {
    private static final String ARG_PROJECT_ID = "param1";

    private String projectId;
    private LinkedList<Task> tasks;

    private Unbinder unbinder;
    private FirebaseFirestore db;
    private CardRecyclerViewAdapter adapter;

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
        tasks = new LinkedList<>();
        adapter = new CardRecyclerViewAdapter(context, tasks, "Titlu");
        readMockTasks();

        return view;
    }

    private void readTasks() {
        //TODO
    }

    private void readMockTasks() {
        for (int i = 0; i < 5; i++) {
            Task.Phase phase = new Task.Phase("In progress");
            Task task = new Task("000", "Task1", phase);
            tasks.addLast(task);
        }
        adapter.notifyDataSetChanged();
    }

}
