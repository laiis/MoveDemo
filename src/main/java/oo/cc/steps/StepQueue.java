package oo.cc.steps;

import java.util.ArrayList;
import java.util.List;

public class StepQueue {

    private final List<Step> stepQueue = new ArrayList<>();
    private final int position = 0;

    public void enqueue(Step step) {
        stepQueue.add(position, step);
    }

    public Step dequeue() {
        if (stepQueue.isEmpty()) {
            return null;
        }
        return stepQueue.remove(position);
    }

    public boolean isEmpty() {
        return stepQueue.isEmpty();
    }
}
