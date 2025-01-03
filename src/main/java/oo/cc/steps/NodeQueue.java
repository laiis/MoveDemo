package oo.cc.steps;

import java.util.ArrayList;
import java.util.List;

public class NodeQueue<T> {

    private final List<T> nodeQueueList = new ArrayList<>();
    private final int position = 0;

    public T peek() {
        if (!nodeQueueList.isEmpty()) {
            return nodeQueueList.get(position);
        }

        return null;
    }

    public void enqueue(T node) {
        nodeQueueList.add(node);
    }

    public T dequeue() {
        if (nodeQueueList.isEmpty()) {
            return null;
        }
        return nodeQueueList.remove(position);
    }

    public boolean isEmpty() {
        return nodeQueueList.isEmpty();
    }
}
