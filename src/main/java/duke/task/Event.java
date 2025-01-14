package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.EventException;

/**
 * Event task for Duke application.
 *
 * @author Farrel Dwireswara Salim
 */
public class Event extends Task {
    private final String timeString;
    private final LocalDate time;

    /**
     * Constructs a new Event instance.
     *
     * @param description the description of the task.
     * @param timeString the string which represents the time of the task.
     * @param tags the tags of the task.
     * @throws EventException If timeString is not valid.
     */
    public Event(String description, String timeString, String ... tags)
            throws EventException {
        super(description, tags);

        try {
            this.timeString = timeString;
            this.time = LocalDate.parse(this.timeString);
        } catch (DateTimeParseException error) {
            throw new EventException("The time given is not a valid date. "
                    + "Try to represent the time in yyyy-mm-dd format.");
        }
    }

    /**
     * Gets the string representation of the Event.
     *
     * @return the string which represents the current Event.
     */
    @Override
    public String toString() {
        String tagInfo = super.getTagsString().isEmpty()
                ? ""
                : "[" + super.getTagsString() + "]";

        return String.format("[E]%s (at: %s) %s", super.toString(),
                this.time.format(DateTimeFormatter.ofPattern("MMM d yyyy")),
                tagInfo);
    }

    /**
     * Transforms the Event to a string that is compatible to Duke's storage.
     *
     * @return the string to be saved in storage.
     */
    @Override
    public String toStorageRepresentation() {
        return String.format("E|%s|%s#%s", super.toStorageRepresentation(),
                this.timeString, super.getTagsString());
    }

    /**
     * Returns true if the Event takes place on the given date.
     *
     * @param selectedDates the date objects.
     * @return true if the Event happens on the selected date, false otherwise.
     */
    @Override
    protected boolean isOnGivenDate(LocalDate ... selectedDates) {
        for (int i = 0; i < selectedDates.length; i++) {
            if (this.time.equals(selectedDates[i])) {
                return true;
            }
        }

        return false;
    }
}
