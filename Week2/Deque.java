/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        }
        else {
            last = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        }
        else {
            first = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        System.out.println("Is the deque empty? " + deque.isEmpty());

        System.out.println("Size of the deque: " + deque.size());

        deque.addFirst(1);
        System.out.println("Added 1 to the front. Size: " + deque.size());

        deque.addLast(2);
        System.out.println("Added 2 to the back. Size: " + deque.size());

        Integer firstItem = deque.removeFirst();
        System.out.println("Removed from front: " + firstItem + ". Size: " + deque.size());

        Integer lastItem = deque.removeLast();
        System.out.println("Removed from back: " + lastItem + ". Size: " + deque.size());

        deque.addFirst(3);
        deque.addLast(4);
        System.out.println("Added 3 to the front and 4 to the back. Size: " + deque.size());
        System.out.print("Items in deque: ");
        for (int item : deque) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
