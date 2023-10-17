import groovyjarjarpicocli.CommandLine

@groovy.transform.TailRecursive
def fact(BigDecimal n, BigDecimal res) {
    if (n < 2) res
    else fact(n - 1, n*res)
}

def fact(BigDecimal n) {
    fact(n, 1)
}
println fact(5)

//TASK Make the function tail recursive so that it can pass the following line
println fact(10000)