package edu.hw10.testinginterfaces;

import edu.hw10.task2.Cache;

public interface LongProcessOperationInterface {
    @Cache(persist = true)
    int longProcess() throws InterruptedException;
}
