import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] RQ;
    private int current;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        RQ = (Item[]) new Object[10];
        current = 0;
        size = 0;
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
        if (current == RQ.length) resize(2 * size);
        RQ[current++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        StdRandom.shuffle(RQ, 0, current);
        Item item = RQ[--current];
        RQ[current] = null;
        if (size > 0 && size == RQ.length / 4) resize(RQ.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return RQ[StdRandom.uniformInt(current)];
    }

    private class RQIterator implements Iterator<Item> {
        private int x;

        public RQIterator() {
            StdRandom.shuffle(RQ, 0, current);
        }

        public boolean hasNext() {
            return x != current;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove should not be used");
        }

        public Item next() {
            if (x == current) throw new NoSuchElementException("No more next");
            return RQ[x++];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 20; i++) {
            rq.enqueue(i);
        }
        for (int a : rq) {
            for (int b : rq) {
                System.out.print(a + "-" + b + " ");
            }
        }
        for (int i = 0; i < 20; i++) {
            System.out.print(rq.dequeue());
        }
    }
}