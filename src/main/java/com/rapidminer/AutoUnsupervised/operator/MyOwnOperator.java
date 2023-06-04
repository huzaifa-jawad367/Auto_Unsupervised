package com.rapidminer.AutoUnsupervised.operator;

import java.util.logging.Level;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.tools.LogService;

/**
 * Describe what your operator does.
 *
 * @author Huzaifa Jawad
 *
 */

public class MyOwnOperator extends Operator {
    public MyOwnOperator(OperatorDescription description) {
        super(description);
    }

    @Override
    public void doWork() {
        LogService.getRoot().log(Level.INFO, "Hello World!");
    }
}
