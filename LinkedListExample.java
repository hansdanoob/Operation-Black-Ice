import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListExample implements ListIterator{
    
    Node head = null; // bottom right
    Node tail = null; // top left
    int size = 0;


    Node previousNode;
    Node nextNode;
    int index;
    Node lastCall;


    public void add(String element) { //Inserts the specified element at the end of the list.
        
        Node newNode = new Node(element, tail, null);

        if (tail != null) {
            tail.setEast(newNode);
        }

        tail = newNode;
        if (head == null) {
            head = newNode;
        }

        size++;
    }


    public void add(int xIndex, int yIndex, RoomInstance room) { //Inserts the specified element at the specified position in the list

        Node current = head;
        if (current == null || xIndex == xSize) {
            //no items in list or appending new node
            add(room);
        } else {
            //get four nodes to be inserted between
            for (int i = 0; i < xIndex; i++) {
                current = current.getEast();
            }
            Node previousRoom = current.getWest();
            //create + insert
            Node newNode = new Node(room, previousRoom, current);
                
            //change links of surrounding nodes
            if (previousNode != null) {
                previousNode.setEast(newNode);
            }
            if (current != null) {
                current.setWest(newNode);
            }

            //change head if needed (tail doesnt need to be changed as would require IndexOutOfBounds)
            if (newNode.getWest() == null) {
                head = newNode;
            }

            size++;
            
        }
    }


    public String toString() { //Return a text representation of the linked list in the form [element1, element2, element2]
        if (size <= 0) {
            return "[]";
        } else {
            String string = "[";
            Node current = head;
            for (int i = 0; i < size; i++) {
                string += current.getValue();
                current = current.getEast();
                if (i+1 < size) {
                    string += ", ";
                }
            }
            string += "]";
            return string;
        }
    }


    public int size() { //Returns the number of elements in this list
        return size;
    }


    public void clear() { //Removes all of the elements from the list
        int originalSize = size;
        for (int i = 0; i < originalSize; i++) {
            remove(0);
        }
    }


    public String get(int index) { //Returns the element at the specified position in the list. Return empty string if the index is "out of bounds".
        if (index < 0 || index > size) {
            return "";
        } else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.getEast();
            }
            return current.getValue();
        }
    }


    public void set(int index, String element) { //Sets the element at the specified position in the list to the given value. If the index is "out of bounds", no action is required.
        if (index >= 0 && index < size) {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.getEast();
            }
            current.setValue(element);
        }
    }


    public void remove(int index) { //Removes the element at the specified position in the list. If the index is "out of bounds", no action is required.
        if (index >= 0 && index < size) {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.getEast();
            }

            Node previousNode = current.getWest();
            Node nextNode = current.getEast();

            if (head == current) {
                head = nextNode;
            }
            if (tail == current) {
                tail = previousNode;
            }

            if (previousNode!= null) {
                previousNode.setEast(nextNode);
            }
            if (nextNode != null) {
                nextNode.setWest(previousNode);
            }

            current = null;

            size--;
        }
    }



    public ListIterator listIterator() {
        nextNode = head;
        previousNode = null;
        index = 0;
        return this;
    }

    public boolean hasNext() {
        if (nextNode != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasPrevious() {
        if (previousNode != null) {
            return true;
        } else {
            return false;
        }
    }

    public Object next() {

        if (nextNode == null) {
            throw new NoSuchElementException();
        }

        lastCall = nextNode;

        String returnValue = nextNode.getValue();

        previousNode = nextNode;
        nextNode = nextNode.getEast();

        index++;

        return returnValue;
    }

    public Object previous() {

        if (previousNode == null) {
            throw new NoSuchElementException();
        }

        lastCall = previousNode;

        String returnValue = previousNode.getValue();

        nextNode = previousNode;
        previousNode = previousNode.getWest();

        index--;

        return returnValue;
    }

    public int nextIndex() {
        return index;
    }

    public int previousIndex() {
        return index-1;
    }

    public void remove() {

        if (lastCall == null) {
            throw new IllegalStateException();
        }

        Node newPreviousNode = lastCall.getWest();
        Node newNextNode = lastCall.getEast();

        newPreviousNode.setEast(newNextNode);
        newNextNode.setWest(newPreviousNode);

        lastCall = null;
        size--;
        index--;
    }

    public void set(Object e) {
        if (lastCall == null) {
            throw new IllegalStateException();
        }
        lastCall.setValue((String) e);
        
    }

    public void add(Object e) {

        if (lastCall == null) {
            throw new IllegalStateException();
        }

        Node newNode = new Node((String) e, previousNode, nextNode);
        previousNode.setEast(newNode);
        nextNode.setWest(newNode);

        previousNode = newNode;

        lastCall = null;

        size++;
        index++;
    }
}
