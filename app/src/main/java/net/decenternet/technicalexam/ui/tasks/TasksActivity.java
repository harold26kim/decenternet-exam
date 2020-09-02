package net.decenternet.technicalexam.ui.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.data.TaskLocalServiceProvider;
import net.decenternet.technicalexam.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity implements TasksContract.View, TaskRecyclerAdapter.OnItemClickListener {

    private TasksContract.Presenter presenter;
    private static final String PREFERENCE_NAME = "my_exam";
    private List<Task> tasks = new ArrayList<>();
    private Context context = this;
    private TaskRecyclerAdapter taskAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initViews();

        presenter = new TasksPresenter(
                new TaskLocalServiceProvider(
                        getSharedPreferences(PREFERENCE_NAME,
                                Context.MODE_PRIVATE
                        )
                ),
                this);
        presenter.getTasks();

        /**
         * Finish this simple task recording app.
         * The following are the remaining todos for this project:
         * 1. Make sure all defects are fixed.
         * 2. Showing a dialog to add/edit the task.
         * 3. Allowing the user to toggle it as completed.
         * 4. Allowing the user to delete a task.
         *
         */
    }

    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddTaskClicked();
            }
        });
    }

    private void setUpAdapter(){
        taskAdapter = new TaskRecyclerAdapter(this, tasks);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        this.tasks = tasks;
        setUpAdapter();
    }

    @Override
    public void addTaskToList(Task task) {
        tasks.add(task);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeTaskFromList(Task task) {
        tasks.remove(task);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateTaskInList(Task task) {
        int index = tasks.indexOf(task);
        tasks.set(index, task);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void popupTaskAddingDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Add Task");
        alertDialog.setMessage("Please enter task");

        final EditText editTask = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editTask.setLayoutParams(lp);
        alertDialog.setView(editTask);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String description = editTask.getText().toString();
                        if ( description.isEmpty() )
                            Toast.makeText(context, "Field is empty.", Toast.LENGTH_LONG).show();
                        else
                            presenter.doAddTask(new Task(description));
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void popupTaskEditDialog(final Task task) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Edit Task");

        final EditText editTask = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editTask.setLayoutParams(lp);
        alertDialog.setView(editTask);
        editTask.setText(task.getDescription());

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String description = editTask.getText().toString();
                        if ( description.isEmpty() )
                            Toast.makeText(context, "Field is empty.", Toast.LENGTH_LONG).show();
                        else{
                            task.setDescription(description);
                            presenter.doEditTask(task);
                        }
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void popupTaskDeleteDialog(final Task task) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Delete Task");
        alertDialog.setMessage("Are you sure you want to delete " + task.getDescription() + "?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.doDeleteTask(task);
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onCheckClicked(Task task) {
        presenter.onTaskChecked(task);
    }

    @Override
    public void onItemDeleteClick(Task task) {
        presenter.onDeleteTaskClicked(task);
    }

    @Override
    public void onItemEditClick(Task task) {
        presenter.onEditTaskClicked(task);
    }
}
