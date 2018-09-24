package ro.alexsicoe.clepsydra.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ro.alexsicoe.clepsydra.R;

public class KanbanFragment extends Fragment {
    private static final String ARG_PROJECT_ID = "param1";

    private String projectId;


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
        Toast.makeText(getContext(), projectId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kanban, container, false);
        return view;
    }

}
