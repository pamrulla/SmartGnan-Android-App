package com.smartgnan.helpers;

import java.util.ArrayList;

public class Options {
    public String text;
    public OptionType type;
    public ArrayList<Integer> inputs;

    public Options(String text, OptionType type) {
        this.text = text;
        this.type = type;
        inputs = new ArrayList<>();
    }
}
