/*
Implement the classes below such that the main (without modifications) prints out the something like this:

Person John Doe aged 24
Person John Doe aged 25
List(h2.PersonState@3d24753a)
Person John Doe aged 24
Thing Box with color (255,0,0)
Person Joe aged 24

*/

package h2

import scala.collection.mutable.ListBuffer


trait WithExplicitState:
  type TState
  protected def state: TState
  protected def state_=(state: TState): Unit


class PersonState(val name: String, val age: Int)

class Person extends WithExplicitState:
  /* Implement this class. It should have no knowledge of the trait History. It should use instances of PersonState as the state. */
  override type TState = PersonState
  override protected var state = new PersonState("", 0)

  def setName(name: String): this.type = {
    this.state = new PersonState(name, this.state.age)
    this
  }

  def setAge(age: Int): this.type = {
    this.state = new PersonState(this.state.name, age)
    this
  }



type RGBColor = (Int, Int, Int)
class ThingState(val name: String, val color: RGBColor)

class Thing extends WithExplicitState:
  /* Implement this class. It should have no knowledge of the trait History. It should use instances of ThingState as the state. */
  override type TState = ThingState
  override protected var state = new ThingState("", (0, 0, 0))

  def setName(name: String): this.type = {
    this.state = new ThingState(name, this.state.color)
    this
  }

  def setColor(color: RGBColor): this.type = {
    this.state = new ThingState(this.state.name, color)
    this
  }


trait History:
    /* Add necessary declarations here. This trait should have no knowledge of classes Person, Thing, PersonState, ThingState.
       It should depend only on the trait WithExplicitState.
    */
    this: WithExplicitState =>

    val hist: ListBuffer[this.TState] = ListBuffer.empty[this.TState]

    def checkpoint(): this.type =
      hist.append(state)
      this

    def history = hist.toList

    def restoreTo(s: this.TState): this.type =
      state = s
      this


object ExplicitStateTest:
  def main(args: Array[String]): Unit =
    // The inferred type of variable "john" should be "Person & History".
    val john = (new Person with History).setName("John Doe").setAge(24).checkpoint()

    println(john)
    john.setAge(25)

    println(john)
    println(john.history)

    val johnsPrevState = john.history(0)
    john.restoreTo(johnsPrevState)
    println(john)

    // The inferred type of variable "box" should be "Thing & History".
    val box = new Thing with History
    box.setName("Box")
    box.setColor((255, 0, 0))
    println(box)

    val joe = new Person with History
    joe.restoreTo(johnsPrevState).setName("Joe")
    println(joe)

    // The line below must not compile. It should complain about an incompatible type.
    // box.restoreTo(johnsPrevState)