package ro.alexsicoe.clepsydra.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Project;
import ro.alexsicoe.clepsydra.util.DateUtil;

public class ProjectFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<Project, ProjectFirestoreRecyclerAdapter.ViewHolder> {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProjectFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Project> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull Project model) {
        holder.item = getSnapshots().get(position);
        holder.tvProjectName.setText(holder.item.getName());
        holder.tvCreatedBy.setText(holder.item.getCreatedBy());
        DateFormat df = new DateUtil(holder.view.getContext()).getDateFormat(DateFormat.MEDIUM);
        holder.tvStartDate.setText(df.format(holder.item.getStart()));

//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != listener) {
//                    listener.onClickItem(holder.item, holder.getAdapterPosition());
//                }
//            }
//        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_project_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDataChanged() {
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if(getItemCount()==0){
//            recyclerView.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
//        return items.size();
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
