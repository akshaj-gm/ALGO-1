/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(N);
        Item item = s[randomIndex];
        s[randomIndex] = s[--N];
        s[N] = null; // Avoid loitering
        if (N > 0 && N == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    // resize the underlying array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(N);
        return s[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] copy;
        private int current;

        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                copy[i] = s[i];
            }
            StdRandom.shuffle(copy);
            current = 0;
        }

        public boolean hasNext() {
            return current < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy[current++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println("Is queue empty? " + rq.isEmpty()); // Expected: true
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        System.out.println("Size of queue: " + rq.size()); // Expected: 3
        System.out.println("Sample item: " + rq.sample()); // Sample one item
        System.out.println("Dequeued item: " + rq.dequeue()); // Remove and print a random item
        System.out.println("Size of queue after dequeue: " + rq.size()); // Expected: 2
        for (int item : rq) {
            System.out.print(item + " "); // Iterate through the queue
        }
        System.out.println();
    }
}


