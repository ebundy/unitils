/*
 * Copyright 2006-2007,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitils.reflectionassert.formatter.util;

import org.unitils.reflectionassert.difference.*;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * A utility class to be able to calculate a score of how well 2 elements match. This enables us to find
 * the best matching differences out of all element differences in an unordered collection difference.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 */
public class MatchingScoreCalculator {

    /**
     * The visitor for visiting the difference tree
     */
    protected MatchingScoreVisitor matchingScoreVisitor = new MatchingScoreVisitor();

    /**
     * Cache for matching scores
     */
    protected Map<Difference, Integer> cachedMatchingScores = new IdentityHashMap<Difference, Integer>();


    /**
     * Gets the matching score for the given difference.
     *
     * @param difference The difference
     * @return The score
     */
    public int calculateMatchingScore(Difference difference) {
        if (difference == null) {
            return 0;
        }
        Integer matchingScore = cachedMatchingScores.get(difference);
        if (matchingScore == null) {
            matchingScore = difference.accept(matchingScoreVisitor, null);
            cachedMatchingScores.put(difference, matchingScore);
        }
        return matchingScore;
    }


    /**
     * Gets the matching score for a simple difference.
     * This will return 0 in case both objects are of the same type.
     * If both objects are of a different type, they are less likely to be a best match, so 5 is returned.
     *
     * @param difference The difference, not null
     * @return The score
     */
    protected int getMatchingScore(Difference difference) {
        Object leftValue = difference.getLeftValue();
        Object rightValue = difference.getRightValue();
        if (leftValue != null && rightValue != null && !leftValue.getClass().equals(rightValue.getClass())) {
            return 5;
        }
        return 1;
    }


    /**
     * Gets the matching score for an object difference.
     * Returns the nr of field differences.
     *
     * @param objectDifference The difference, not null
     * @return The score
     */
    protected int getMatchingScore(ObjectDifference objectDifference) {
        return objectDifference.getFieldDifferences().size();
    }


    /**
     * Gets the matching score for a map difference.
     * Returns the nr of value differences.
     *
     * @param mapDifference The difference, not null
     * @return The score
     */
    protected int getMatchingScore(MapDifference mapDifference) {
        return mapDifference.getValueDifferences().size();
    }


    /**
     * Gets the matching score for a collection difference.
     * Returns the nr of element differences.
     *
     * @param collectionDifference The difference, not null
     * @return The score
     */
    protected int getMatchingScore(CollectionDifference collectionDifference) {
        return collectionDifference.getElementDifferences().size();
    }


    /**
     * Gets the matching score for an unordered collection difference.
     * Returns the sum of the matching scores of the best matches.
     *
     * @param unorderedCollectionDifference The difference, not null
     * @return The score
     */
    protected int getMatchingScore(UnorderedCollectionDifference unorderedCollectionDifference) {
        return unorderedCollectionDifference.getBestMatchingScore();
    }


    /**
     * The visitor for visiting the difference tree.
     */
    protected class MatchingScoreVisitor implements DifferenceVisitor<Integer, Integer> {

        public Integer visit(Difference difference, Integer argument) {
            return getMatchingScore(difference);
        }

        public Integer visit(ObjectDifference objectDifference, Integer argument) {
            return getMatchingScore(objectDifference);
        }

        public Integer visit(MapDifference mapDifference, Integer argument) {
            return getMatchingScore(mapDifference);
        }

        public Integer visit(CollectionDifference collectionDifference, Integer argument) {
            return getMatchingScore(collectionDifference);
        }

        public Integer visit(UnorderedCollectionDifference unorderedCollectionDifference, Integer argument) {
            return getMatchingScore(unorderedCollectionDifference);
        }
    }
}