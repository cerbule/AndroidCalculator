package com.alin.androidcalculator;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

/**
 * Created by Alin on 10/29/2017.
 */

public class Calculation {



    private final Symbols symbols;
    private CalculationResult calculationResult;
    private static String currentExpression;

    interface CalculationResult{
        void onExpresionChange(String result, boolean successful);
    }

    public void setCalculationResultListener(CalculationResult calculationResult){
        this.calculationResult = calculationResult;
        currentExpression = "";
    }

    public Calculation(){
        symbols = new Symbols();
    }

    /**
     * Delete a single character from currentExpression, unless is empty
     * "" - invalid
     * 543 - valid
     * 45*56 - valid
     */
    public void deleteCharacter(){
        if(currentExpression.length() > 0){
            currentExpression = currentExpression.substring(0, currentExpression.length()-1);
            calculationResult.onExpresionChange(currentExpression, true);
        }else {
            calculationResult.onExpresionChange("Invalid input", false);
        }
    }

    /**
     * Delete entire expression unless empty
     * "" - invalid
     */
    public void deleteExpression(){
        if(currentExpression.equals("")){
            calculationResult.onExpresionChange("Invalid input", false);
        }
        currentExpression = "";
        calculationResult.onExpresionChange(currentExpression, true);


    }

    /**
     * Append number to currentExpression if valid
     * "0" & number is 0 - invalid
     * "123456789012345678" - invalid
     * @param number
     */
    public void appendNumber(String number){
        if(currentExpression.startsWith("0") && number.equals("0")){
            calculationResult.onExpresionChange("Invalid input", false);
        }else {
            if(currentExpression.length() <= 16){
                currentExpression += number;
                calculationResult.onExpresionChange(currentExpression, true);
            }else{
                calculationResult.onExpresionChange("Expression too long", false);
            }
        }
    }

    /**
     * append an operator to currentExpression if valid
     * 56 - valid
     * 54* - invalid
     * 54*2 - valid
     * "" - invalid
     * @param operator one of:
     *                 - "*"
     *                 - "+"
     *                 - "-"
     *                 - "/"
     */
    public void appendOperator(String operator){
        if(validateExpression(currentExpression)){
            currentExpression += operator;
            calculationResult.onExpresionChange(currentExpression, true);
        }
    }

    /**
     * See type comment for appendOperator
     */
    public void appendDecimal(){
        if(validateExpression(currentExpression)){
            currentExpression += ".";
            calculationResult.onExpresionChange(currentExpression, true);
        }
    }

    /**
     * If currentExpression passes checks, pass currentExpression to symbol object,
     * then return the result
     */
    public void performEvaluation(){
        if(validateExpression(currentExpression)){
            try {
                Double result = symbols.eval(currentExpression);
                currentExpression = Double.toString(result);
                calculationResult.onExpresionChange(currentExpression, true);
            } catch (SyntaxException e) {
                calculationResult.onExpresionChange("Invalid input", false);
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper function to validate expression against the following checks:
     * "" - invalid
     * 4646 - valid
     * @param expression
     * @return
     */
    public boolean validateExpression(String expression){
        if(expression.endsWith("*")||
                expression.endsWith("/")||
                expression.endsWith("+")||
                expression.endsWith("-")
                ){
            calculationResult.onExpresionChange("Invalid input", false);
            return false;
        }else if(expression.equals("")){
            calculationResult.onExpresionChange("Empty expression", false);
            return false;
        }else if(expression.length() > 16){
            calculationResult.onExpresionChange("Expression too long", false);
            return false;
        }else {
            return true;
        }
    }

}
