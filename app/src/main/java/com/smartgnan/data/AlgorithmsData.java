package com.smartgnan.data;

import com.smartgnan.algorithms.LinkedLists.SingleLinkedLists;
import com.smartgnan.algorithms.Queue.Queue_sg;
import com.smartgnan.algorithms.Sort.BubbleSort;
import com.smartgnan.algorithms.Stack.Stack;

import java.util.ArrayList;

public final class AlgorithmsData {
    public final ArrayList<CategoryDetails> Categories = new ArrayList() {{
        add(new CategoryDetails("Sorting", new ArrayList<AlgorithmDetails>() {{
            new AlgorithmDetails("Bubble Sort", BubbleSort.class);
        }}));
        add(new CategoryDetails("Queue", new ArrayList<AlgorithmDetails>() {{
            new AlgorithmDetails("Queue", Queue_sg.class);
        }}));
        add(new CategoryDetails("Stacks", new ArrayList<AlgorithmDetails>() {{
            new AlgorithmDetails("Stack", Stack.class);
        }}));
        add(new CategoryDetails("Linked Lists", new ArrayList<AlgorithmDetails>() {{
            new AlgorithmDetails("Single Linked Lists", SingleLinkedLists.class);
        }}));
    }};
}
