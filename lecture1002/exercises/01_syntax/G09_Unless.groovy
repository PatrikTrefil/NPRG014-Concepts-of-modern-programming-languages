//TASK Define the unless (aka if not) method
def unless(Boolean condition, Closure action) {
    if(!condition) {
        action.call()
    }
}

unless(1 > 5) {
    println "Condition not satisfied!"
    def value = 10
    println "Value is $value"
}

println 'done'