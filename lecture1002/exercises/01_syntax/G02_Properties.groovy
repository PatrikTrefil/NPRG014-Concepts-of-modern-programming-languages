class City {
    String name
    int size
    boolean capital = false
    public String toString() {
        return "${capital ? 'Capital city of' : 'City of'} ${name}, population: ${size}"
    }
    
    static def create(String n, int v, boolean e = false) {
        return new City(name: n, size: v, capital: e)
    }
}

println City.create("Brno", 400000).dump()
def praha = City.create("Praha", 1300000, true).dump()
println praha

City pisek = new City(name: 'Písek', size: 25000, capital: false)
City tabor = new City(size: 35000, capital: false, name: 'Tábor')

println pisek.dump()
pisek.size = 25001
println pisek.dump()

println tabor
//TASK Provide a customized toString() method to print the name and the population
assert 'City of Písek, population: 25001' == pisek.toString()
City praha = new City(name: 'Praha', size: 1300000, capital: true)
assert 'Capital city of Praha, population: 1300000' == praha.toString()