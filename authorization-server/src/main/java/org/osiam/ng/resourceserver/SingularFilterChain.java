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


import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingularFilterChain implements FilterChain {
    static final Pattern SINGULAR_CHAIN_PATTERN =
            Pattern.compile("(\\S+) (" + Constraints.createOrConstraints() + ")[ ]??(\\S*)");
    private final String key;
    private final Constraints constraint;
    private final String value;

    public SingularFilterChain(String chain) {
        if (chain == null || chain.isEmpty()) {
            this.key = null;
            this.constraint = Constraints.fromString.get("empty");
            this.value = null;
        } else {
            Matcher matcher = SINGULAR_CHAIN_PATTERN.matcher(chain);
            if (!matcher.matches()) { throw new IllegalArgumentException(chain + " is not a SingularFilterChain."); }
            this.key = matcher.group(1);
            this.constraint = Constraints.fromString.get(matcher.group(2));
            this.value = matcher.group(3);
        }
    }

    @Override
    public Query buildQuery(QueryBuilder queryBuilder, Criteria criteria) {
        switch (constraint) {
            case CONTAINS:
                return queryBuilder.keyword().wildcard().onField(key).matching("*" + value + "*").createQuery();
            case STARTS_WITH:
                return queryBuilder.keyword().wildcard().onField(key).matching(value + "*" ).createQuery();
            case EQUALS:
                return queryBuilder.keyword().onField(key).matching(value).createQuery();
            case GREATER_EQUALS:
                return queryBuilder.range().onField(key).above(value).createQuery();
            case GREATER_THAN:
                try {
                    return new QueryParser(Version.LUCENE_35, key, new StandardAnalyzer(Version.LUCENE_35)).parse(key+":["+value+" null]");
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            case LESS_EQUALS:
                return queryBuilder.range().onField(key).below(value).createQuery();
            case LESS_THAN:
                return queryBuilder.range().onField(key).below(value).excludeLimit().createQuery();
            case PRESENT:
                criteria.add(Restrictions.isNotNull(key));
                return queryBuilder.keyword().wildcard().onField(key).matching("*").createQuery();
            case EMPTY:
                return queryBuilder.all().createQuery();
            default:
                throw new IllegalArgumentException("Unknown constraint.");
        }
    }


    public enum Constraints {
        EQUALS("eq"),
        CONTAINS("co"),
        STARTS_WITH("sw"),
        PRESENT("pr"),
        GREATER_THAN("gt"),
        GREATER_EQUALS("ge"),
        LESS_THAN("lt"),
        LESS_EQUALS("le"),
        EMPTY("empty");
        static Map<String, Constraints> fromString = new ConcurrentHashMap<>();

        static {
            for (Constraints k : values()) { fromString.put(k.constraint, k); }
        }

        private final String constraint;


        Constraints(String constraint) {
            this.constraint = constraint;
        }

        static String createOrConstraints() {
            StringBuilder sb = new StringBuilder();
            for (Constraints k : values()) {
                if (sb.length() != 0) { sb.append("|");}
                sb.append(k.constraint);
            }
            return sb.toString();

        }


    }
}
