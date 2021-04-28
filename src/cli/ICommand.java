package cli;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

public interface ICommand {
        void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException;
        String description();
}
