package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.HasLabels;
import cz.muni.fi.pb162.hw02.LabelMatcher;
import cz.muni.fi.pb162.hw02.error.InvalidExpressionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class implements LabelMatcher
 * @author Michael Skor
 */
public class LabelMatcherImpl implements LabelMatcher {
    private final List<String> expression;

    /**
     * Constructor of LabelMatherImpl
     * @param expression expression
     */
    public LabelMatcherImpl(String expression) {
        if (expression.matches(" *") ||
                !expression.matches("(!*[A-Za-z0-9]+)( *(&|[|]) *!*[A-Za-z0-9]+)*")) {
            throw new InvalidExpressionException(expression);
        }
        this.expression = new ArrayList<>(List.of(expression
                .trim()
                .replaceAll(" +", "")
                .split("((?=&|[|]))")));
    }

    @Override
    public boolean matches(HasLabels labeled) {
        Set<String> labels = labeled.getLabels();
        boolean tempExpression = true;
        for (String exp : expression) {
            if (exp.charAt(0) == '|') {
                tempExpression = tempExpression || getResult(labels, exp.substring(1));
            } else if (exp.charAt(0) == '&') {
                tempExpression = tempExpression && getResult(labels, exp.substring(1));
            } else {
                tempExpression = getResult(labels, exp);
            }
        }
        return tempExpression;
    }

    private boolean getResult(Set<String> labels, String exp) {
        int newIndex = exp.lastIndexOf('!');
        if (newIndex >= 0 && newIndex % 2 == 0) {
            return !labels.contains(exp.substring(newIndex + 1));
        }
        return labels.contains(exp.substring(newIndex + 1));
    }

    @Override
    public boolean all(Iterable<HasLabels> labeled) {
        for (HasLabels labels : labeled) {
            if (!matches(labels)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean any(Iterable<HasLabels> labeled) {
        for (HasLabels labels : labeled) {
            if (matches(labels)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean none(Iterable<HasLabels> labeled) {
        for (HasLabels labels : labeled) {
            if (matches(labels)) {
                return false;
            }
        }
        return true;
    }
}
