package com.rapidminer.AutoUnsupervised.operator;

import java.util.logging.Level;

import org.h2.engine.User;
import java.util.List;
import java.util.LinkedList;

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.tools.LogService;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Attributes;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.Example;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.DoubleArrayDataRow;
import com.rapidminer.example.table.MemoryExampleTable;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.tools.Ontology;
import com.rapidminer.operator.ports.metadata.ExampleSetPrecondition;
import com.rapidminer.operator.UserError;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeBoolean;
import com.rapidminer.parameter.ParameterTypeString;
import com.rapidminer.parameter.conditions.BooleanParameterCondition;

/**
 * Describe what your operator does.
 *
 * @author Huzaifa Jawad
 *
 */

public class MyOwnOperator extends Operator {

    // private InputPort exampleSetInput = getInputPorts().createPort("example set");
    private InputPort exampleSetInput = getInputPorts().createPort("example set", ExampleSet.class);
    private OutputPort exampleSetOutput = getOutputPorts().createPort("example set");
    public static final String PARAMETER_USE_CUSTOM_TEXT = "use custom text";
    public static final String PARAMETER_TEXT = "log text";

    public MyOwnOperator(OperatorDescription description) {
        super(description);
        // exampleSetInput.addPrecondition(
        // new SimplePrecondition( exampleSetInput, new MetaData(ExampleSet.class) ));
        exampleSetInput.addPrecondition(new ExampleSetPrecondition( exampleSetInput, new String[]{"test"}, Ontology.ATTRIBUTE_VALUE) );
    }

    @Override
    public List<ParameterType> getParameterTypes(){
        List<ParameterType> types = super.getParameterTypes();

        types.add(new ParameterTypeBoolean(PARAMETER_USE_CUSTOM_TEXT, "If checked, a custom text is printed to the log view.", false, false));

        ParameterType type = new ParameterTypeString(PARAMETER_TEXT, "This parameter defines which text is logged to the console when this operator is executed.", "This is a default text", false);

        type.registerDependencyCondition(new BooleanParameterCondition(this, PARAMETER_USE_CUSTOM_TEXT, true, true));

        types.add(type);

        return types;
    }

    @Override
    public void doWork() throws UserError, OperatorException {
        LogService.getRoot().log(Level.INFO, "Hello World!");

        try {
            ExampleSet exampleSet = exampleSetInput.getData(ExampleSet.class);
            // Rest of the code
            // get attributes from example set
            Attributes attributes = exampleSet.getAttributes();
            // create a new attribute
            String newName = "newAttribute";
            // define the name and the type of the new attribute
            // valid types are 
            // - nominal (sub types: binominal, polynominal, string, file_path)
            // - date_time (sub types: date, time)
            // - numerical (sub types: integer, real)
            Attribute targetAttribute = AttributeFactory.createAttribute(newName, Ontology.REAL);

            targetAttribute.setTableIndex(attributes.size());
            exampleSet.getExampleTable().addAttribute(targetAttribute);
            attributes.addRegular(targetAttribute);

            for(Example example:exampleSet){
                example.setValue(targetAttribute, Math.round(Math.random()*10+0.5));
            }
            exampleSetOutput.deliver(exampleSet);
        } catch (UserError e) {
            // Handle the exception appropriately
            LogService.getRoot().log(Level.INFO, "User Error Occured!");
            System.exit(0);
        }
    }
}
