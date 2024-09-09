import java.util.NoSuchElementException;

/**
 * Implementation of a non-circular SinglyLinkedList with a tail pointer.
 */
public class SinglyLinkedList<T> {

    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the specified index.
     *     
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext(); // iteration step
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *     
     * @param data the data to add to the front of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        if (size == 0) {
            tail = newNode;
        }
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *     
     * @param data the data to add to the back of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            addToFront(data);
        } else {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            SinglyLinkedListNode<T> lastNode = getTail();
            lastNode.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            T removedNode = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            return removedNode;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *     
     * @return the data formerly located at the front of the list
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        if (size == 1) {
            tail = null;
        }
        SinglyLinkedListNode<T> removedNode = head;
        head = head.getNext();
        size--;
        return removedNode.getData();
    }

    /**
     * Removes and returns the last data of the list.
     *
     * @return the data formerly located at the back of the list
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        if (size == 1) {
            return removeFromFront();
        }
        SinglyLinkedListNode<T> removedNode = tail;
        SinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size - 2; i++) {
            current = current.getNext();
        }
        tail = current;
        current.setNext(null);
        size--;
        return removedNode.getData();
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        if (index == 0) {
            return head.getData();
        }
        if (index == size - 1) {
            return tail.getData();
        }
        SinglyLinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws IllegalArgumentException if data is null
     * @throws NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        SinglyLinkedListNode<T> current = head;
        SinglyLinkedListNode<T> prev = null;
        SinglyLinkedListNode<T> lastOccurrence = null;
        SinglyLinkedListNode<T> beforeLast = null;

        while (current != null) {
            if (current.getData().equals(data)) {
                lastOccurrence = current;
                beforeLast = prev;
            }
            prev = current;
            current = current.getNext();
        }
        if (lastOccurrence == null) {
            throw new NoSuchElementException("Data not found.");
        }
        if (beforeLast == null) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
        } else {
            beforeLast.setNext(lastOccurrence.getNext());
            if (lastOccurrence == tail) {
                tail = beforeLast;
            }
        }
        size--;
        return lastOccurrence.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *     
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        int index = 0;
        SinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
