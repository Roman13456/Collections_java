import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class representing a node in the doubly linked list
 */
class Node {
    Flower data;
    Node next;
    Node previous;

    Node(Flower data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }
}

/**
 * Custom list implementation for storing flowers using doubly linked list
 */
class FlowerList implements Iterable<Flower> {
    private Node head;
    private Node tail;
    private int size;

    public FlowerList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Add a flower to the end of the list
     */
    public void add(Flower flower) {
        Node newNode = new Node(flower);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Get flower by index
     */
    public Flower get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Remove flower by index
     */
    public Flower remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current == head) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
        } else if (current == tail) {
            tail = tail.previous;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }

        size--;
        return current.data;
    }

    /**
     * Return the number of flowers in the list
     */
    public int size() {
        return size;
    }

    /**
     * Sort flowers by freshness
     */
    public void sortByFreshness() {
        if (size <= 1) {
            return;
        }

        for (Node i = head; i != null; i = i.next) {
            for (Node j = i.next; j != null; j = j.next) {
                if (i.data.getFreshness() > j.data.getFreshness()) {
                    Flower temp = i.data;
                    i.data = j.data;
                    j.data = temp;
                }
            }
        }
    }

    /**
     * Find flowers by stem length range
     */
    public FlowerList findFlowersByStemLength(double minLength, double maxLength) {
        FlowerList result = new FlowerList();
        for (Node current = head; current != null; current = current.next) {
            if (current.data.getStemLength() >= minLength && current.data.getStemLength() <= maxLength) {
                result.add(current.data);
            }
        }
        return result;
    }

    /**
     * Iterator for FlowerList
     */
    @Override
    public Iterator<Flower> iterator() {
        return new Iterator<Flower>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Flower next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Flower data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Flower flower : this) {
            sb.append(flower).append("\n");
        }
        return sb.toString();
    }
}

/**
 * General class for flowers
 */
class Flower {
    private String name;
    private double price;
    private int freshness; // from 1 (fresh) to 10 (not fresh)
    private double stemLength;

    public Flower(String name, double price, int freshness, double stemLength) {
        if (price <= 0 || freshness < 1 || freshness > 10 || stemLength <= 0) {
            throw new IllegalArgumentException("Invalid flower parameters.");
        }
        this.name = name;
        this.price = price;
        this.freshness = freshness;
        this.stemLength = stemLength;
    }

    public double getPrice() {
        return price;
    }

    public int getFreshness() {
        return freshness;
    }

    public double getStemLength() {
        return stemLength;
    }

    @Override
    public String toString() {
        return name + " (Freshness: " + freshness + ", Stem Length: " + stemLength + " cm, Price: $" + price + ")";
    }
}

/**
 * Subclasses for different types of flowers
 */
class Rose extends Flower {
    public Rose(double price, int freshness, double stemLength) {
        super("Rose", price, freshness, stemLength);
    }
}

class Tulip extends Flower {
    public Tulip(double price, int freshness, double stemLength) {
        super("Tulip", price, freshness, stemLength);
    }
}

class Lily extends Flower {
    public Lily(double price, int freshness, double stemLength) {
        super("Lily", price, freshness, stemLength);
    }
}

/**
 * General class for accessories
 */
class Accessory {
    private String type;
    private double price;

    public Accessory(String type, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Accessory price can't be negative.");
        }
        this.type = type;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return type + " (Price: $" + price + ")";
    }
}

class Ribbon extends Accessory {
    public Ribbon(double price) {
        super("Ribbon", price);
    }
}

class Wrapper extends Accessory {
    public Wrapper(double price) {
        super("Wrapper", price);
    }
}

class Card extends Accessory {
    public Card(double price) {
        super("Card", price);
    }
}

/**
 * Class representing a bouquet
 */
class Bouquet {
    private FlowerList flowers;
    private Accessory[] accessories;

    public Bouquet(FlowerList flowers, Accessory[] accessories) {
        this.flowers = flowers;
        this.accessories = accessories;
    }

    /**
     * Calculate the total cost of the bouquet
     */
    public double calculateTotalCost() {
        double totalCost = 0;
        for (Flower flower : flowers) {
            totalCost += flower.getPrice();
        }
        for (Accessory accessory : accessories) {
            totalCost += accessory.getPrice();
        }
        return totalCost;
    }

    /**
     * Sort flowers by freshness
     */
    public void sortByFreshness() {
        flowers.sortByFreshness();
    }

    /**
     * Find flowers within a given stem length range
     */
    public FlowerList findFlowersByStemLength(double minLength, double maxLength) {
        return flowers.findFlowersByStemLength(minLength, maxLength);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Flowers:\n").append(flowers).append("Accessories:\n");
        for (Accessory accessory : accessories) {
            sb.append(accessory).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Main class to demonstrate bouquet creation and management
 */
public class FlowerShop {
    public static void main(String[] args) {
        try {
            // Create a custom flower list
            FlowerList flowers = new FlowerList();
            flowers.add(new Rose(10.5, 2, 50));
            flowers.add(new Tulip(7.0, 5, 30));
            flowers.add(new Lily(12.0, 3, 40));
            
            // Uncomment the line below to see the error when trying to access a flower at an invalid index (index 3 doesn't exist in the list).
            // Flower flower404 = flowers.get(3); // This will throw an IndexOutOfBoundsException as there are only 3 flowers (0, 1, 2).
            
            // Uncomment the line below to see the error when trying to remove a flower at an invalid index (index 3 doesn't exist in the list).
            // flowers.remove(3); // This will throw an IndexOutOfBoundsException because the list has only 3 flowers (index 0, 1, 2).

            // Create accessories
            Accessory[] accessories = {
                new Ribbon(2.0),
                new Wrapper(3.0),
                new Card(1.5)
            };

            // Create bouquet with flowers and accessories
            Bouquet bouquet = new Bouquet(flowers, accessories);

            // Display bouquet before sorting
            System.out.println("Bouquet before sorting by freshness:");
            System.out.println(bouquet);

            // Sort flowers by freshness
            bouquet.sortByFreshness();
            System.out.println("Bouquet after sorting by freshness:");
            System.out.println(bouquet);

            // Find flowers within a stem length range
            double minLength = 35;
            double maxLength = 55;
            FlowerList foundFlowers = bouquet.findFlowersByStemLength(minLength, maxLength);
            if (foundFlowers.size() > 0) {
                System.out.println("Flowers found within stem length range (" + minLength + " - " + maxLength + " cm):");
                System.out.println(foundFlowers);
            } else {
                System.out.println("No flowers found within stem length range (" + minLength + " - " + maxLength + " cm).");
            }

            // Calculate total cost of the bouquet
            double totalCost = bouquet.calculateTotalCost();
            System.out.println("Total cost of the bouquet: $" + totalCost);
            
             // Test get() method to retrieve a specific flower by index
            System.out.println("\nTest: Getting the flower at index 1:");
            Flower flowerAtIndex1 = flowers.get(1); // should be Lily (as sorted by freshness)
            System.out.println("Flower at index 1: " + flowerAtIndex1);

            // Test remove() method to remove a flower by index
            System.out.println("\nTest: Removing the flower at index 0 (first flower in list):");
            Flower removedFlower = flowers.remove(0); // should remove the Rose
            System.out.println("Removed flower: " + removedFlower);

            System.out.println("\nTest: Getting the flower at index 0:");
            flowerAtIndex1 = flowers.get(0); // should be Lily (now that Rose has been removed)
            System.out.println("Flower at index 0: " + flowerAtIndex1);
            
            // Display the updated bouquet after removing a flower
            System.out.println("\nBouquet after removing the first flower:");
            System.out.println(bouquet);
            

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}