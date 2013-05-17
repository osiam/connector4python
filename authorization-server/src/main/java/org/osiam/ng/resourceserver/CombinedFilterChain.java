/*
 * Copyright 2013
 *     tarent AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osiam.ng.resourceserver;


import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CombinedFilterChain implements FilterChain {
    static final Pattern COMBINED_FILTER_CHAIN =
            Pattern.compile("[\\(]{0,1}([\\S ]+?)[\\)]{0,1} (and|AND|OR|or) [\\(]{0,1}([\\S ]+?)[\\)]{0,1}");
    private final FilterChain term1;
    private final Combiner combinedWith;
    private final FilterChain term2;
    private FilterParser filterParser = new FilterParser();

    public CombinedFilterChain(String chain) {
        Matcher matcher = COMBINED_FILTER_CHAIN.matcher(chain);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(chain + " is not a CombinedFilterChain.");
        }
        this.term1 = filterParser.parse(matcher.group(1));
        this.combinedWith = Combiner.fromString(matcher.group(2));
        this.term2= filterParser.parse(matcher.group(3));
    }

    @Override
    public Criterion buildCriterion() {
        if (combinedWith == Combiner.AND)
            return Restrictions.and(term1.buildCriterion(), term2.buildCriterion());
        else if (combinedWith == Combiner.OR)
            return Restrictions.or(term1.buildCriterion(), term2.buildCriterion());
        throw new IllegalArgumentException("Combined with is unknown: "+combinedWith);
    }


    public enum Combiner {
        AND("and"),
        OR("or");
        static Map<String, Combiner> fromString = new ConcurrentHashMap<>();

        static {
            for (Combiner k : values()) {
                fromString.put(k.constraint, k);
            }
        }

        private final String constraint;


        Combiner(String constraint) {
            this.constraint = constraint;
        }

        static String createOrConstraints() {
            StringBuilder sb = new StringBuilder();
            for (Combiner k : values()) {
                if (sb.length() != 0) {
                    sb.append("|");
                }
                sb.append(k.constraint);
            }
            return sb.toString();

        }


        public static Combiner fromString(String group) {
            return fromString.get(group.toLowerCase());
        }
    }
}
