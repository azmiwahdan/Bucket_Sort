package edu.birzeit.sort;

public class Cursor {

	protected Node[] cursorArray;
	protected static int MAX_SIZE = 300;

	public Cursor() {
		initialization();

	}

	// Initialize the cursor array to null and next index.
	public int initialization() {

		cursorArray = new Node[MAX_SIZE];

		for (int i = 0; i < cursorArray.length - 1; i++)
			cursorArray[i] = new Node(null, i + 1);
		cursorArray[cursorArray.length - 1] = new Node(null, 0);
		return 0;
	}

	// returns the first node after the header (next of the head)
	public int malloc() {
		int p = cursorArray[0].next;
		cursorArray[0].next = cursorArray[p].next;
		return p;
	}

	// return node to free list.
	public void free(int p) {
		cursorArray[p] = new Node(null, cursorArray[0].next);

		cursorArray[0].next = p;

	}

	// return true if the list not created
	public boolean isNull(int l) {
		return cursorArray[l] == null;
	}

	//// return true if the list is empty
	public boolean isEmpty(int l) {
		return cursorArray[l].next == 0;
	}

	public int createList() {
		/* create new empty list */
		int l = malloc();
		if (l == 0)
			System.out.println("Error:out of space");
		else
			cursorArray[l] = new Node("-", 0);// Empty Linked List
		return l; /* Head of the list */
	}

	public void insertAtHead(String data, int l) {
		if (isNull(l)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			cursorArray[p] = new Node(data, cursorArray[l].next);
			cursorArray[l].next = p;
		} else
			System.out.println("Error: Out of space!!!");
	}

	public void insertAtTail(String data, int l) {
		if (isNull(l))
			return;
		int p = malloc();
		if (p != 0) {
			cursorArray[p] = new Node(data, 0);
			if (isEmpty(l)) {
				cursorArray[l].next = p;
			} else {
				int k = cursorArray[l].next;
				while (cursorArray[k].next != 0) {
					k = cursorArray[k].next;
				}
				cursorArray[k].next = p;
			}

		} else {
			System.out.println("Error:out of space!!!");
		}

	}

	public int length(int l) {
		int length = 0;
		while (cursorArray[l].next != 0) {
			length++;
			l = cursorArray[l].next;

		}
		return length;
	}

	public String traversList(int l) {

		String s = "LIST_" + l + ":";
		while (!isNull(l) && !isEmpty(l)) {
			l = cursorArray[l].next;
			s += cursorArray[l].data + "-->";
		}
		s += "NULL";
		return s;
	}

	// remove all element in a specific list
	public void clear(int l) {
		cursorArray[l].next = 0;

	}

	public int findPrevious(String data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (cursorArray[cursorArray[l].next].data.equals(data))
				return l;
			l = cursorArray[l].next;
		}
		return -1; // not found
	}

	public Node delete(String data, int l) {
		int p = findPrevious(data, l);
		if (p != -1) {
			int c = cursorArray[p].next;
			Node temp = cursorArray[c];
			cursorArray[p].next = temp.next;
			free(c);
		}
		return null;
	}

	public int find(String data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = cursorArray[l].next;
			if (cursorArray[l].data.equals(data))
				return l;// return position of data,cyrorArray[position].data=data
		}
		return -1; // not found
	}

	// get max length in list
	public int getMaxIteration(int l) {
		if (isNull(l) || isEmpty(l))
			return -1;

		l = cursorArray[l].next;
		int max = cursorArray[l].data.length();
		while (l != 0) {
			if (cursorArray[l].data.length() > max) {
				max = cursorArray[l].data.length();

			}
			l = cursorArray[l].next;
		}

		return max;
	}

	// Create buckets
	public int[] createBucket() {
		int[] bucket = new int[27];
		for (int i = 0; i < 27; i++) {
			bucket[i] = createList();
		}
		return bucket;
	}

	/**
	 * sort a specific list by bucket sort time complexity=O(n),n=number of
	 * iteration,,hint:n+n+n+constant=O(n)
	 * 
	 * 
	 */

	public int sort(int l,int []buckets) {

		int longest = getMaxIteration(l);// number of iteration
		if (longest == -1) {
			System.out.println("Error:list is Empty!!!");
		} else {
			int iter = longest - 1;
			
			while (iter >= 0) {
				int m = cursorArray[l].next;

				while (m != 0) {

					if (cursorArray[m].data.length() < (iter + 1) || Character.isSpace(cursorArray[m].data.charAt(iter))
							|| Character.compare(cursorArray[m].data.charAt(iter), 'A') < 0) {
						insertAtTail(cursorArray[m].data, buckets[0]);// for a space or string length is less than numb
																		// of tier to insert in space bucket(0).

					} else {

						int b = (Character.toLowerCase(cursorArray[m].data.charAt(iter))) - 96;
						insertAtTail(cursorArray[m].data, buckets[b]);// a-96=1,so like this will put strings in bucket
																		// if there is no space.
					}
					m = cursorArray[m].next;
				}
				clear(l);// delete data form list

				// insert sorted at list

				for (int i = 0; i < buckets.length; i++) {
					int n = cursorArray[buckets[i]].next;
					if (!isEmpty(buckets[i])) {
						while (n != 0) {
							insertAtTail(cursorArray[n].data, l);
							n = cursorArray[n].next;
						}
					}

					clear(buckets[i]);// at the end of iteration will delete strings
				}

				iter--;//
			}
		}
		return l;
	}

}
