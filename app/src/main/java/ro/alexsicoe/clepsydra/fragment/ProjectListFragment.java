package ro.alexsicoe.clepsydra.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.activity.AddProjectActivity;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.model.RequestType;

public class ProjectListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String STR_PROJECT = "project";
    private static final String STR_INDEX = "index";
    @Nullable
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int columnCount = 1;
    private List<Project> projects;
    private Unbinder unbinder;

    public static ProjectListFragment newInstance(int columnCount) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        final Context context = view.getContext();
        ButterKnife.bind(this, view);
        projects = new ArrayList<>();
        //TODO load from db
        initLayoutManager(context);
        recyclerView.setAdapter(new ProjectRecyclerViewAdapter(projects, new OnItemClickListener() {
                    @Override
                    public void onClickItem(Project project, int index) {
                        //TODO start ProjectActivity
                        Toast.makeText(context, project.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
        initItemTouchHelper();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClickFab() {
        Intent intent = new Intent(getContext(), AddProjectActivity.class);
        startActivityForResult(intent, RequestType.ADD.getCode());
    }

    private void initLayoutManager(Context context) {
        if (columnCount < 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestType.ADD.getCode()) {
                Project project = (Project) data.getSerializableExtra(STR_PROJECT);

                projects.add(project);
                //TODO insert into DB
                recyclerView.getAdapter().notifyItemInserted(projects.size() - 1);
                Toast.makeText(getContext(), "Project added successfully!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (requestCode == RequestType.EDIT.getCode()) {
                Project project = (Project) data.getSerializableExtra(STR_PROJECT);
                int index = data.getIntExtra(STR_INDEX, projects.size() - 1);
                projects.set(index, project);
                //TODO update DB
                recyclerView.getAdapter().notifyItemChanged(index);
                Toast.makeText(getContext(), "Project modified!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                throw new Exception("Request code " + requestCode + " not implemented!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initItemTouchHelper() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Project project = projects.remove(position);
                //TODO delete from db
                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public interface OnItemClickListener {
        void onClickItem(Project project, int index);
    }
}
