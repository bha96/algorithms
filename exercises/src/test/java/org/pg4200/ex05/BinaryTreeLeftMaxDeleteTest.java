package org.pg4200.ex05;

import org.pg4200.les05.MyMapBinarySearchTreeTest;

public class BinaryTreeLeftMaxDeleteTest extends MyMapBinarySearchTreeTest {

    @Override
    protected <K extends Comparable<K>, V> BinaryTreeLeftMaxDelete getTreeInstance() {
        return new BinaryTreeLeftMaxDelete<>();
    }
}
