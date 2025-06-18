
package itri.sstc.freemarker.binding;
 
import java.util.List;
public class BindStatus {
    private final String value;
    private final String expression;
    private final List<String> errorMessages;

    public BindStatus(String value, String expression, List<String> errorMessages) {
        this.value = value;
        this.expression = expression;
        this.errorMessages = errorMessages;
    }

    public String getValue() {
        return value;
    }

    public String getExpression() {
        return expression;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}



