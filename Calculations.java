import java.util.Stack;

public class Calculations {
    public static void main(String[]args){
        /*
            next steps:
                - negative numbers
                - allow users to get their answers via typing
                - clean up the way the calculator looks lol
         */

        //testing the main methods used
        String test = "12.5*2";
        Calculations test1 = new Calculations(test);
        String postfixTest = test1.postfix();
        double evalTest = test1.calculate();
        System.out.println(postfixTest);
        System.out.println(evalTest);
    }
    private String equation;

    /**
     *
     * @param equation
     *      The equation that will be handled in this calculation
     */
    public Calculations(String equation){
        this.equation = equation;
    }

    /**
     *
     * @return
     *      the global variable, equation
     */
    public String getEquation(){
        return equation;
    }

    /**
     *      sets the global variable equation, to a new equation
     */
    public void setEquation(String equation){
        this.equation = equation;
    }

    /**
     *
     * @param elem
     *      the character that will be given a precedence based on PEMDAS rules
     * @return
     *      the precedence of the character accessed
     */
    public int precedence(char elem){
        int output = 0;
        if(elem == '+' || elem == '-')
            output = 1;
        else if(elem == '*' || elem == '/')
            output = 2;
        else if(elem == '^'|| elem == 'E')
            output = 3;
        else if(elem == '(')
            output = 4;
        else if(elem == ')')
            output = 4;
        return output;
    }

    /**
     *
     * @return
     *      the equation in it's postfix form
     */
    public String postfix() {
        Stack<String> operators = new Stack<>();
        Stack<String>operands = new Stack<>();

        //String builder that will continuously build the equation in it's postfix form
        StringBuilder postfixEq = new StringBuilder();

        //takes care of null error
        if (equation.length() == 0) {
            postfixEq.append("0");
            return postfixEq.toString();
        }
        for (int i = 0; i < equation.length(); i++) {
            //goes through each character individually
            char elem = equation.charAt(i);

            //try-catch to separate symbols from numeric values
            try {
                Integer num = Integer.parseInt(elem+"");
                System.out.println(elem);
                operands.push(elem + "");
            } catch (Exception e) {
                if (elem == '(')
                    operators.push(elem + "");
                else if (elem == '.') {
                    operands.push(elem + "");
                }
                else {
                    String current = numBuilder(operands);
                    postfixEq.append(current).append(" ");
                    if (elem == ')') {
                        while (!operators.isEmpty() && operators.peek().charAt(0) != '(')
                            postfixEq.append(operators.pop());
                        operators.pop();
                    }
                    else {
                        while (!operators.isEmpty() && precedence(elem) <= precedence(operators.peek().charAt(0))) {
                            if (operators.peek().equals("("))
                                break;
                            //appended with a space as a whitespace marker
                            postfixEq.append(operators.pop()).append(" ");
                        }
                        operators.push(elem + "");
                    }
                }
            }
        }
        String current = numBuilder(operands);
        postfixEq.append(current).append(" ");
        while (!operators.isEmpty()) {
            if (operators.peek().equals("(")) {
                return "ERROR";
            }
            postfixEq.append(operators.pop()).append(" ");
        }
        System.out.println(postfixEq.toString());
        return postfixEq.toString();
    }

    /**
     *
     * @param operands
     *      a stack of Integers in the form of Strings or decimals to accommodate double calculations
     * @return
     *      a String made of the elements found in operands
     * @throws IllegalArgumentException
     *      thrown when there is more than one decimal found
     */
    public String numBuilder(Stack<String>operands)throws IllegalArgumentException{
        /*temp stack to make sure that the operands stack will come out
        in the correct order when it is appended to the string*/
        Stack<String>temp = new Stack<>();
        boolean containsDeci = operands.contains(".");
        int decimalCounter = 0;
        String output = "";
        while(!operands.isEmpty()) {
            String popped = operands.pop();
            temp.push(popped);
        }
        if(!temp.isEmpty()) {
            String current = temp.pop();
            String decimal = "";
            while (!temp.isEmpty()) {
                if (containsDeci) {
                    while (temp.contains(".") && !temp.peek().equals(".")) {
                        //constructs the double before the decimal
                        current = (Integer.parseInt(current) * 10) + "";
                        current = Integer.parseInt(current) + Integer.parseInt(temp.pop()) + "";
                    }
                    if (temp.peek().equals(".")) {
                        //discards the decimal and takes count of the number
                        temp.pop();
                        decimalCounter += 1;
                    }
                    decimal = temp.pop();
                    if (!temp.isEmpty()) {
                        //constructs the double after the decimal
                        decimal = (Integer.parseInt(decimal) * 10) + "";
                        decimal = Integer.parseInt(decimal) + Integer.parseInt(temp.pop()) + "";
                    }
                } else {
                    // constructs the number
                    current = (Integer.parseInt(current) * 10) + "";
                    current = Integer.parseInt(current) + Integer.parseInt(temp.pop()) + "";
                }
            }
            //puts together each part of the number
            if (containsDeci) {
                current += "." + decimal;
            }
            output = current;
        }
        //makes sure there aren't multiple decimals in the double
        if(decimalCounter > 1)
            throw new IllegalArgumentException();
        return output;
    }

    /**
     *
     * @return
     *      the final result of the equation
     * @throws IllegalArgumentException
     *      thrown when a number is divided by zero
     */
    public double calculate()throws IllegalArgumentException{
        String eq = postfix();
        Stack<Double>evaluate = new Stack<>();
        int lastSpace = -1;
        if(eq.equals("0")){
            return 0;
        }
        for(int i = 0; i<eq.length();i++){
            char elem = eq.charAt(i);
            if(elem == ' '){
                /*gets characters through the use of the whitespace
                markers in order to account for multi-digit numbers*/
                String symbol = eq.substring(lastSpace+1,i);
                //try-catch to separate between symbols and numeric values
                try{
                    evaluate.push(Double.parseDouble(symbol));
                }
                catch(Exception e){
                    Double num2 = evaluate.pop();
                    Double num1 = evaluate.pop();
                    switch (symbol) {
                        case "E":
                            double E = Math.pow(10,num2);
                            evaluate.push(num1*E);
                            break;
                        case "+":
                            evaluate.push(num1 + num2);
                            break;
                        case "-":
                            evaluate.push(num1 - num2);
                            break;
                        case "*":
                            evaluate.push(num1 * num2);
                            break;
                        case "/":
                            if(num2 == 0){
                                throw new IllegalArgumentException();
                            }
                            evaluate.push(num1 / num2);
                            break;
                    }
                }
                lastSpace = i;
            }
        }
        return evaluate.pop();
    }
}
