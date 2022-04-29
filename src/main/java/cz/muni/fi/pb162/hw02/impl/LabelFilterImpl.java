package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.HasLabels;
import cz.muni.fi.pb162.hw02.LabelFilter;
import cz.muni.fi.pb162.hw02.LabelMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements LabelFilter
 * @author Michael Skor
 */
public class LabelFilterImpl implements LabelFilter {
    private final LabelMatcher matcher;

    /**
     * Constructor for LabelFilterImpl
     * @param expression expression
     */
    public LabelFilterImpl(String expression) {
        matcher = new LabelMatcherImpl(expression);
    }

    @Override
    public Collection<HasLabels> matching(Iterable<HasLabels> labeled) {
        Set<HasLabels> returnLabels = new HashSet<>();
        for (HasLabels labels : labeled) {
            if (matcher.matches(labels)) {
                returnLabels.add(labels);
            }
        }
        return returnLabels;
    }

    @Override
    public Collection<HasLabels> notMatching(Iterable<HasLabels> labeled) {
        Set<HasLabels> returnLabels = new HashSet<>();
        for (HasLabels labels : labeled) {
            if (!matcher.matches(labels)) {
                returnLabels.add(labels);
            }
        }
        return returnLabels;
    }

    @Override
    public Collection<HasLabels> joined(Iterable<HasLabels> fst, Iterable<HasLabels> snd) {
        Set<HasLabels> returnLabels = new HashSet<>();
        for (HasLabels labels : fst) {
            if (matcher.matches(labels)) {
                returnLabels.add(labels);
            }
        }
        for (HasLabels labels : snd) {
            if (matcher.matches(labels)) {
                returnLabels.add(labels);
            }
        }
        return returnLabels;
    }

    @Override
    public Collection<HasLabels> distinct(Iterable<HasLabels> fst, Iterable<HasLabels> snd) {
        Set<HasLabels> returnLabels = new HashSet<>();
        for (HasLabels labels : fst) {
            if (matcher.matches(labels)) {
                returnLabels.add(labels);
            }
        }
        for (HasLabels labels : snd) {
            if (matcher.matches(labels)) {
                if (returnLabels.contains(labels)) {
                    returnLabels.remove(labels);
                } else {
                    returnLabels.add(labels);
                }
            }
        }
        return returnLabels;
    }

    @Override
    public Collection<HasLabels> intersection(Iterable<HasLabels> fst, Iterable<HasLabels> snd) {
        Set<HasLabels> returnLabels = new HashSet<>();
        Set<HasLabels> fstLabels = new HashSet<>();
        for (HasLabels labels : fst) {
            if (matcher.matches(labels)) {
                fstLabels.add(labels);
            }
        }
        for (HasLabels labels : snd) {
            if (matcher.matches(labels)) {
                if (fstLabels.contains(labels)) {
                    returnLabels.add(labels);
                }
            }
        }
        return returnLabels;
    }
}
