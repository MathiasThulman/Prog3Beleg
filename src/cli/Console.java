package cli;

import java.util.Scanner;

public class Console {
    private InputMode mode;
    private InputReader inputReader;


    public void read() {

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        if (input.length() == 2 && input.charAt(0) == ':') {
            changeMode(input);
        } else if (input.length() >= 1) {
            executeCommand(input);
        } else {
            return;
        }

    }

    private void changeMode(String input) {
        switch (input) {
            case ":c":
                this.mode = InputMode.insertMode;
                break;
            case ":d":
                this.mode = InputMode.deleteMode;
                break;
            case ":r":
                this.mode = InputMode.displayMode;
                break;
            case ":u":
                this.mode = InputMode.changeMode;
                break;
            case ":p":
                this.mode = InputMode.persistence;
                break;
            default:
                break;
        }
    }

    public void executeCommand(String input) {
        switch (this.mode) {
            case insertMode:
                this.inputReader.readInsert(input);
                break;
            case deleteMode:
                this.inputReader.readDelete(input);
                break;
            case displayMode:
                this.inputReader.readDisplay(input);
                break;
            case changeMode:
                this.inputReader.readChange(input);
                break;
            case persistence:
                this.inputReader.readPersistence(input);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public void setInputReader(InputReader inputReader) {
        this.inputReader = inputReader;
    }
}
