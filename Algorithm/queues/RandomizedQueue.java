import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] RQ;
    private int current;
    private int size;
    private boolean needRandom;

    // construct an empty randomized queue
    public RandomizedQueue() {
        RQ = (Item[]) new Object[10];
        current = 0;
        size = 0;
        needRandom = false;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // resize the array
    private void resize(int factor) {
        Item[] copy = (Item[]) new Object[factor];
        for (int i = 0; i < size; i++) {
            copy[i] = RQ[i];
        }
        RQ = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item is null");
        if (current == RQ.length) resize(2 * size);
        RQ[current++] = item;
        size++;
        if (!needRandom) needRandom = true;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The Queues is empty");
        if (needRandom) {
            StdRandom.shuffle(RQ, 0, current);
            needRandom = false;
        }
        Item item = RQ[--current];
        size--;
        RQ[current] = null;
        if (size > 0 && size == RQ.length / 4) resize(RQ.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The Queues is empty");
        return RQ[StdRandom.uniformInt(current)];
    }

    private class RQIterator implements Iterator<Item> {
        private int x;
        Item[] RQ2;


        public RQIterator() {
            StdRandom.shuffle(RQ, 0, current);
            RQ2 = Arrays.copyOf(RQ, RQ.length);
        }

        public boolean hasNext() {
            return x != current;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove should not be used");
        }

        public Item next() {
            if (x == current) throw new NoSuchElementException("No more next");
            return RQ2[x++];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        for (int a : rq) {
            System.out.print(a + "-");
        }
        System.out.println();
        for (int b : rq) {
            System.out.print(b + "-");
        }


    }
}