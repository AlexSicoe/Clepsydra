package ro.alexsicoe.clepsydra.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.util.DateUtil;

@Deprecated
class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder> {
    private final List<Project> items;
    private final ProjectListFragment.OnItemClickListener listener;

    ProjectRecyclerViewAdapter(List<Project> items, ProjectListFragment.OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_project_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.tvProjectName.setText(holder.item.getName());
        holder.tvCreatedBy.setText(holder.item.getCreatedBy());
        DateFormat df =  new DateUtil(holder.view.getContext()).getDateFormat(DateFormat.MEDIUM);
        holder.tvStartDate.setText(df.format(holder.item.getStart()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClickItem(holder.item, holder.getAdapterPosition());
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.tvProjectName)
        TextView tvProjectName;
        @BindView(R.id.tvCreatedBy)
        TextView tvCreatedBy;
        @BindView(R.id.tvStartDate)
        TextView tvStartDate;
        private Project item;

        private ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
