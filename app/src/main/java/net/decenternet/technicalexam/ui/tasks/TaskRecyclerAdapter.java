package net.decenternet.technicalexam.ui.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.domain.Task;

import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {

    interface OnItemClickListener {
        void onItemDeleteClick(Task task);
        void onItemEditClick(Task task);
        void onCheckClicked(Task task);
    }

    private OnItemClickListener onItemClickListener;
    private List<Task> tasks;

    public TaskRecyclerAdapter(OnItemClickListener onItemClickListener, List<Task> tasks) {
        this.onItemClickListener = onItemClickListener;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_holder, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.checkBoxDescription.setChecked(task.isChecked());
        holder.checkBoxDescription.setText(task.getDescription());

        holder.checkBoxDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = tasks.get(holder.getAdapterPosition());
                task.setChecked(holder.checkBoxDescription.isChecked());
                onItemClickListener.onCheckClicked(task);
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = tasks.get(holder.getAdapterPosition());
                onItemClickListener.onItemEditClick(task);
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = tasks.get(holder.getAdapterPosition());
                onItemClickListener.onItemDeleteClick(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBoxDescription;
        public Button buttonEdit;
        public Button buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxDescription = itemView.findViewById(R.id.checkbox_description);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}
