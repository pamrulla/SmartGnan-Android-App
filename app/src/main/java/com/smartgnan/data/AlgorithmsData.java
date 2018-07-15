package com.smartgnan.data;

import com.smartgnan.algorithms.LinkedLists.SingleLinkedLists;
import com.smartgnan.algorithms.Queue.Queue_sg;
import com.smartgnan.algorithms.Sort.BubbleSort;
import com.smartgnan.algorithms.Stack.Stack;

import java.util.ArrayList;

public class AlgorithmsData {
    public static ArrayList<CategoryDetails> Categories = new ArrayList();

    public static void Initialize() {
        UpdateSortingCategory();
        UpdateQueueCategory();
        UpdateStackCategory();
        UpdateLinkedListsCategory();
    }

    private static void UpdateLinkedListsCategory() {
        AlgorithmDetails singleLinkedList = new AlgorithmDetails("Single Linked Lists", Queue_sg.class, false);

        CategoryDetails cat = new CategoryDetails("Linked List");
        cat.Algorithms.add(singleLinkedList);

        Categories.add(cat);
    }

    private static void UpdateStackCategory() {
        AlgorithmDetails queue = new AlgorithmDetails("Stack", Queue_sg.class, true);

        CategoryDetails cat = new CategoryDetails("Stack");
        cat.Algorithms.add(queue);

        Categories.add(cat);
    }

    private static void UpdateQueueCategory() {
        AlgorithmDetails queue = new AlgorithmDetails("Queues", Queue_sg.class, true);

        CategoryDetails cat = new CategoryDetails("Queue");
        cat.Algorithms.add(queue);

        Categories.add(cat);
    }

    private static void UpdateSortingCategory() {
        AlgorithmDetails bubbleSort = new AlgorithmDetails("Bubble Sort nnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnn nnnnn", BubbleSort.class, true);

        CategoryDetails cat = new CategoryDetails("Sorting");
        cat.Algorithms.add(bubbleSort);

        Categories.add(cat);
    }

}
