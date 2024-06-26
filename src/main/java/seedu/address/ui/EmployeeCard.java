package seedu.address.ui;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.employee.Employee;

/**
 * A UI component that displays information of a {@code Employee}.
 */
public class EmployeeCard extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Employee employee;

    @FXML
    private Label teamRole;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label uid;
    @FXML
    private VBox taskList;
    @FXML
    private Label completionRate;

    /**
     * Creates a {@code EmployeeCode} with the given {@code Employee} and index to
     * display.
     */
    public EmployeeCard(Employee employee, int displayedIndex) {
        super(FXML);
        this.employee = employee;
        updateTaskList(employee);
        teamRole.setText(employee.getTeam().toString() + " | " + employee.getRole().value);
        id.setText(displayedIndex + ". ");
        name.setText(employee.getName().fullName);
        phone.setText(employee.getPhone().value);
        address.setText(employee.getAddress().value);
        email.setText(employee.getEmail().value);
        uid.setText("uid: " + employee.getUid().toString());
        completionRate.setText(" | " + "Completion Rate: " + employee.getTaskCompletionRate() + "%");
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

    }

    /**
     * Updates the task list in the UI.
     */
    public void updateTaskList(Employee employee) {
        taskList.getChildren().clear(); // Clear existing task views

        AtomicInteger taskIndex = new AtomicInteger(1);
        employee.getTasks().stream()
                .map(task -> {
                    String status = task.isDone() ? "[X] " : "[_] ";
                    return new Label(taskIndex.getAndIncrement() + ". " + status + task.getDescription());
                })
                .forEach(taskLabel -> taskList.getChildren().add(taskLabel));
    }
}
