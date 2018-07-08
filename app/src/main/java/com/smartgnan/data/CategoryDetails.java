package com.smartgnan.data;

import java.util.ArrayList;

public class CategoryDetails {
    public String Name;
    public ArrayList<AlgorithmDetails> Algorithms;

    public CategoryDetails(String name, ArrayList<AlgorithmDetails> algorithms) {
        Name = name;
        Algorithms = new ArrayList<>(algorithms);
    }
}
