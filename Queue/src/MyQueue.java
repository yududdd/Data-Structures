/**
 * Array-based implementation of queue.
 * @author Yu Du
 * @param <AnyType>
 */
public class MyQueue<AnyType> implements QueueInterface<AnyType> {
    /**
     * Array to store as queue
     */
    private AnyType[] array;
    /**
     * front of queue.
     */
    private int front;
    /**
     * back of queue.
     */
    private int back;
    /**
     * size of this queue.
     */
    private int size;
    /**
     * default capacity of this queue
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * constructor of this queue
     */
    @SuppressWarnings("unchecked")
	public MyQueue() {
        this.front = 0;
        this.back = -1;
        this.size = 0;
        this.array = (AnyType[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Inserts a new item to the back of the queue
     * @param item element waited to be inserted
     */
    @Override
    public void enqueue(AnyType item) {
        if (size == array.length) {
            throw new RuntimeException("queue is full!");
        }
        back = (back + 1) % array.length;
        array[back] = item;
        size++;
    }

    /**
     * pop element from front of this queue. aka dequeue, use front pointer.
     * @return front element of this queue
     */
    @Override
    public AnyType dequeue() {
        if (size == 0) {
            throw new RuntimeException("queue is empty!");
        }
        @SuppressWarnings("unchecked")
        AnyType result = (AnyType) array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return result;
    }

    /**
     * peek element from front of this queue.
     * @return front element of this queue
     */
    @Override
    public AnyType peekFront() {
        if (size == 0) {
            throw new RuntimeException("queue is empty!");
        }
        @SuppressWarnings("unchecked")
        AnyType result = (AnyType) array[front];
        return result;
    }

    /**
     * Test if this queue is empty or not.
     * @return true if this queue is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}