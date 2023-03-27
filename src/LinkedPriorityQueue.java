//JACOB LEBLANC - 202101683

import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedPriorityQueue<T> implements PriorityQueue<T> {

    private PriorityNode front;
    private int size;

    public LinkedPriorityQueue() {
        front = null;
        size = 0;
    }

    @Override
    public void enqueue(T element, int priority) {
        // make a new Priority Node using the inputted data and priority
        PriorityNode newNode = new PriorityNode(element, priority, null);

        // if the Queue is empty
        if (isEmpty()) {
            // make the new Node the first element to join the Queue
            front = newNode;
            // if there is already a front to the Queue, check if the new Node's priority is lower
        } else if (newNode.getPriority() < front.getPriority()) {
            // set the new Node's next to the old front
            newNode.setNext(front);
            // the new Node is now the new front
            front = newNode;
            // otherwise, go down the Queue to see where the new Node fits
        } else {
            // set a current Node and a Next Node
            PriorityNode current = front;
            PriorityNode nextNode = current.getNext();
            // while there is a Next Node and the new Node's priority is still higher
            while (nextNode != null && newNode.getPriority() > nextNode.getPriority()) {
                // keep going through the Queue
                current = nextNode;
                nextNode = current.getNext();
            }
            // once done insert the new Node
            current.setNext(newNode);
            newNode.setNext(nextNode);
        }
        size++;
    }

    @Override
    public void enqueue(T element) {
        PriorityNode newNode = new PriorityNode(element, 1000, null);

        if (isEmpty()) {
            front = newNode;
        } else {
            PriorityNode current = front;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;

    }

    @Override
    public T dequeue() {
        // if the Queue is empty
        if (isEmpty()) {
            // throw this excpetion
            throw new NoSuchElementException("The queue is already empty. ");
        }
        // otherwise, get the front of the Queue
        PriorityNode toDequeue = front;
        // make the front equal the next node
        front = front.getNext();
        // lower the size of the Queue
        size--;
        // return the Node we just removed
        return toDequeue.getData();

    }

    @Override
    public T first() {
        // if the Queue is empty
        if (isEmpty()) {
            // there is no front
            throw new NoSuchElementException("There is no front to an empty queue. ");
        }
        // otherwise, return the first Node in the Queue
        return front.getData();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // if the Queue is empty
        if (isEmpty()) {
            // return this as what an empty Queue's toString looks like
            return sb.append("<-- front").toString();
        } else {
            // otherwise, append the front with the front string
            sb.append(front.getData()).append(" <-- front");
            PriorityNode toPrint = front.getNext();
            while (toPrint != null) {
                // for every Node in the Queue, start a new line then append the element
                sb.append("\n").append(toPrint.getData());
                toPrint = toPrint.getNext();
            }
        }
        return sb.toString();
    }

    /**
     * Two LinkedPriorityQueues are considered equal if all elements/data in the two
     * queues are considered equal, along with their corresponding priorities.
     *
     * @param o That thing to check if this is equal to
     * @return If they be equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedPriorityQueue<?> that = (LinkedPriorityQueue<?>) o;
        if (this.size != that.size) return false;
        LinkedPriorityQueue<?>.PriorityNode thisCurrent = this.front;
        LinkedPriorityQueue<?>.PriorityNode thatCurrent = that.front;
        while (thisCurrent != null) {
            if (thisCurrent.getPriority() != thatCurrent.getPriority()
                    || !thisCurrent.getData().equals(thatCurrent.getData())) return false;
            thisCurrent = thisCurrent.getNext();
            thatCurrent = thatCurrent.getNext();
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(size);
        PriorityNode current = front;
        while (current != null) {
            result = result * 97 + Objects.hash(current.getPriority(), current.getData());
            current = current.getNext();
        }
        return result;
    }

    private class PriorityNode {

        private final T data;
        private final int priority;
        private PriorityNode next;

        public PriorityNode(T data, int priority, PriorityNode next) {
            this.data = data;
            this.priority = priority;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public int getPriority() {
            return priority;
        }

        public PriorityNode getNext() {
            return next;
        }

        public void setNext(PriorityNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "(p:" + String.valueOf(priority) +
                    ", d:" + data.toString() + ")";
        }
    }
}