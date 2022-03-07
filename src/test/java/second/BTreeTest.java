package second;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BTreeTest {

    private BTree first;
    private final int[] arr1 = { 1, 7, 19, 24, 12, 6, 46, 5, 76, 13, 11 };
    private final int[] arr2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 5, 5 };
    private final int[] arr3 = { 1, 2, 3, 4, 5, 6, 7, 2, 2, 3 };


    @BeforeEach
    public void setUp() {
        first = new BTree(3);
    }

    private void bigInsert(int size){
        for(int i = 0; i < size; i++){
            first.insert(i);
        }
    }

    private void insert(int[] arr){
        for(int i : arr) {
            first.insert(i);
        }
    }

    @Test
    public void insertRemoveAndSearch() {
        first.insert(1);
        assertEquals(first.search(1).keys[0],1);
        first.insert(3);
        first.insert(7);
        first.insert(10);
        first.insert(11);
        first.insert(4);
        first.insert(5);
        first.insert(2);
        first.insert(12);
        first.insert(6);
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 10, 11, 12]", first.traverse().toString());
        first.remove(3);
        assertEquals("[1, 2, 4, 5, 6, 7, 10, 11, 12]", first.traverse().toString());
        first.remove(4);
        assertEquals("[1, 2, 5, 6, 7, 10, 11, 12]", first.traverse().toString());
        first.remove(4);
        assertEquals("[1, 2, 5, 6, 7, 10, 11, 12]", first.traverse().toString());
        first.insert(3);
        assertEquals("[1, 2, 3, 5, 6, 7, 10, 11, 12]", first.traverse().toString());
        first.remove(1);
        assertEquals("[2, 3, 5, 6, 7, 10, 11, 12]", first.traverse().toString());
        first.search(7);
        assertArrayEquals(new Integer[]{7}, first.result.toArray(new Integer[0]));
        first.search(11);
        assertArrayEquals(new int[]{7, 10, 11}, first.result.stream().mapToInt(i -> i).toArray());
        first.search(10);
        assertArrayEquals(new int[]{7, 10}, first.result.stream().mapToInt(i -> i).toArray());
    }

    @Test
    void removeAndSearchWhenNodeEquals() {
        first.insert(1);
        first.insert(1);
        first.insert(1);
        first.insert(1);
        first.insert(1);
        assertEquals("[1, 1, 1, 1, 1]", first.traverse().toString());
        first.remove(1);
        assertEquals("[1, 1, 1, 1]", first.traverse().toString());
        first.search(1);
        assertArrayEquals(new int[]{1}, first.result.stream().mapToInt(i -> i).toArray());
    }

    @Test
    void insertTest(){
        insert(arr1);
        assertEquals("[1, 5, 6, 7, 11, 12, 13, 19, 24, 46, 76]", first.traverse().toString());
    }

    @Test
    void searchIfBTreeContainsKey(){
        insert(arr1);
        first.search(7);
        assertArrayEquals(new int[]{1, 5, 6, 7}, first.result.stream().mapToInt(i -> i).toArray());
    }

    @Test
    void searchIfLeaf(){
        insert(arr1);
        first.search(76);
        System.out.println(Arrays.toString(first.result.stream().mapToInt(i -> i).toArray()));
        assertArrayEquals(new int[]{12, 13, 19, 24, 46, 76}, first.result.stream().mapToInt(i -> i).toArray());
    }

    @Test
    void searchIfNotLeaf(){
        insert(arr1);
        assertNull(first.search(100));
    }

    @Test
    void removeIfBTreeIsEmpty(){
        first.remove(12);
        assertEquals(0, first.remove(12));
    }

    @Test
    void removeBTreeRoot(){
        insert(arr1);
        first.remove(1);
        assertEquals("[5, 6, 7, 11, 12, 13, 19, 24, 46, 76]", first.traverse().toString());
    }

    @Test
    void removeLastElement(){
        first.insert(1);
        first.remove(1);
        assertEquals("[]", first.traverse().toString());
    }

    @Test
    void checkRootNumThanInsertManyElements(){
        bigInsert(1000000);
        assertEquals(4, first.root.num);
    }

    @Test
    void checkFillNextWhileRemoving(){
        bigInsert(6);
        first.remove(1);
        assertEquals("[0, 2, 3, 4, 5]", first.root.traverse().toString());
    }

    @Test
    void checkFillPredWhileRemoving(){
        insert(arr2);
        first.remove(9);
        first.remove(8);
        assertEquals("[6, 7]", first.root.children[2].traverse().toString());
    }

    @Test
    void checkMergeWhileRemoving(){
        insert(arr2);
        first.remove(9);
        first.remove(8);
        first.remove(7);
        first.remove(6);
        first.remove(5);
        assertEquals("[4, 5, 5]", first.root.children[1].traverse().toString());
    }

    @Test
    void removeFromNonLeaf(){
        bigInsert(22);
        first.insert(1);
        first.insert(1);
        first.remove(2);
        assertEquals("[15, 16]", first.root.children[1].children[1].traverse().toString());
    }
}
