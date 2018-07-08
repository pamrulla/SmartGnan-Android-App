package com.smartgnan.data;

import com.smartgnan.algorithms.BaseAlgorithm;

import java.lang.reflect.Type;

public class AlgorithmDetails {
    public String Name;
    public Class<? extends BaseAlgorithm> AlgorithmClass;

    public AlgorithmDetails(String name, Class<? extends BaseAlgorithm> algorithmClass) {
        Name = name;
        AlgorithmClass = algorithmClass;
    }
}
