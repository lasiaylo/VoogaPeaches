package engine.managers;

import groovy.util.Eval;

import java.util.Map;

/**
 * A class that evaluates groovy class from a string
 * @author Albert
 * @author Lasia
 * @author Simran
 * @author Walker
 */
public class Logic {
    private String myLogic;
    private Map<String, Object> myParameters;

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

    /**
     * @return  the string for which the groovy script would evaluate
     */
    public String getLogic() {
        return myLogic;
    }
}
