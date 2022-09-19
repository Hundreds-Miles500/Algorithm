import java.util.Iterator;
import java.util.NoSuchElementException;

// Linked_list type
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node pre;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item input is null");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) last = first;
        else {
            first.next = oldfirst;
            oldfirst.pre = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item input is null");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) first = last;
        else {
            oldlast.next = last;
            last.pre = oldlast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.pre;
        last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove should not be used");
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException("No more next");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void printallitems(Deque<Item> deque) {
        for (Item item : deque) {
            System.out.println(item);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(2);
        deque.addLast(5);
        deque.addLast(7);
        deque.addLast(10);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.printallitems(deque);
        System.out.println(deque.size());
    }
}
