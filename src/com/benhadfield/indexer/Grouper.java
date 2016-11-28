package com.benhadfield.indexer;

import com.benhadfield.posting.Posting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * The Grouper class is responsible for grouping postings across multiple files by term.
 */

public class Grouper {
    private TreeMap<String, ArrayList<Posting>> group = new TreeMap<>();
    private Mapper[] mappers;

    public Grouper(Mapper... mappers) {
        this.mappers = mappers;
        group();
    }


    public TreeMap<String, ArrayList<Posting>> getGroup() {
        return group;
    }

    public void _printGroup() {
        System.out.println("Term\tPostings");
        group.forEach((term, postings) -> {
            System.out.print(term);
            System.out.print(" \t");
            for (Posting posting : postings) {
                System.out.print(posting.toString());
                System.out.print(", ");
            }
            System.out.println();
        });
    }

    private void group() {
        for (Mapper mapper : mappers) {
            TreeMap<String, Posting> map = mapper.getMap();
            map.forEach(this::addToGroup);
        }
    }

    private void addToGroup(String term, Posting posting) {
        if (group.containsKey(term)) {
            group.get(term).add(posting);
        } else {
            group.put(term, new ArrayList<>(Collections.singletonList(posting)));
        }
    }
}
