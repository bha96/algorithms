package org.pg4200;

import org.pg4200.ex03.StringComparator;

public class Main {
    public static void main(String[] args) {
        StringComparator stringComparator = new StringComparator();

        System.out.println(stringComparator.compare("aab", "aabs"));
    }
}
