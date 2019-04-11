object ExpressionTester {

  def main(args: Array[String]): Unit = {
    val aInt = IntValue(3)
    val aFloat = DoubleValue(0.5)

    val zeroFloat = DoubleValue(0.0)
    val zero = IntValue(0)
    val one = IntValue(1)



  }

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

  case class BinExp(op: BinOp, value1: Number, value2: Number) extends NumExp {
    val value = op.apply(value1,value2)
    override def getNumber(): Number = value
    override def toString() = value.toString
  }
  case class UnExp(op: UnOp, value1: Number) extends NumExp {
    val value = op.apply(value1)
    override def getNumber(): Number = value
    override def toString() = value.toString
  }
  case class CompExp(op: Comp, value1: Number, value2: Number) extends Expression {
    val value = op.apply(value1,value2)
    def getBool(): Bool= value
    override def toString() = value.toString
  }

  val add : BinOp = {
    case (IntValue(value1),IntValue(value2)) => IntValue(value1 + value2)
    case (DoubleValue(value1),IntValue(value2)) => DoubleValue(value1 + value2)
    case (IntValue(value1),DoubleValue(value2)) => DoubleValue(value1 + value2)
    case (DoubleValue(value1),DoubleValue(value2)) => DoubleValue(value1 + value2)
  }
  val subtract : BinOp = {
    case (IntValue(value1),IntValue(value2)) => IntValue(value1 - value2)
    case (DoubleValue(value1),IntValue(value2)) => DoubleValue(value1 - value2)
    case (IntValue(value1),DoubleValue(value2)) => DoubleValue(value1 - value2)
    case (DoubleValue(value1),DoubleValue(value2)) => DoubleValue(value1 - value2)
  }
  val multiply : BinOp = {
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
  val negate : UnOp = {
    case (IntValue(value)) => IntValue(value * -1)
    case (DoubleValue(value)) => DoubleValue(value * -1)
  }
 val equal : Comp = {
    case (IntValue(value1),IntValue(value2)) => Bool(value1 == value2)
    case (DoubleValue(value1),IntValue(value2)) => Bool(value1 == value2)
    case (IntValue(value1),DoubleValue(value2)) => Bool(value1 == value2)
    case (DoubleValue(value1),DoubleValue(value2)) => Bool(value1 == value2)
  }

  val notEqual : Comp = {
    case (IntValue(value1),IntValue(value2)) => Bool(value1 != value2)
    case (DoubleValue(value1),IntValue(value2)) => Bool(value1 != value2)
    case (IntValue(value1),DoubleValue(value2)) => Bool(value1 != value2)
    case (DoubleValue(value1),DoubleValue(value2)) => Bool(value1 != value2)
  }



}
