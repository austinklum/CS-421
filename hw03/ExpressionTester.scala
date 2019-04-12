object ExpressionTester {

  def main(args: Array[String]): Unit = {
    val aInt = IntValue(3)
    val aFloat = DoubleValue(0.5)

    val zeroFloat = DoubleValue(0.0)
    val zero = IntValue(0)
    val one = IntValue(1)

//    val exp1 = UnExp(neg, BinExp(add, aInt, aFloat))
//    val exp2 = BinExp(times, aInt, aFloat)
//    val exp3 = CompExp(eq, exp1, exp2)
//    val exp4 = CompExp(eq, exp1, exp1)
//    val exp5 = CompExp(ne, exp1, exp2)
//    val exp6 = BinExp(divide, BinExp(minus, one, one), one)
    val exp1 = BinExp(times, BinExp(add, IntValue(4), IntValue(3)), BinExp(minus, IntValue(2), BinExp(divide, IntValue(6), IntValue(2))))
    val exp2 = BinExp(times, DoubleValue(2.0), BinExp(minus, IntValue(3), BinExp(divide, IntValue(4), DoubleValue(2.0))))
    val exp3 = BinExp(minus, IntValue(9), BinExp(minus, IntValue(6), BinExp(times, DoubleValue(5.0), UnExp(neg, IntValue(4)))))
    val exp4 = BinExp(times, UnExp(neg, IntValue(7)), BinExp(times, IntValue(4), BinExp(minus, IntValue(3), BinExp(times, DoubleValue(4.0), DoubleValue(2.0)))))
    val exp5 = CompExp(ne, BinExp(divide, IntValue(3), IntValue(2)), BinExp(divide, IntValue(3), DoubleValue(2.0)))
    val exp6 = CompExp(eq, BinExp(divide, DoubleValue(3.0), IntValue(2)), BinExp(divide, IntValue(3), DoubleValue(2.0)))
    val exp7 = CompExp(eq, BinExp(divide, BinExp(minus, one, one), one), zero)
    val exp8 = CompExp(eq, BinExp(divide, BinExp(minus, one, one), one), zeroFloat)

   println("* + 4 3 - 2 / 6 2 => " + evaluate(exp1))
   println("* 2.0 - 3 / 4 2.0 => " + evaluate(exp2))
   println("- 9 - 6 - * 5.0 4 => " + evaluate(exp3))
   println("- * 7 * 4 - 3 * 4.0 2.0 => " + evaluate(exp4))
   println("/ 3 2 != / 3 2.0 => " + evaluate(exp5))
   println("/ 3.0 2 == / 3 2.0 => " + evaluate(exp6))
   println("/ - 1 1 1 == 0 => " + evaluate(exp7))
   println("/ - 1 1 1 == 0.0 => " + evaluate(exp8))

  }

  def evaluate(exp: Expression) = exp.toString

  trait Expression
  trait NumExp extends Expression {
    def getNumber() : Number
  }
  trait Number extends NumExp {
    def getValue()
  }

  case class IntValue(value: Int) extends Number {
    override def getValue() = value
    override def getNumber(): Number = IntValue(value)
    override def toString: String = "IntValue(" + value + ")"
  }

  case class DoubleValue(value: Double) extends Number {
    override def getValue() = value
    override def getNumber(): Number = DoubleValue(value)
    override def toString: String = "DoubleValue(" + value + ")"
  }
  case class Bool(value : Boolean) {
    def getValue() = value
    override def toString: String = "Bool(" + value + ")"
  }

  type IntOp = Int => Int
  type DoubleOp = Double => Double
  type BinOp = (Number,Number) => Number
  type UnOp = Number => Number
  type Comp = (Number,Number) => Bool

  case class BinExp(op: BinOp, value1: NumExp, value2: NumExp) extends NumExp {
    val value = op.apply(value1.getNumber(),value2.getNumber())
    override def getNumber(): Number = value
    override def toString() = value.toString
  }
  case class UnExp(op: UnOp, value1: NumExp) extends NumExp {
    val value = op.apply(value1.getNumber())
    override def getNumber(): Number = value
    override def toString() = value.toString
  }
  case class CompExp(op: Comp, value1: NumExp, value2: NumExp) extends Expression {
    val value = op.apply(value1.getNumber(),value2.getNumber())
    def getBool(): Bool= value
    override def toString() = value.toString
  }

  val add : BinOp = {
    case (IntValue(value1),IntValue(value2)) => IntValue(value1 + value2)
    case (DoubleValue(value1),IntValue(value2)) => DoubleValue(value1 + value2)
    case (IntValue(value1),DoubleValue(value2)) => DoubleValue(value1 + value2)
    case (DoubleValue(value1),DoubleValue(value2)) => DoubleValue(value1 + value2)
  }
  val minus : BinOp = {
    case (IntValue(value1),IntValue(value2)) => IntValue(value1 - value2)
    case (DoubleValue(value1),IntValue(value2)) => DoubleValue(value1 - value2)
    case (IntValue(value1),DoubleValue(value2)) => DoubleValue(value1 - value2)
    case (DoubleValue(value1),DoubleValue(value2)) => DoubleValue(value1 - value2)
  }
  val times : BinOp = {
    case (IntValue(value1),IntValue(value2)) => IntValue(value1 * value2)
    case (DoubleValue(value1),IntValue(value2)) => DoubleValue(value1 * value2)
    case (IntValue(value1),DoubleValue(value2)) => DoubleValue(value1 * value2)
    case (DoubleValue(value1),DoubleValue(value2)) => DoubleValue(value1 * value2)
  }
  val divide : BinOp = {
    case (IntValue(value1),IntValue(value2)) => IntValue(value1 / value2)
    case (DoubleValue(value1),IntValue(value2)) => DoubleValue(value1 / value2)
    case (IntValue(value1),DoubleValue(value2)) => DoubleValue(value1 / value2)
    case (DoubleValue(value1),DoubleValue(value2)) => DoubleValue(value1 / value2)
  }

  //negate
  val neg : UnOp = {
    case (IntValue(value)) => IntValue(value * -1)
    case (DoubleValue(value)) => DoubleValue(value * -1)
  }

  //equal
 val eq : Comp = {
    case (IntValue(value1),IntValue(value2)) => Bool(value1 == value2)
    case (DoubleValue(value1),IntValue(value2)) => Bool(value1 == value2)
    case (IntValue(value1),DoubleValue(value2)) => Bool(value1 == value2)
    case (DoubleValue(value1),DoubleValue(value2)) => Bool(value1 == value2)
  }

  //Not Equal
  val ne : Comp = {
    case (IntValue(value1),IntValue(value2)) => Bool(value1 != value2)
    case (DoubleValue(value1),IntValue(value2)) => Bool(value1 != value2)
    case (IntValue(value1),DoubleValue(value2)) => Bool(value1 != value2)
    case (DoubleValue(value1),DoubleValue(value2)) => Bool(value1 != value2)
  }



}
