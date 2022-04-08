package org.pg4200.ex05;

import org.pg4200.les05.MyMapTreeBased;

import java.util.Objects;

public class TernaryTreeMap<K extends Comparable<K>,V> implements MyMapTreeBased<K,V> {

    protected class TreeNode {
        public K firstKey;
        public V firstValue;

        public K secondKey;
        public V secondValue;

        public TreeNode left;
        public TreeNode middle;
        public TreeNode right;
    }

    protected TreeNode root;

    protected int size = 0;

    @Override
    public void put(K key, V value) {
        Objects.requireNonNull(key);
        root = put(key, value, root);
    }

    private TreeNode put(K key, V value, TreeNode subtree) {

        if (subtree == null) {
            TreeNode node = new TreeNode();
            node.firstKey = key;
            node.firstValue = value;
            size++;
            return node;
        }

        int cmpFirst = key.compareTo(subtree.firstKey);
        /*
            The value of the key to the node we want to
            insert is smaller than firstKey.
        */
        if (cmpFirst < 0) {
            subtree.left = put(key, value, subtree.left);
            return subtree;
        //Greater than first, and we haven't added a second key yet.
        } else if (cmpFirst > 0 && subtree.secondKey == null) {
            subtree.secondKey = key;
            subtree.secondValue = value;
            size++;
            return subtree;
        //replace firstValue
        }else if(cmpFirst == 0){
            subtree.firstValue = value;
            return subtree;
        }
        //Middle
        int cmpSecond = key.compareTo(subtree.secondKey);
        if (cmpSecond < 0) {
            subtree.middle = put(key, value, subtree.middle);
        //Right
        }else if(cmpSecond > 0){
            subtree.right = put(key, value, subtree.right);
        //Replace value
        }else{
            subtree.secondValue = value;

        }
        return subtree;
    }

    @Override
    public void delete(K key) {
        Objects.requireNonNull(key);
        root = delete(key, root);
    }

    protected TreeNode delete(K key, TreeNode subtreeRoot){
        if (subtreeRoot == null){
            return null;
        }

        int cmpFirst = key.compareTo(subtreeRoot.firstKey);

        //Search down left subtree
        if (cmpFirst < 0){
            subtreeRoot.left = delete(key, subtreeRoot.left);
            return subtreeRoot;
        }
        //Delete first
        if (cmpFirst == 0){
            size--;

            if (subtreeRoot.secondKey == null){

                /*
                    If the secondKey is null then
                    right also need to be null.
                 */
                assert subtreeRoot.right == null;

                //If all children are null then we can return null
                if (subtreeRoot.left == null && subtreeRoot.middle == null) {
                    return null;
                }

                //2 of the children are null, either left and right or middle and right.
                if (subtreeRoot.left == null) {
                    return subtreeRoot.middle;
                } else if (subtreeRoot.middle == null) {
                    return subtreeRoot.left;
                /*
                left AND middle are not null,
                so we take the node with the smallest key in middle
                and replace the current with that one
                 */
                } else {
                    TreeNode min = min(subtreeRoot.middle);
                    subtreeRoot.firstKey = min.firstKey;
                    subtreeRoot.firstValue = min.firstValue;
                    subtreeRoot.middle = deleteMin(subtreeRoot.middle);
                    return subtreeRoot;
                }

            }else {
                /*
                If middle is null we move everything down one "level"
                so right -> middle and second -> first
                 */
                if (subtreeRoot.middle == null) {

                    moveSecondToFirst(subtreeRoot);
                    subtreeRoot.middle = subtreeRoot.right;
                    subtreeRoot.right = null;
                    return subtreeRoot;
                /*
                Same here but left is null, so we move everything down
                one "level". right -> left, middle -> left and right -> middle.
                Right and second will now be null
                 */
                } else if(subtreeRoot.left == null){

                    moveSecondToFirst(subtreeRoot);
                    subtreeRoot.left = subtreeRoot.middle;
                    subtreeRoot.middle = subtreeRoot.right;
                    subtreeRoot.right = null;
                    return subtreeRoot;

                /*
                Neither left nor middle is null, so we set first
                to the smallest node in the middle subtree
                 */
                }else {

                    TreeNode min = min(subtreeRoot.middle);
                    subtreeRoot.firstKey = min.firstKey;
                    subtreeRoot.firstValue = min.firstValue;
                    subtreeRoot.middle = deleteMin(subtreeRoot.middle);
                    return subtreeRoot;

                }
            }
        }
        //Search down middle or second subtree
        if (cmpFirst > 0){

            //Search down middle subtree
            if (subtreeRoot.secondKey == null) {
                subtreeRoot.middle = delete(key, subtreeRoot.middle);
                return subtreeRoot;
            }

            /*
            It is not possible to compare the second key before this because
            compareTo requires non-null, and we haven't ruled that out before now
             */
            int cmpSecond = key.compareTo(subtreeRoot.secondKey);

            /*
            key is greater than first but lower than second,
            so we search down the middle subtree
             */
            if (cmpSecond < 0){
                subtreeRoot.middle = delete(key, subtreeRoot.middle);
                return subtreeRoot;
            }
            /*
            key is greater than second,
            so we search down right subtree
             */
            if (cmpSecond > 0){
                subtreeRoot.right = delete(key, subtreeRoot.right);
                return subtreeRoot;
            }

            //Delete second
            assert cmpSecond == 0;
            size--;

            /*
            if right is null we don't have to do anything
             */
            if (subtreeRoot.right == null){
                subtreeRoot.secondKey = null;
                subtreeRoot.secondValue = null;

            /*
            if right is not null we set the node with
            the smallest key to our second, and the new right is the
            same but with the node with the smallest key removed
             */
            }else{
                TreeNode min = min(subtreeRoot.right);
                subtreeRoot.secondKey = min.firstKey;
                subtreeRoot.secondValue = min.firstValue;
                subtreeRoot.right = deleteMin(subtreeRoot.right);
            }
            return subtreeRoot;

        }




        return subtreeRoot;
    }

    private TreeNode moveSecondToFirst(TreeNode subtreeRoot){
        subtreeRoot.firstKey = subtreeRoot.secondKey;
        subtreeRoot.firstValue = subtreeRoot.secondValue;
        subtreeRoot.secondKey = null;
        subtreeRoot.secondValue = null;
        return subtreeRoot;
    }

    @Override
    public V get(K key) {
        Objects.requireNonNull(key);
        return get(key, root);
    }

    public V get(K key, TreeNode subtreeRoot){

        if (subtreeRoot == null){
            return null;
        }
        int cmpFirst = key.compareTo(subtreeRoot.firstKey);
        if(cmpFirst == 0){
            return subtreeRoot.firstValue;
        }

        if (cmpFirst < 0){
            return get(key, subtreeRoot.left);
        }
        if (subtreeRoot.middle == null && subtreeRoot.right == null){
            return null;
        }
        int cmpSecond = key.compareTo(subtreeRoot.secondKey);
        if (cmpSecond < 0){
            return get(key, subtreeRoot.middle);
        }else{
            return get(key, subtreeRoot.right);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getMaxTreeDepth() {
        return 0;
    }

    private TreeNode min(TreeNode node){
        if (node.left == null){
            return node;
        }
        return min(node.left);
    }

    private TreeNode deleteMin(TreeNode subtreeRoot){

        /*If subtreeRoot.left is null then we
        are at the node with the smallest key*/
        if (subtreeRoot.left == null){
            if (subtreeRoot.secondKey == null){
                return subtreeRoot.middle;
            }else {
                moveSecondToFirst(subtreeRoot);
                subtreeRoot.left = subtreeRoot.middle;
                subtreeRoot.middle = subtreeRoot.right;
                subtreeRoot.right = null;
                return subtreeRoot;
            }
        }

        subtreeRoot.left = deleteMin(subtreeRoot.left);

        return subtreeRoot;
    }






}
