package model;

public abstract class Model {
		
	protected boolean evaluateCondition(String value, String operator, String conditionValue) {
        switch (operator) {
            case "=":
                return value.equals(conditionValue);
            case "!=":
                return !value.equals(conditionValue);
            case ">":
                try {
                    return Integer.parseInt(value) > Integer.parseInt(conditionValue);
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }
	
}
