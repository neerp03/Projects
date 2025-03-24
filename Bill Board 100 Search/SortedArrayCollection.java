

import java.util.Iterator;
import java.util.Comparator;


public class SortedArrayCollection<T> implements CollectionInterface<T>, Iterable<T> {
	protected final int DEFCAP = 100; // default capacity
	protected int origCap; // original capacity
	protected T[] elements; // array to hold collection elements
	protected int numElements = 0; // number of elements in this collection

	// set by find method
	protected boolean found; // true if target found, otherwise false
	protected int location; // indicates location of target if found,
							// indicates add index if not found

	protected Comparator<T> comp; // Sets order

	/*
	 * Default constructor using natural order comparator
	 */
	public SortedArrayCollection() {
		elements = (T[]) new Object[DEFCAP];
		origCap = DEFCAP;
		comp = new Comparator<T>() {
			public int compare(T element1, T element2) {
				return ((Comparable) element1).compareTo(element2);
			}
		};
	}

	/*
	 * Construct collection of specific size using natural order comparator
	 */
	public SortedArrayCollection(int capacity) {
		elements = (T[]) new Object[capacity];
		this.origCap = capacity;
		comp = new Comparator<T>() {
			public int compare(T element1, T element2) {
				return ((Comparable) element1).compareTo(element2);
			}
		};
	}

	/*
	 * Default capacity constructor using user defined comparator
	 */
	public SortedArrayCollection(Comparator<T> comp) {
		elements = (T[]) new Object[DEFCAP];
		origCap = DEFCAP;
		this.comp = comp;
	}

	/*
	 * User defined capacity collection using user defined comparator
	 */
	public SortedArrayCollection(int capacity, Comparator<T> comp) {
		elements = (T[]) new Object[capacity];
		this.origCap = capacity;
		this.comp = comp;
	}

	protected void enlarge() {
		// Increments the capacity of the collection by an amount
		// equal to the original capacity.
		// Create the larger array.
		T[] larger = (T[]) new Object[elements.length + origCap];

		// Copy the contents from the smaller array into the larger array.
		for (int i = 0; i < numElements; i++) {
			larger[i] = elements[i];
		}

		// Reassign elements reference.
		elements = larger;
	}

	protected void find(T target) {
		// Searches elements for an occurrence of an element e such that
		// e.equals(target). If successful, sets instance variables
		// found to true and location to the array index of e. If
		// not successful, sets found to false and location to the
		// array index where target should be inserted.
		location = 0;
		found = false;
		if (!isEmpty())
			recFind(target, 0, numElements - 1);
	}

	protected void recFind(T target, int first, int last) {
		// Used by find.
		int result; // result of the comparison
		if (first > last) {
			found = false;
			result = comp.compare(target, elements[location]);
			if (result > 0)
				location++; // adjust location to indicate insert index
		} else {
			location = (first + last) / 2;
			result = comp.compare(target, elements[location]);
			if (result == 0) // found target
				found = true;
			else if (result > 0) // target too high
				recFind(target, location + 1, last);
			else // target too low
				recFind(target, first, location - 1);
		}
	}

	public boolean add(T element) {
		// Precondition: element is Comparable to previously added objects
		//
		// Adds element to this collection.
		if (numElements == elements.length)
			enlarge();

		find(element); // sets location to index where element belongs

		for (int index = numElements; index > location; index--)
			elements[index] = elements[index - 1];

		elements[location] = element;
		numElements++;
		return true;
	}

	public boolean remove(T target) {
		// Removes an element e from this collection such that e.equals(target)
		// and returns true; if no such element exists, returns false.
		find(target);
		if (found) {
			for (int i = location; i <= numElements - 2; i++)
				elements[i] = elements[i + 1];
			elements[numElements - 1] = null;
			numElements--;
		}
		return found;
	}

	public int size() {
		// Returns the number of elements on this collection.
		return numElements;
	}

	public boolean contains(T target) {
		// Returns true if this collection contains an element e such that
		// e.equals(target); otherwise, returns false.
		find(target);
		return found;
	}

	public T get(T target) {
		// Returns an element e from this collection such that e.equals(target);
		// if no such element exists, returns null.
		find(target);
		if (found)
			return elements[location];
		else
			return null;
	}

	public boolean isEmpty() {
		// Returns true if this collection is empty; otherwise, returns false.
		return (numElements == 0);
	}

	public boolean isFull() {
		// This collection is unbounded so always returns false.
		return false;
	}

	public Iterator<T> iterator()
	// Returns an Iterator over this list.
	{
		return new Iterator<T>() {
			private int previousPos = -1;

			public boolean hasNext()
			// Returns true if the iteration has more elements; otherwise returns false.
			{
				return (previousPos < (size() - 1));
			}

			public T next()
			// Returns the next element in the iteration.
			// Throws NoSuchElementException - if the iteration has no more elements
			{
				if (!hasNext())
					throw new IndexOutOfBoundsException(
							"illegal invocation of next " + " in SortedArrayCollection iterator.\n");
				previousPos++;
				return elements[previousPos];
			}

			public void remove()
			// Throws UnsupportedOperationException.
			{
				throw new UnsupportedOperationException("Unsupported index-based remove attempted on sorted list.");
			}
		};
	}

}