package utility;

import java.io.PrintStream;

/**
 * The class is an extension of {@link PrintStream} that allows
 * printing colored text to the terminal. This class allows for color customization in console output.
 * These codes are reset after each print operation to ensure the color does not persist for subsequent text.
 */
public class ColoredPrintStream extends PrintStream {
    private String currentColor = TerminalColors.RESET;

    /**
     * Constructs a new {@code ColoredPrintStream} that wraps around an existing {@link PrintStream}.
     * This class will modify the output by adding color codes before printing the message.
     *
     * @param original The original {@link PrintStream} that will be wrapped and used for output.
     */
    public ColoredPrintStream(PrintStream original) {
        super(original);
    }

    /**
     * Sets the color for the text that will be printed.
     *
     * @param color The color code to be set for text output.
     */
    public void setColor(String color) {
        this.currentColor = color;
    }

    /** Resets the color to the default color (no color). */
    public void resetColor() {
        this.currentColor = TerminalColors.RESET;
    }

    /**
     * Prints a message followed by a new line with the current color applied.
     * <p>
     * This method overrides {@link PrintStream#println(String)} to apply the current color 
     * before printing the message. The color is reset after the message.
     *
     * @param message The message to be printed.
     */
    @Override
    public void println(String message) {
        super.println(currentColor + message + TerminalColors.RESET);
    }

    /**
     * Prints a message with the current color applied.
     * <p>
     * This method overrides {@link PrintStream#print(String)} to apply the current color 
     * before printing the message. The color is reset after the message.
     *
     * @param message The message to be printed.
     */
    @Override
    public void print(String message) {
        super.print(currentColor + message + TerminalColors.RESET);
    }
}

