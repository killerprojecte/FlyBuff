package flyproject.flybuff.utils;

import java.util.Stack;

public class MathEngine {

    private static double number(char[] arr, int start, double end) {
        StringBuilder buffer = new StringBuilder();
        for (int i = start; i <= end; i++) {
            buffer.append(arr[i]);
        }
        return Double.parseDouble(buffer.toString());
    }

    private static int compute(String format) {
        if (format.equals("+") || format.equals("-"))
            return 1;
        if (format.equals("*") || format.equals("/"))
            return 2;
        return 0;
    }

    private static String compute(double a, double b, String format) {
        double res;
        if (format.equals("+")) {
            res = a + b;
            return String.valueOf(res);
        }
        if (format.equals("-")) {
            res = a - b;
            return String.valueOf(res);
        }
        if (format.equals("*")) {
            res = a * b;
            return String.valueOf(res);
        }
        if (format.equals("/") && b != 0) {
            res = a / b;
            return String.valueOf(res);
        } else {
            throw new IndexOutOfBoundsException("MathEngine Failed calculate result");
        }
    }

    public static String format(String mathstr) {
        Stack<Double> numbers = new Stack<>();
        Stack<String> operator = new Stack<>();
        operator.push(".");
        char[] exps = mathstr.toCharArray();
        int start = 0;
        if (exps[0] == '-') numbers.push(0.0);
        for (int j = 0; j < exps.length; j++) {
            if (exps[j] == '+' || exps[j] == '*' || exps[j] == '/' || exps[j] == '-') {
                if (start <= j - 1) {
                    numbers.push(number(exps, start, j - 1));
                }
                start = j + 1;
                while (compute(operator.peek()) >= compute(String.valueOf(exps[j]))) {
                    double two = numbers.peek();
                    numbers.pop();
                    double one = numbers.peek();
                    numbers.pop();
                    String result = compute(one, two, operator.peek());
                    operator.pop();
                    if (result.equals("error")) {
                        return result;
                    }
                    numbers.push(Double.valueOf(result));
                }
                operator.push(String.valueOf(exps[j]));
            }
        }
        numbers.push(number(exps, start, exps.length - 1));
        while (operator.size() > 1) {
            double two = numbers.peek();
            numbers.pop();
            double one = numbers.peek();
            numbers.pop();
            String op = operator.peek();
            operator.pop();
            String value = compute(one, two, op);
            if (value.equals("error")) {
                return value;
            }
            numbers.push(Double.valueOf(value));
        }
        return numbers.peek().toString();
    }
}
