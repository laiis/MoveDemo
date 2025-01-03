package oo.cc.steps;

import java.util.ArrayList;
import java.util.List;

public class StepStack {
    private final List<Step> stepStackList = new ArrayList<>();
    private final int position = 0;

    public void push(Step step) {
        if (step != null) {
            stepStackList.add(position, step);
        }
    }

    public Step pop() {
        if (stepStackList.isEmpty()) {
            return null;
        }
        return stepStackList.remove(position);
    }
}
