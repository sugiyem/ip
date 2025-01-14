package duke.command;

import java.util.Arrays;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import javafx.util.Pair;

/**
 * Find command for Duke application.
 *
 * @author Farrel Dwireswara Salim
 */
public class FindCommand implements Command {
    private final String[] keywords;

    /**
     * Constructs a new FindCommand instance based on keyword.
     *
     * @param keyword the keyword string.
     * @throws DukeException If the keyword string is empty.
     */
    public FindCommand(String keyword) throws DukeException {
        if (keyword == null || keyword == "") {
            throw new DukeException("The keyword after find command "
                    + "must be a non-empty string!");
        }

        // Filter out all empty words
        this.keywords = Arrays.stream(keyword.split(","))
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .toArray(String[]::new);
    }

    /**
     * Executes the FindCommand and returns the response pair.
     *
     * @param ui the Ui object to handle user interface.
     * @param storage the storage to be used by the FindCommand.
     * @param taskList the task list to be used by the FindCommand.
     * @return the response pair.
     */
    @Override
    public Pair<Boolean, String> execute(Ui ui, Storage storage, TaskList taskList) {
        TaskList filteredTasks = taskList.filterByKeyword(this.keywords);

        String responseMessage = filteredTasks.isEmpty()
                ? "There are no task that match the keyword"
                : "Here are the tasks in your list that match the keyword\n"
                + filteredTasks;
        ui.printMessage(responseMessage);

        return new Pair<>(true, responseMessage);
    }
}
