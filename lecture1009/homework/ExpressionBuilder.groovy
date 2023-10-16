// 2023/2024
// TASK The MarkupBuilder in Groovy can transform a hierarchy of method calls and nested closures into a valid XML document.
// Create a NumericExpressionBuilder builder that will read a user-specified hierarchy of simple math expressions and build a tree representation of it.
// The basic arithmetics operations as well as the power (aka '^') operation must be supported.
// It will feature a toString() method that will pretty-print the expression tree into a string with the same semantics, as verified by the assert on the last line.
// This means that parentheses must be placed where necessary with respect to the mathematical operator priorities.
// Change or add to the code in the script. Reuse the infrastructure code at the bottom of the script.
class NumericExpressionBuilder extends BuilderSupport {
    protected Object createNode(Object nodeName) {
        createNode nodeName, null, null
    }

    protected Object createNode(Object nodeName, Object value) {
        createNode nodeName, null, value
    }

    protected Object createNode(Object nodeName, Map attrs) {
        createNode nodeName, attrs, null
    }

    protected Object createNode(Object nodeName, Map attrs, Object value) {
        if (nodeName instanceof String) {
            nodeName = nodeName as String
            if (nodeName as String in Operator.operatorPriorities.keySet()) {
                return new Operator(nodeName)
            } else if (nodeName == "number") {
                return new Literal(attrs.value as Integer)
            } else if (nodeName == "variable") {
                return new Variable(attrs.value as String)
            }
        }
        throw new Exception("Unexpected nodeName $nodeName");
    }

    protected void setParent(Object parent, Object child) {
        switch (parent.getClass()) {
            case Operator:
                parent = parent as Operator
                if (parent.left == null) {
                    parent.left = child as Item
                } else {
                    parent.right = child as Item
                }
                break
            default:
                throw new Exception("Unknown parent type: ${parent.getClass()}");
        }
    }
}

abstract class Item {
    abstract String toString();
}

class Literal extends Item {
    Integer value;

    Literal(Integer value) {
        this.value = value;
    }

    @Override
    String toString() {
        return value;
    }
}

class Variable extends Item {
    String value;

    Variable(String value) {
        this.value = value;
    }

    @Override
    String toString() {
        return value;
    }
}

class Operator extends Item {
    public static operatorPriorities = ['+': 0, '-': 0, '*': 1, '/': 1, '^': 1, 'power': 1]
    String operator;
    Item left;
    Item right;

    Operator(String operator) {
        if (operator == "power") operator = "^"
        this.operator = operator;
    }

    @Override
    String toString() {
        def str = ""
        if (
            left instanceof Operator &&
            operatorPriorities[(left as Operator).operator] < operatorPriorities[operator]
        )
            str += "($left)"
        else
            str += "$left"
        str += " $operator "
        if (
            right instanceof Operator &&
            operatorPriorities[(right as Operator).operator] < operatorPriorities[operator]
        )
            str += "($right)"
        else
            str += "$right"
        return str
    }
}
//------------------------- Do not modify beyond this point!

def build(builder, String specification) {
    def binding = new Binding()
    binding['builder'] = builder
    // HACK: modified this code
    return new GroovyShell(binding).evaluate(specification)
//    return builder
}

//Custom expression to display. It should be eventually pretty-printed as 10 + x * (2 - 3) / 8 ^ (9 - 5)
String description = '''
builder.'+' {
    number(value: 10)
    '*' {
        variable(value: 'x')
        '/' {
            '-' {
                number(value: 2)
                number(value: 3)
            }
            power {
                number(value: 8)
                '-' {
                    number(value: 9)
                    number(value: 5)
                }
            }
        }
    }
}
'''

//XML builder building an XML document
def xml = build(new groovy.xml.MarkupBuilder(), description)
println xml.toString()

//NumericExpressionBuilder displaying the expression
def expression = build(new NumericExpressionBuilder(), description)
println (expression.toString())
assert '10 + x * (2 - 3) / 8 ^ (9 - 5)' == expression.toString()