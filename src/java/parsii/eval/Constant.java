/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package parsii.eval;

/**
 * Represents a constant numeric expression.
 */
public class Constant extends Expression {
    private double value;

    /**
     * Used as dummy expression by the parser if an error occurs while parsing.
     */
    public static final Constant EMPTY = new Constant(Double.NaN);

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
