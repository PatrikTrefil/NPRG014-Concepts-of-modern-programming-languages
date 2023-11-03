package h1
import scala.language.implicitConversions

// Add necessary class and object definitions in order to make the statements in the main work.

/**
 * Complex number class
 * @param r - real part
 * @param i - imaginary part
 */
class Complex(r: Int, i: Int):
	/**
	 * Real part
	 */
	val re = r
	/**
	 * Imaginary part
	 */
	val im = i
	override def toString = s"$re${if (im < 0) "" else "+"}${im}i"
	def +(c: Complex) = Complex(re + c.re, im + c.im)
	def +(n: Int): Complex = Complex(re + n, im)
	def *(c: Complex): Complex = Complex(re * c.re - im * c.im, re * c.im + im * c.re)
	def *(dummy: I.type): Complex = this * Complex(0, 1) // Replace I symbol with Complex(0, 1) and multiply as normal
	def unary_- = Complex(-r, -i)

/**
 * Object used to turn Int into Complex.
 */
object I:
	def *(i: Int) = Complex(0, i)

extension (i: Int)
	def *(dummy: I.type) = Complex(0, i)
	def +(c: Complex) = Complex(i + c.re, c.im)

object ComplexNumbers:
	def main(args: Array[String]): Unit =
		println(Complex(1,2)) // 1+2i

		println(1 + 2*I + I*3 + 2) // 3+5i

		val c = (2+3*I + 1 + 4*I) * I
		println(-c) // 7-3i