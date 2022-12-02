package org.velikokhatko.part2.blocking.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractBlockingQueue<R> implements BlockingQueue<R> {
    @Override
    public boolean add(R r) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(R r) {
        throw new UnsupportedOperationException();
    }

    @Override
    public R remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public R poll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public R element() {
        throw new UnsupportedOperationException();
    }

    @Override
    public R peek() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(R r) throws InterruptedException {

    }

    @Override
    public boolean offer(R r, long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public R take() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public R poll(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int remainingCapacity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends R> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<R> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int drainTo(Collection<? super R> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int drainTo(Collection<? super R> c, int maxElements) {
        throw new UnsupportedOperationException();
    }
}
