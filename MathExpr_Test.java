import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MathExpr {
	public static void main(String[] args) {
		double a = System.currentTimeMillis();
		/*
		 * String str = "23+ 2"; double r = Calculate(ReversePolish(str)); boolean k =
		 * Double.isNaN(r); System.out.println("等式是否无效:" + k); System.out.println("结果为:"
		 * + Math.round(r));
		 */

		String[] strlist = new String[] { "1+2.0*sin(37+(25*3))", "(2+ 3.50)*4*sqrt(sin(1.5))", "-3+4/ (2.5+3.7)",
				"(-3+4)/2.5+3.9", "1.2-3.5*5.2-13.2", "1.2-3.5*5.2-13.7", "2.3*5*7 - 12*9/8",
				"-sin(3.5-sqrt(4)) + cos(tan(2.5))", "sqrt(-1)", "2.5 4.0", "1+2", "3+4/3+7", "1*2*3*4+5",
				"2*3+4*5+6/7", "1/2-1*3/3", "1/2-1*4/3", ")1(", "1+((2-3)" };

		for (int i = 0; i < 18; i++) {
			String str = strlist[i];
			boolean k = Double.isNaN(parse(str));
			System.out.println("等式:" + str);
			System.out.println("等式是否无效:" + k);
			System.out.println("结果为:" + Math.round(parse(str)));
			System.out.println("------------------------------------------");
		}

		double b = System.currentTimeMillis();
		System.out.println("花费时间：" + (b - a) + "毫秒");

	}

	public static double parse(String str) {
		double Result = Calculate(ReversePolish(str));
		return Result;
	}

	public static List<String> ReversePolish(String str) {
		int BlankStarts = 0;
		int BlankEnds = 0;
		int BlankLength = 0;
		int OperatorLength = 0;
		List<String> Result = new ArrayList<String>();
		String[] temp = new String[50];
		Stack<String> OperatorStack = new Stack<String>();

		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (Character.isWhitespace(charArray[i])) {
				if (BlankLength == 0) {
					BlankStarts = i;
				}
				BlankLength += 1;
			} else {
				if (BlankLength == 0) {
					continue;
				} else {
					BlankEnds = BlankStarts + BlankLength - 1;
					BlankLength = 0;
					if (BlankStarts > 0 && BlankEnds < (charArray.length - 1)) {
						char SInvalid = charArray[BlankStarts - 1];
						char EInvalid = charArray[BlankEnds + 1];
						if (((SInvalid == '.') || (Character.isDigit(SInvalid)))
								&& ((EInvalid == '.') || (Character.isDigit(EInvalid)))) {
							return Result;
						}
					}
				}
			}
		}

		for (int i = 0; i < charArray.length; i++) {
			if (i == 0 && (charArray[i] == '*' || charArray[i] == '/' || charArray[i] == ')')) {
				return Result;
			}
			if (charArray[i] == '+' || charArray[i] == '-' || charArray[i] == '*' || charArray[i] == '/') {
				OperatorLength += 1;
				if (OperatorLength > 1) {
					return Result;
				}
			} else {
				OperatorLength = 0;
			}
		}

		int leftp = 0;
		int rightp = 0;
		for (int i = 0; i < charArray.length; i++) {
			if (leftp == 0 && rightp == 1) {
				return Result;
			}
			if (charArray[i] == '(') {
				leftp++;
			}
			if (charArray[i] == ')') {
				rightp++;
			}
		}
		if (leftp != rightp) {
			return Result;
		}

		str = str.replaceAll(" ", "");
		charArray = str.toCharArray();
		Outer: for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] != ' ') {
				if (Character.isDigit(charArray[i])) {
					temp[i] = charArray[i] + "";
					int dot = 0;
					for (int j = i + 1; j < charArray.length; j++) {
						if (Character.isDigit(charArray[j]) || charArray[j] == '.') {
							if (charArray[j] == '.') {
								dot++;
							}
							if (dot == 2) {
								return Result;
							}
							if (j != (charArray.length - 1)) {
								continue;
							} else {
								for (int n = i + 1; n <= j; n++) {
									temp[i] = temp[i] + charArray[n] + "";
									if (n == j && charArray[n] == '.') {
										return Result;
									}
								}
								break Outer;
							}
						} else {
							for (int k = i + 1; k < j; k++) {
								temp[i] = temp[i] + charArray[k] + "";
							}
							i = j - 1;
							break;
						}
					}
				} else {
					if (charArray[i] == '+' || charArray[i] == '-') {
						temp[i] = charArray[i] + "1";
					} else if (charArray[i] == '/' || charArray[i] == '*') {
						temp[i] = charArray[i] + "2";
					} else if (charArray[i] == '(') {
						temp[i] = charArray[i] + "4";
					} else if (charArray[i] == ')') {
						temp[i] = charArray[i] + "";
					} else {
						if (i < (charArray.length - 5)) {
							if (charArray[i] == 's' && charArray[i + 1] == 'i' && charArray[i + 2] == 'n') {
								temp[i] = "sin" + "3";
								i = i + 2;
							} else if (charArray[i] == 'c' && charArray[i + 1] == 'o' && charArray[i + 2] == 's') {
								temp[i] = "cos" + "3";
								i = i + 2;
							} else if (charArray[i] == 't' && charArray[i + 1] == 'a' && charArray[i + 2] == 'n') {
								temp[i] = "tan" + "3";
								i = i + 2;
							} else if (i < (charArray.length - 6)) {
								if (charArray[i] == 's' && charArray[i + 1] == 'q' && charArray[i + 2] == 'r'
										&& charArray[i + 3] == 't') {
									temp[i] = "sqrt" + "3";
									i = i + 3;
								} else {
									return Result;
								}
							}
						}
					}
				}
			}
		}

		String bowl;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != null) {
				if (Character.isDigit(temp[i].charAt(0))) {
					Result.add(temp[i]);
				} else if (temp[i].equals("(4")) {
					OperatorStack.push(temp[i]);
				} else if (temp[i].equals(")")) {
					while (!OperatorStack.peek().equals("(4")) {
						bowl = OperatorStack.pop();
						Result.add(bowl.substring(0, bowl.length() - 1));
					}
					OperatorStack.pop();
				} else {
					if (OperatorStack.isEmpty()) {
						OperatorStack.push(temp[i]);
					} else {
						int operator1 = Integer.parseInt(temp[i].substring(temp[i].length() - 1));
						int operator2 = Integer
								.parseInt(OperatorStack.peek().substring(OperatorStack.peek().length() - 1));
						if (operator1 == 4 || operator1 > operator2) {
							OperatorStack.push(temp[i]);
						} else {
							while (operator1 <= operator2 && operator2 != 4) {
								bowl = OperatorStack.pop();
								Result.add(bowl = bowl.substring(0, bowl.length() - 1));
								if (!OperatorStack.isEmpty()) {
									operator2 = Integer.parseInt(
											OperatorStack.peek().substring(OperatorStack.peek().length() - 1));
								} else {
									break;
								}
							}
							OperatorStack.push(temp[i]);
						}
					}
				}
			}
		}

		while (!OperatorStack.isEmpty()) {
			bowl = OperatorStack.pop();
			Result.add(bowl.substring(0, bowl.length() - 1));
		}

		return Result;
	}

	public static double Calculate(List<String> PolishExpression) {
		Stack<String> Contain = new Stack<String>();

		if (PolishExpression.size() == 0) {
			return Double.NaN;
		}

		for (int i = 0; i < PolishExpression.size(); i++) {
			String s = PolishExpression.get(i);
			if (Character.isDigit(s.charAt(0))) {
				Contain.push(s);
			} else {
				double Num1 = Double.parseDouble(Contain.pop());
				double Num2 = 0;
				if (!Contain.isEmpty()) {
					if (!s.equals("sin") && !s.equals("cos") && !s.equals("tan") && !s.equals("sqrt")) {
						Num2 = Double.parseDouble(Contain.pop());
					} else {
						if (s.equals("+") || s.equals("-")) {
							Num2 = 0;
						} else if (s.equals("*") || s.equals("/")) {
							return Double.NaN;
						}
					}
				}

				switch (s) {
				case "+":
					Contain.push((Num2 + Num1) + "");
					continue;
				case "-":
					Contain.push((Num2 - Num1) + "");
					continue;
				case "*":
					Contain.push((Num2 * Num1) + "");
					continue;
				case "/":
					if (Num1 == 0) {
						return Double.NaN;
					} else {
						double Division = Num2 / Num1;
						Contain.push(Division + "");
					}
					continue;
				case "sin":
					Contain.push(Math.sin(Num1) + "");
					continue;
				case "cos":
					Contain.push(Math.cos(Num1) + "");
					continue;
				case "tan":
					if (Num1 % Math.PI == 0) {
						return Double.NaN;
					} else {
						Contain.push(Math.tan(Num1) + "");
						continue;
					}
				case "sqrt":
					if (Num1 < 0) {
						return Double.NaN;
					} else {
						Contain.push(Math.sqrt(Num1) + "");
						continue;
					}
				default:
					return Double.NaN;
				}
			}
		}

		double Result = Double.parseDouble(Contain.pop());
		return Result;
	}
}
