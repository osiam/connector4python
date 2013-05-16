package org.osiam.ng.resourceserver.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 5/16/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SCIMSearchResult<T> {
    private final List<T> result;
    private final long totalResult;

    public SCIMSearchResult(List<T> list, long totalResult) {
        this.result = list;
        this.totalResult = totalResult;

    }

    public List<T> getResult() {
        return result;
    }

    public long getTotalResult() {
        return totalResult;
    }
}
