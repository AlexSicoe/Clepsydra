package ro.alexsicoe.clepsydra.view.recyclerView.kanbanBoard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.Task;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {
    TextView tvHeader;
    ArrayList<View> extraViews;
    private Context context;
    private List<Task> items;
    private String header;

    public CardRecyclerViewAdapter(Context context, List<Task> items, String header) {
        this.context = context;
        this.items = items;
        this.header = header;

//        tvHeader = new TextView(context);
//        tvHeader.setText(header);
//
//        extraViews = new ArrayList<>();
//        extraViews.add(tvHeader);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);

//        view.addTouchables(extraViews);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task model = items.get(position);
        holder.setModel(model);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.tvTaskName)
        TextView tvTaskName;
        @BindView(R.id.tvTaskDescription)
        TextView tvTaskDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(Task model) {
            String id = model.getId();
            String name = model.getName();
            String description = model.getDescription();
            List<String> assignedOwners = model.getAssignedOwners();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Tasks").document(id);


            tvTaskName.setText(name);
            tvTaskDescription.setText(description);

            cardView.addView(tvTaskName); //
            cardView.addView(tvTaskDescription);


            itemView.setOnClickListener(v -> {
                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
            });

            itemView.setOnLongClickListener(v -> {
                Toast.makeText(context, "LONG CLICK", Toast.LENGTH_SHORT).show();
                return true;
            });

            itemView.setOnDragListener((v, event) -> {
                Toast.makeText(context, "DRAG", Toast.LENGTH_SHORT).show();
                return false;
            });

            itemView.setOnHoverListener((v, event) -> {
                Toast.makeText(context, "HOVER", Toast.LENGTH_SHORT).show();
                return false;
            });
        }
    }
}
