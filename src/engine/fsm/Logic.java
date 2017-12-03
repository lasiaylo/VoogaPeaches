package engine.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import groovy.util.Eval;

import java.util.Map;

/**
 * A class that evaluates groovy class from a string
 * @author Albert
 * @author Lasia
 * @author richardtseng
 */
public class Logic extends TrackableObject {
    @Expose private String myLogic;
    @Expose private Map<String, Object> myParameters;

    /**
     * Creates a new Logic object from the database
     */
    private Logic() {}

    /**
     * Creates a new Logic
     * @param logicStatement    String statement to be evaluated
     */
    public Logic(String logicStatement, Map<String, Object> parameters) {
        myLogic = logicStatement;
        myParameters = parameters;
    }

    /**
     * Evaluates the logic in myLogic through the parameters specified
     * @return
     */
    public boolean evaluate() {
        String evalLogic = parseGroovyEvalString();
        return (boolean) Eval.me(evalLogic);
    }

    /**
     * @return  A String that replaces variable names with values
     */
    private String parseGroovyEvalString() {
        String[] evalLogicArray = myLogic.split("//s+");
        StringBuilder evalLogicBuilder = new StringBuilder("");
        for(int i = 0; i < evalLogicArray.length; i++) {
            if(myParameters.keySet().contains(evalLogicArray[i])) {
                evalLogicArray[i] = myParameters.get(evalLogicArray[i]).toString();
            }
            evalLogicBuilder.append(evalLogicArray[i] + " ");
        }

        return evalLogicBuilder.toString();
    }

    /**
     * Sets myLogic to param
     * @param newLogic  new logic string to be evaluated
     */
    public void setLogic(String newLogic) {
        myLogic = newLogic;
    }

    @Override
    public void initialize() {

    }
}