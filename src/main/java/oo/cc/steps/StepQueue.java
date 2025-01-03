package oo.cc.steps;

import java.util.ArrayList;
import java.util.List;

public class StepQueue {

    private final List<Step> stepQueueList = new ArrayList<>();
    private final int position = 0;

    public void enqueue(Step step) {
        stepQueueList.add(step);
    }

    public Step dequeue() {
        if (stepQueueList.isEmpty()) {
            return null;
        }
        return stepQueueList.remove(position);
    }

    public boolean isEmpty() {
        return stepQueueList.isEmpty();
    }
}
