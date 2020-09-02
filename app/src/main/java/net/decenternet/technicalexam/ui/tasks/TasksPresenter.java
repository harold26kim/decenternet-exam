package net.decenternet.technicalexam.ui.tasks;

import net.decenternet.technicalexam.data.TaskLocalService;
import net.decenternet.technicalexam.domain.Task;

public class TasksPresenter implements TasksContract.Presenter {

    private final TaskLocalService taskLocalService;
    private TasksContract.View taskView;
    public TasksPresenter(TaskLocalService taskLocalService, TasksContract.View taskView) {
        this.taskLocalService = taskLocalService;
        this.taskView = taskView;
    }

    @Override
    public void getTasks() {
        taskView.displayTasks(taskLocalService.findAll());
    }

    @Override
    public void onAddTaskClicked() {
        taskView.popupTaskAddingDialog();
    }

    @Override
    public void onEditTaskClicked(Task task) {
        taskView.popupTaskEditDialog(task);
    }

    @Override
    public void onDeleteTaskClicked(Task task) {
        taskView.popupTaskDeleteDialog(task);
    }

    @Override
    public void onTaskChecked(Task task) {
        taskLocalService.edit(task);
        taskView.updateTaskInList(task);
    }

    @Override
    public void doAddTask(Task task) {
        taskLocalService.save(task);
        taskView.addTaskToList(task);
    }

    @Override
    public void doDeleteTask(Task task) {
        taskLocalService.remove(task);
        taskView.removeTaskFromList(task);
    }

    @Override
    public void doEditTask(Task task) {
        taskLocalService.edit(task);
        taskView.updateTaskInList(task);
    }
}
