package ro.alexsicoe.clepsydra.view.recyclerView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.controller.fragment.ProjectListFragment;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.view.recyclerView.viewHolder.ProjectViewHolder;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private final List<Project> items;
    private Context context;
    private String userEmail;

    public ProjectRecyclerViewAdapter(List<Project> items, Context context, String userEmail) {
        this.items = items;
        this.context = context;
        this.userEmail = userEmail;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectViewHolder holder, int position) {
        Project model = items.get(position);
        holder.setModel(context, userEmail, model);
    }




}