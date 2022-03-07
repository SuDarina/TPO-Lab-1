package second;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
    public List<Integer> result = new ArrayList<>();
    public int[] keys;
    int MinDeg;
    public BTreeNode[] children;
    int num;
    boolean isLeaf;

    public BTreeNode(int deg, boolean isLeaf) {
        this.MinDeg = deg;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * this.MinDeg - 1];
        this.children = new BTreeNode[2 * this.MinDeg];
        this.num = 0;
    }

    public int findKey(int key) {
        int idx = 0;
        while (idx < num && keys[idx] < key)
            ++idx;
        return idx;
    }


    public void remove(int key) {

        int idx = findKey(key);
        if (idx < num && keys[idx] == key) {
            if (isLeaf)
                removeFromLeaf(idx);
            else
                removeFromNonLeaf(idx);
        } else {
            if (isLeaf) {
                System.out.printf("The key %d does not exist in the tree\n", key);
                return;
            }


            boolean flag = idx == num;

            System.out.println("children[idx].num:" + children[idx].num);
            System.out.println("MinDeg: " + MinDeg);
            if (children[idx].num < MinDeg){
                fill(idx);
            }


            if (flag && idx > num)
                children[idx - 1].remove(key);
            else
                children[idx].remove(key);
        }
    }

    public void removeFromLeaf(int idx) {
        if (num - (idx + 1) >= 0)
            System.arraycopy(keys, idx + 1, keys,
                    idx + 1 - 1, num - (idx + 1));
        num--;
    }

    public void removeFromNonLeaf(int idx) {

        int key = keys[idx];
        System.out.println("in rfnl");
        System.out.println("children[idx].num: " + children[idx].num);
        System.out.println("children[idx]: " + children[idx].traverse().toString());
        System.out.println("idx: " + idx);
        if (children[idx].num >= MinDeg) {
            int pred = getPred(idx);
            keys[idx] = pred;
            children[idx].remove(pred);
        }

        else if (children[idx + 1].num >= MinDeg) {
            int succ = getSucc(idx);
            keys[idx] = succ;
            children[idx + 1].remove(succ);
        } else {

            merge(idx);
            children[idx].remove(key);
        }
    }

    public int getPred(int idx) {
        BTreeNode cur = children[idx];
        while (!cur.isLeaf)
            cur = cur.children[cur.num];
        return cur.keys[cur.num - 1];
    }

    public int getSucc(int idx) {
        BTreeNode cur = children[idx + 1];
        while (!cur.isLeaf)
            cur = cur.children[0];
        return cur.keys[0];
    }

    public void fill(int idx) {
        System.out.println("here");
        if (idx != 0 && children[idx - 1].num >= MinDeg)
            borrowFromPrev(idx);
        else if (idx != num && children[idx + 1].num >= MinDeg)
            borrowFromNext(idx);
        else {
            if (idx != num)
                merge(idx);
            else
                merge(idx - 1);
        }
    }

    public void borrowFromPrev(int idx) {
        System.out.println("prev");


        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];

        for (int i = child.num - 1; i >= 0; --i)
            child.keys[i + 1] = child.keys[i];

        if (!child.isLeaf) {
            for (int i = child.num; i >= 0; --i)
                child.children[i + 1] = child.children[i];
        }

        child.keys[0] = keys[idx - 1];
        if (!child.isLeaf)
            child.children[0] = sibling.children[sibling.num];

        keys[idx - 1] = sibling.keys[sibling.num - 1];
        child.num += 1;
        sibling.num -= 1;
    }

    public void borrowFromNext(int idx) {
        System.out.println("next");

        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[child.num] = keys[idx];

        if (!child.isLeaf)
            child.children[child.num + 1] = sibling.children[0];

        keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.num; ++i)
            sibling.keys[i - 1] = sibling.keys[i];

        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.num; ++i)
                sibling.children[i - 1] = sibling.children[i];
        }
        child.num += 1;
        sibling.num -= 1;
    }

    public void merge(int idx) {

        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[MinDeg - 1] = keys[idx];

        for (int i = 0; i < sibling.num; ++i)
            child.keys[i + MinDeg] = sibling.keys[i];

        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.num; ++i)
                child.children[i + MinDeg] = sibling.children[i];
        }

        for (int i = idx + 1; i < num; ++i)
            keys[i - 1] = keys[i];
        for (int i = idx + 2; i <= num; ++i)
            children[i - 1] = children[i];

        child.num += sibling.num + 1;
        num--;
    }


    public void insertNotFull(int key) {

        int i = num - 1;

        if (isLeaf) {
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            num = num + 1;
        } else {
            while (i >= 0 && keys[i] > key)
                i--;
            if (children[i + 1].num == 2 * MinDeg - 1) {
                splitChild(i + 1, children[i + 1]);
                if (keys[i + 1] < key)
                    i++;
            }
            children[i + 1].insertNotFull(key);
        }
    }


    public void splitChild(int i, BTreeNode y) {

        BTreeNode z = new BTreeNode(y.MinDeg, y.isLeaf);
        z.num = MinDeg - 1;

        for (int j = 0; j < MinDeg - 1; j++)
            z.keys[j] = y.keys[j + MinDeg];
        if (!y.isLeaf) {
            for (int j = 0; j < MinDeg; j++)
                z.children[j] = y.children[j + MinDeg];
        }
        y.num = MinDeg - 1;

        for (int j = num; j >= i + 1; j--)
            children[j + 1] = children[j];
        children[i + 1] = z;

        for (int j = num - 1; j >= i; j--)
            keys[j + 1] = keys[j];
        keys[i] = y.keys[MinDeg - 1];

        num = num + 1;
    }


    public List<Integer> traverse() {
        int i;
        result.clear();
        for (i = 0; i < num; i++) {
            if (!isLeaf) {
                result.addAll(children[i].traverse());
            }
            result.add(keys[i]);
            System.out.printf(" %d", keys[i]);

        }

        if (!isLeaf) {
            result.addAll(children[i].traverse());
        }
        return result;
    }


    public BTreeNode search(int key) {
        result.clear();
        int i = 0;
        BTreeNode tmp;
        while (i < num && key > keys[i]) {
            result.add(keys[i]);
            i++;
        }
        if (i < num && keys[i] == key) {
            result.add(keys[i]);
            return this;
        }
        if (isLeaf)
            return null;
        tmp = children[i].search(key);
        result.addAll(children[i].result);
        return tmp;
    }
}
