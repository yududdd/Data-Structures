/**
 * Simple Stack Interface based on array
 * @author Yu Du
 *
 */
public interface StackInterface<AnyType> {
	/**
	 * O(1)
	 * @param item Any type element to be inserted
	 * @return false if the stack is full else true
	 */
	boolean push(AnyType item);
	
	/**
	 * O(1)
	 * Removes the element at the top of the stack 
	 * @return the reference of the element to be removed.
	 */
	AnyType pop();
	
	/**
	 * O(1)
	 * @return Returns a reference to the element at the top of the stack.
	 */
	AnyType peek();
	
	/**
	 * O(1)
	 * @return  Returns true if the stack is empty and false otherwise. 
	 */
	boolean isEmpty();
}
