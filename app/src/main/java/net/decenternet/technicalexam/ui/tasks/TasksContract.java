package net.decenternet.technicalexam.ui.tasks;

import net.decenternet.technicalexam.domain.Task;

import java.util.List;

public interface TasksContract {
    public interface View {
        void displayTasks(List<Task> tasks);

        void addTaskToList(Task task);
        void removeTaskFromList(Task task);
        void updateTaskInList(Task task);

        void popupTaskAddingDialog();
        void popupTaskEditDialog(Task task);
        void popupTaskDeleteDialog(Task task);


    }
    
    public interface Presenter {
        void getTasks();

        void onAddTaskClicked();
        void onDeleteTaskClicked(Task task);
        void onEditTaskClicked(Task task);

        void onTaskChecked(Task task);
        void doAddTask(Task task);
        void doDeleteTask(Task task);
        void doEditTask(Task task);
    }
    
}
