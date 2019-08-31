import java.util.NoSuchElementException;
/**
 * An array-based implementation of stack
 * @author Yu Du
 *
 */
public class MyStack<AnyType> implements StackInterface<AnyType>{
	/**
	 * Capacity of the stack
	 */
	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * top pointer of this stack
	 */
	private int top;

	/**
	 * the element the stack should contain
	 */
	private AnyType[] elements;

	/**
	 * Constructor to create the stack 
	 * @param initialCapacity the user indicated capacity of the array
	 */
	@SuppressWarnings("unchecked")
	public MyStack(int initialCapacity) {
		if (initialCapacity < 0) {
			elements = (AnyType[]) new Object[DEFAULT_CAPACITY];
		} else {
			elements = (AnyType[]) new Object[initialCapacity];
		}

		// set the top to be -1, indicate the stack is empty
		top = -1;
	}

	public MyStack() {
		this(DEFAULT_CAPACITY);
	}

	@Override
	public boolean push(AnyType item) {
		//check if the stack is full
		if (top == elements.length - 1) {
			return false;
		}
		elements[top] = item;
		top++;
		return true;
	}

	@Override
	public AnyType pop() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		// get the element on the top
		AnyType item = (AnyType) elements[top];
		elements[top] = null;
		top--;
		return item;
	}

	@Override
	public AnyType peek() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return elements[top - 1];
	}

	@Override
	public boolean isEmpty() {
		return top == -1;
	}

}
