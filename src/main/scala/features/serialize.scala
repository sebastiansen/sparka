package features

import java.io._

// create a serializable Stock class
@SerialVersionUID(123L)
class Stock(var symbol: String, var price: BigDecimal)
  extends Serializable {
  override def toString = f"$symbol%s is ${price.toDouble}%.2f"
}

object SerializationDemo extends App {

  // (1) create a Stock instance
  val nflx = new Stock("NFLX", BigDecimal(85.00))

  // (2) write the instance out to a file
  val oos = new ObjectOutputStream(new FileOutputStream("/tmp/nflx"))
  oos.writeObject(nflx)
  oos.close

  // (3) read the object back in
  val ois = new ObjectInputStream(new FileInputStream("/tmp/nflx"))

  val s = ois.readObject().asInstanceOf[Stock]

  println(s)
}