package com.smartgnan.data;

import com.smartgnan.algorithms.BaseAlgorithm;

import java.lang.reflect.Type;

public class AlgorithmDetails {
    public String Name;
    public Class<? extends BaseAlgorithm> AlgorithmClass;
    public boolean IsFree;

    public AlgorithmDetails(String name, Class<? extends BaseAlgorithm> algorithmClass, boolean isfree) {
        Name = name;
        AlgorithmClass = algorithmClass;
        IsFree = isfree;
    }
}
