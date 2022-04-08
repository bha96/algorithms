package org.pg4200.ex05;

import org.pg4200.les05.MyMapBinarySearchTree;

public class BinaryTreeLeftMaxDelete<K extends Comparable<K>,V> extends MyMapBinarySearchTree<K, V> {


    @Override
    protected TreeNode delete(K key, TreeNode subtreeRoot){
        if(subtreeRoot == null){
            return null;
        }
        //compare the key you put in to the key of current node
        int cmp = key.compareTo(subtreeRoot.key);

        //key is smaller than the key of the current node
        if(cmp < 0){
            /* we go deeper into the tree and set the "fixed"
            subtree to current nodes left*/
            subtreeRoot.left = delete(key, subtreeRoot.left);

            //return the "fixed" subtree including current node
            return subtreeRoot;
        }

        //key is greater than the key of the current node
        if(cmp > 0){
            /* we go deeper into the tree and set the "fixed"
            subtree to current nodes right */
            subtreeRoot.right = delete(key, subtreeRoot.right);

            //return the "fixed" subtree including current node
            return subtreeRoot;
        }

        /* We have covered greater and lesser, so we know
        it is equal and this is the node we want to delete */
        assert cmp == 0;

        size--;

        //0 or 1 child and left is null
        if(subtreeRoot.left == null){
            return subtreeRoot.right;
        }
        //0 or 1 child and right is null
        if (subtreeRoot.right == null){
            return subtreeRoot.left;
        }

        //Has two children
        assert subtreeRoot.left != null && subtreeRoot.right != null;

        //Set current node to the node with the greatest value in the left subtree
        TreeNode tmp = subtreeRoot;
        subtreeRoot = max(tmp.left);
        subtreeRoot.left = deleteMax(tmp.left);
        subtreeRoot.right = tmp.right;

        return subtreeRoot;

    }

    private TreeNode max(TreeNode subtreeRoot){
        if (subtreeRoot.right == null){
            return subtreeRoot;
        }
        return max(subtreeRoot.right);
    }

    private TreeNode deleteMax(TreeNode subtreeRoot){
        if (subtreeRoot.right == null){
            return subtreeRoot.left;
        }

        subtreeRoot.right = deleteMax(subtreeRoot.right);

        return subtreeRoot;
    }
}
