def powerRec(x: Int, y: Int):Int = {
  def powerRecHelp(pow: Int, count : Int): Int = {
    if (count < 0) {
      pow
    } else {
      powerRecHelp(pow * x, count - 1)
    }
  }
  powerRecHelp(x,y)
}