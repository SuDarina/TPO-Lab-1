package second;

public class Main {
    public static void main(String[] args) {
        BTree first = new BTree(3);
        first.insert(1);
        first.insert(3);
        first.insert(7);
        first.insert(10);
        first.insert(11);
        first.insert(4);
        first.insert(5);
        first.insert(2);
        first.insert(12);
        first.insert(6);
        System.out.println(first.traverse().toString());

    }
}
