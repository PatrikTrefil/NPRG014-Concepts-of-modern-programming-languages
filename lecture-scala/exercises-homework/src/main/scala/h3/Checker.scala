package h3

import scala.collection.mutable.ListBuffer

abstract class Event

case class Command(cmdName: String) extends Event

case class Succeed(cmdName: String) extends Event

case class Fail(cmdName: String) extends Event

class Property(val name: String, val func: () => Boolean)

class Monitor[T]:
  val properties = ListBuffer.empty[Property]

  def property(propName: String)(formula: => Boolean): Unit =
    properties += Property(propName, () => formula)

  var eventsToBeProcessed = List[T]()

  def check(events: List[T]) =
    for prop <- properties do
      eventsToBeProcessed = events

      val result = prop.func()

      println("Property \"" + prop.name + "\" ... " + (if (result) "OK" else "FAILED"))


  def require(func: PartialFunction[T, Boolean]): Boolean =
    while (eventsToBeProcessed.nonEmpty) {
      // pop first event from the list
      val event :: restOfEvents = eventsToBeProcessed
      eventsToBeProcessed = restOfEvents
      // if there is a partial function defined for the event, then call it
      if func.isDefinedAt(event) then
        return func(event)
    }
    // if there was no match for any event, the requirement is considered unfulfilled
    false


/* Add body here
 *
 * to know whether a partial function is defined for a given event,
 * use func.isDefinedAt(event).
 */


class MyMonitor extends Monitor[Event] :
  property("The first command should succeed or fail before it is received again") {
    require {
      case Command(c) =>
        require {
          case Succeed(`c`) => true
          case Fail(`c`) => true
          case Command(`c`) => false
        }
    }
  }

  property("The first command should not get two results") {
    require {
      case Succeed(c) =>
        require {
          case Succeed(`c`) => false
          case Fail(`c`) => false
          case Command(`c`) => true
        }
      case Fail(c) =>
        require {
          case Succeed(`c`) => false
          case Fail(`c`) => false
          case Command(`c`) => true
        }
    }
  }

  property("The first command should succeed") {
    /* Add a property definition here which requires that the first command does not fail.
     * It should yield OK with the events listed in the main method.
     */
    require {
      case Command(c) =>
        require {
          case Succeed(`c`) => true
        }
    }
  }


object Checker:

  def main(args: Array[String]): Unit =
    val events = List(
      Command("take_picture"),
      Command("get_position"),
      Succeed("take_picture"),
      Fail("take_picture")
    )

    val monitor = new MyMonitor
    monitor.check(events)