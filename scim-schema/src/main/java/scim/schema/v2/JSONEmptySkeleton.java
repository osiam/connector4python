/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package scim.schema.v2;

import com.sun.istack.internal.NotNull;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings("unchecked")
/**
 * This class has the purpose to enable Jackson to ignore empty User.Collections ...
 */
public abstract class JSONEmptySkeleton<T extends Collection> implements Collection<T> {

    @NotNull
    @JsonIgnore
    protected final T collection;

    JSONEmptySkeleton(T collection) {
        this.collection = collection;

    }

    @Override

    @JsonIgnore
    public int size() {
        return collection.size();

    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    @JsonIgnore
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    @JsonIgnore
    public Iterator<T> iterator() {

        Iterator iterator = collection.iterator();
        return iterator;
    }

    @Override
    @JsonIgnore
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    @JsonIgnore
    public <T1 extends Object> T1[] toArray(T1[] t1s) {
        return (T1[]) collection.toArray(t1s);
    }

    @Override
    @JsonIgnore
    public boolean add(T t) {
        return collection.add(t);
    }

    @Override
    @JsonIgnore
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    @Override
    @JsonIgnore
    public boolean containsAll(Collection<?> objects) {
        return collection.containsAll(objects);
    }

    @Override
    @JsonIgnore
    public boolean addAll(Collection<? extends T> ts) {
        return collection.addAll(ts);
    }

    @Override
    @JsonIgnore
    public boolean removeAll(Collection<?> objects) {
        return collection.removeAll(objects);
    }

    @Override
    @JsonIgnore
    public boolean retainAll(Collection<?> objects) {
        return collection.retainAll(objects);
    }

    @Override
    @JsonIgnore
    public void clear() {
        collection.clear();
    }
}
