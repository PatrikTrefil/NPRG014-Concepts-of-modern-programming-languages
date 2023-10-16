class MyIndentingBuilder {

    def indentSize = 4
    def indent = indentSize

    def invokeMethod(String methodName, args) {
        indent += indentSize
        def result = '';
        if (args.size() > 0) {
            Closure closure = args[0]
            closure.delegate = this
            result = closure()
        }
        indent -= indentSize
        return "<$methodName>\n${' ' * indent}$result\n${' ' * (indent - indentSize)}</$methodName>"
    }
}

//TASK manipulate the value in "indent" so as the generated xml is nicely indented

def doc = new MyIndentingBuilder().html {
    body {
        div {
            "content"
        }
    }
}

println doc