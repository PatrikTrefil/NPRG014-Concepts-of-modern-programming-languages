assert 270 == (10..20).findAll{it%2==0}
        .collect {3*it}
        .inject(0){acc, v -> acc + v}
        
        
assert 5050 == (1..100).inject(0) {int acc, int v -> acc + v}
assert 5050 == (1..100).sum()

//TASK make all words uppercase
assert ['GROOVY', 'JAVA', 'SCALA', 'KOTLIN'] == ['Groovy', 'Java', 'scala', 'kotlin']*.toUpperCase()
assert ['GROOVY', 'JAVA', 'SCALA', 'KOTLIN'] == ['Groovy', 'Java', 'scala', 'kotlin'].collect{it.toUpperCase()}

//TASK concatenate all characters between 'a' and 'z'
assert 'abcdefghijklmnopqrstuvwxyz' == ('a'..'z').collect{it}.join()
assert 'abcdefghijklmnopqrstuvwxyz' == ('a'..'z').inject('') {acc, v -> acc + v}

//TASK assert that there are 26 characters between 'a' and 'z'
assert 26 == ('a'..'z').size()

//TASK Find the maximum of the following collection using the inject() function
assert 26 == [1, 2, 3, 4, 3, 4, 19, 26, 25, 19, 22, 25, 3, 10, 12, 23, 24, 12, 11, 18].inject(0) {acc, v -> acc > v ? acc : v}

println 'ok'