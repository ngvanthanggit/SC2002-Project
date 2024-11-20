package utility;

import java.io.PrintStream;

public class ColoredPrintStream extends PrintStream {
    private String currentColor = TerminalColors.RESET;

    public ColoredPrintStream(PrintStream original) {
        super(original);
    }

    public void setColor(String color) {
        this.currentColor = color;
    }

    public void resetColor() {
        this.currentColor = TerminalColors.RESET;
    }

    @Override
    public void println(String message) {
        super.println(currentColor + message + TerminalColors.RESET);
    }

    @Override
    public void print(String message) {
        super.print(currentColor + message + TerminalColors.RESET);
    }
}

