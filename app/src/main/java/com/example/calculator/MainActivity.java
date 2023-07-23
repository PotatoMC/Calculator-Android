package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private String operand1 = "";
    private static final String OPERATORS = "+-*/";
    private int openBrackets = 0;

    private static final int[] PRECEDENCE = {1, 1, 2, 2};
    private boolean clearResult = false;

    private String expression = "";
    private String operand2 = "";
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.results);
        float desiredTextSize = 26f;
        ViewGroup parent = (ViewGroup) findViewById(R.id.gridLayout); // replace parent_layout with the ID of the layout containing the buttons
        ArrayList<Button> buttons = getAllButtons(parent);
        for (Button button : buttons) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, desiredTextSize);
            button.setTextColor(getResources().getColor(R.color.white));
        }


        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("0");
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("1");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("2");
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("3");
            }
        });
        findViewById(R.id.buttonBrackets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bracketsClicked();
            }
        });
        findViewById(R.id.buttonBackspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expression.isEmpty()) {
                    expression = expression.substring(0, expression.length() - 1);
                    resultTextView.setText(expression);
                }
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("4");
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("5");
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("6");
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("7");
            }
        });
        findViewById(R.id.buttonPercent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentClicked();
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("8");
            }
        });
        findViewById(R.id.buttonDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decimalClicked();
            }
        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClicked("9");
            }
        });
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                clearResult = false;
                openBrackets = 0;
                resultTextView.setText("0");
            }
        });

        findViewById(R.id.buttonPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorClicked("+");
            }
        });
        findViewById(R.id.buttonMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorClicked("-");
            }
        });
        findViewById(R.id.buttonPlusMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusMinusClicked();
            }
        });

        findViewById(R.id.buttonMultiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorClicked("*");
            }
        });
        findViewById(R.id.buttonDivide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorClicked("/");
            }
        });
        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalsClicked();
            }
        });
    }

    private void numberClicked(String number) {
        if (clearResult) {
            expression = "";
            clearResult = false;
        }
        expression += number;
        resultTextView.setText(expression);
    }

    private void operatorClicked(String op) {
        if (clearResult) {
            clearResult = false;
        }
        if (!expression.isEmpty()) {
            if (OPERATORS.contains(expression.substring(expression.length() - 1))) {
                expression = expression.substring(0, expression.length() - 1);
            }
            expression += op;
            resultTextView.setText(expression);
        }
    }

    private void equalsClicked() {
        if (!expression.matches(".*\\d.*")) {
            return;
        }
        if (!expression.isEmpty() && OPERATORS.contains(expression.substring(expression.length() - 1))) {
            expression = expression.substring(0, expression.length() - 1);
        }
        try {
            String result = evaluateExpression(expression);
            resultTextView.setText(result);
            expression = result;
            clearResult = true;
        } catch (Exception e) {
            resultTextView.setText("Syntax Error");
            expression = "";
            clearResult = true;
        }
    }

    private BigDecimal performOperation() {
        BigDecimal num1 = new BigDecimal(operand1);
        BigDecimal num2 = new BigDecimal(operand2);
        BigDecimal result = BigDecimal.ZERO;
        switch (operator) {
            case "+":
                result = num1.add(num2);
                break;
            case "-":
                result = num1.subtract(num2);
                break;
            case "*":
                result = num1.multiply(num2);
                break;
            case "/":
                result = num1.divide(num2);
                break;
        }
        return result;
    }

    private void percentClicked() {
        if (clearResult) {
            expression = "";
            clearResult = false;
        }
        if (!expression.isEmpty() && Character.isDigit(expression.charAt(expression.length() - 1))) {
            BigDecimal num = new BigDecimal(expression);
            BigDecimal result = num.divide(new BigDecimal("100"));
            expression = result.toString();
            resultTextView.setText(expression);
        }
    }
    private void decimalClicked() {
        if (clearResult) {
            expression = "";
            clearResult = false;
        }
        expression += ".";
        resultTextView.setText(expression);
    }

    private void bracketsClicked() {
        if (clearResult) {
            expression = "";
            clearResult = false;
        }
        if (openBrackets > 0 && !expression.endsWith("(")) {
            expression += ")";
            openBrackets--;
        } else {
            if (!expression.isEmpty() && Character.isDigit(expression.charAt(expression.length() - 1))) {
                expression += "*";
            }
            expression += "(";
            openBrackets++;
        }
        resultTextView.setText(expression);
    }


    private void plusMinusClicked() {
        if (clearResult) {
            expression = "";
            clearResult = false;
        }
        if (expression.startsWith("-")) {
            expression = expression.substring(1);
        } else {
            expression = "-" + expression;
        }
        resultTextView.setText(expression);
    }

    private ArrayList<Button> getAllButtons(ViewGroup layout) {
        ArrayList<Button> buttons = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                buttons.addAll(getAllButtons((ViewGroup) child));
            } else if (child instanceof Button) {
                buttons.add((Button) child);
            }
        }
        return buttons;
    }

//    private String evaluateExpression(String expression) {
//        String[] tokens = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
//        Stack<String> operatorStack = new Stack<>();
//        Stack<BigDecimal> operandStack = new Stack<>();
//        for (String token : tokens) {
//            if (token.trim().isEmpty()) {
//                continue;
//            } else if (token.equals("(")) {
//                operatorStack.push(token);
//            } else if (token.equals(")")) {
//                while (!operatorStack.peek().equals("(")) {
//                    processOperator(operatorStack, operandStack);
//                }
//                operatorStack.pop();
//            } else if (OPERATORS.contains(token)) {
//                while (!operatorStack.empty() && !operatorStack.peek().equals("(") &&
//                        PRECEDENCE[OPERATORS.indexOf(token)] <= PRECEDENCE[OPERATORS.indexOf(operatorStack.peek())]) {
//                    processOperator(operatorStack, operandStack);
//                }
//                operatorStack.push(token);
//            } else {
//                operandStack.push(new BigDecimal(token));
//            }
//        }
//        while (!operatorStack.empty()) {
//            processOperator(operatorStack, operandStack);
//        }
//        return operandStack.peek().stripTrailingZeros().toPlainString();
//    }

    //    private void processOperator(Stack<String> operatorStack, Stack<BigDecimal> operandStack) {
//        String operator = operatorStack.pop();
//        BigDecimal num2 = operandStack.pop();
//        BigDecimal num1 = operandStack.pop();
//        BigDecimal result = BigDecimal.ZERO;
//        switch (operator) {
//            case "+":
//                result = num1.add(num2);
//                break;
//            case "-":
//                result = num1.subtract(num2);
//                break;
//            case "*":
//                result = num1.multiply(num2);
//                break;
//            case "/":
//                result = num1.divide(num2, 10, RoundingMode.HALF_UP); // specify scale and rounding mode
//                break;
//        }
//        operandStack.push(result);
//    }
    private String evaluateExpression(String expression) {
        String[] tokens = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
        Stack<String> operatorStack = new Stack<>();
        Stack<BigDecimal> operandStack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.trim().isEmpty()) {
                continue;
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.peek().equals("(")) {
                    processOperator(operatorStack, operandStack);
                }
                operatorStack.pop();
            } else if (OPERATORS.contains(token)) {
                while (!operatorStack.empty() && !operatorStack.peek().equals("(") &&
                        PRECEDENCE[OPERATORS.indexOf(token)] <= PRECEDENCE[OPERATORS.indexOf(operatorStack.peek())]) {
                    processOperator(operatorStack, operandStack);
                }
                operatorStack.push(token);
            } else {
                if (i > 0 && tokens[i - 1].equals("-") && (i == 1 || OPERATORS.contains(tokens[i - 2]) || tokens[i - 2].equals("("))) {
                    operandStack.push(new BigDecimal("-" + token));
                    operatorStack.pop();
                } else {
                    operandStack.push(new BigDecimal(token));
                }
            }
        }
        while (!operatorStack.empty()) {
            processOperator(operatorStack, operandStack);
        }
        return operandStack.peek().stripTrailingZeros().toPlainString();
    }

    private void processOperator(Stack<String> operatorStack, Stack<BigDecimal> operandStack) {
        String operator = operatorStack.pop();
        BigDecimal num2 = operandStack.pop();
        BigDecimal num1 = operandStack.pop();
        BigDecimal result = BigDecimal.ZERO;
        switch (operator) {
            case "+":
                result = num1.add(num2);
                break;
            case "-":
                result = num1.subtract(num2);
                break;
            case "*":
                result = num1.multiply(num2);
                break;
            case "/":
                result = num1.divide(num2, 10, RoundingMode.HALF_UP); // specify scale and rounding mode
                break;
        }
        operandStack.push(result);
    }

}