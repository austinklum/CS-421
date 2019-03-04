/**
  * Starter/test code for homework 02: CS 421/521, Spring 2019.
  *
  * Small collection of basic Scala functions.
  *
  * Author:  M. Allen
  */

object HW02 {

  def main(args: Array[String]): Unit = {

    /**
      * 1.a. A simple power function:  takes two values, x and y, and returns x to the yth power
      * (This should be written iteratively, using some var and loop)
      */
    def power(x: Int, y: Int) = {
      var pow = 1
      for (i <- 1 to y if y > 0) {
        pow *= x
      }
      pow
    }


   /* println("\n***************\nTesting the iterative power function\n***************\n")
    println(power(1, 3)) //> 1
    println(power(1, 4)) //> 1
    println(power(-1, 3)) //> -1
    println(power(-1, 4)) //> 1
    println(power(2, -1)) //> 1
    println(power(2, 0)) //> 1
    println(power(2, 3)) //> 8*/


    /**
      * 1.b. A simple power function:  takes two values, x and y, and returns x to the yth power
      * (This should be written recursively, without any var or loop)
      */
      def powerRec(x: Int, y: Int):Int = {
        if(y < 1) {
          1
        }else {
          x * powerRec(x, y - 1)
        }
      }
/*
      println("\n***************\nTesting the recursive power function\n***************\n")
      println(powerRec(1, 3)) //> 1
      println(powerRec(1, 4)) //> 1
      println(powerRec(-1, 3)) //> -1
      println(powerRec(-1, 4)) //> 1
      println(powerRec(2, -1)) //> 1
      println(powerRec(2, 0)) //> 1
      println(powerRec(2, 3)) //> 8*/


    /**
      * 2.a. A simple range-sum function:  for integers x and y, computes the sum of all
      * values between the two, inclusively.  If (x == y), this will be x.
      * (This should be written iteratively, using var and loop.)
      */
    def rangeSum(x : Int, y : Int) = {
      var sum = 0
      val start = if (x < y) x else y
      val end = if (x < y) y else x

      for ( i <- start to end) {
        sum += i
      }
      sum
    }
/*      println("\n***************\nTesting the range-sum function\n***************\n")
      println(rangeSum(-1, -1)) //> -1
      println(rangeSum(1, 1)) //> 1
      println(rangeSum(-1, 1)) //> 0
      println(rangeSum(1, -1)) //> 0
      println(rangeSum(1, 5)) //> 15
      println(rangeSum(5, 1)) //> 15
      println(rangeSum(-1, -5)) //> -15
      println(rangeSum(1, -5)) //> -14*/


    /**
      * 2.b. A simple range-sum function:  for integers x and y, computes the sum of all
      * * values between the two, inclusively.  If (x == y), this will be x.
      * (This should be written recursively, without var or loop.)
      */
    def rangeSumRec(x : Int, y : Int):Int = {
     if (x < y ) {
        x + rangeSumRec(x + 1,y)
      } else if (x > y) {
        y + rangeSumRec(x, y + 1)
      } else {
       x
     }
    }
/*      println("\n***************\nTesting the recursive range-sum function\n***************\n")
      println(rangeSumRec(-1, -1)) //> -1
      println(rangeSumRec(1, 1)) //> 1
      println(rangeSumRec(-1, 1)) //> 0
      println(rangeSumRec(1, -1)) //> 0
      println(rangeSumRec(1, 5)) //> 15
      println(rangeSumRec(5, 1)) //> 15
      println(rangeSumRec(-1, -5)) //> -15
      println(rangeSumRec(1, -5)) //> -14*/


    /**
      * 3.a. List product method:  given input list of integers, returns product of all elements.
      * Will return 0 for empty lists.
      * (This should be written iteratively, using var and loop.)
      */
     def product(list : List[Int]) = {
       var total = if(list.nonEmpty) 1 else 0
      for(item <- list) {
        total *= item
      }
       total
     }

      val ls0: List[Int] = List() //> ls0  : List[Int] = List()
      val ls1 = List(0, 1, 2, 3, 4) //> ls1  : List[Int] = List(0, 1, 2, 3, 4)
      val ls2 = List(1, 2, 3, 4, 5) //> ls2  : List[Int] = List(1, 2, 3, 4, 5)
      val ls3 = List(1, -2, 3, 4, 5) //> ls3  : List[Int] = List(1, -2, 3, 4, 5)

    /*
      println("\n***************\nTesting the iterative list-product function\n***************\n")
      println(product(ls0)) //> 0
      println(product(ls1)) //> 0
      println(product(ls2)) //> 120
      println(product(ls3)) //> -120*/


    /**
      * 3.b. List product method:  given input list of integers, returns product of all elements.
      * Will return 0 for empty lists.
      * (This should be written recursively, without var or loop.)
      */
    def productRec(list: List[Int]): Int = {
      def productRecHelp(list :List[Int]) : Int = list match {
        case item::tail => item * productRecHelp(tail)
        case Nil => 1
      }
      if (list == Nil){
        0
      } else {
        productRecHelp(list)
      }
    }
   /*   println("\n***************\nTesting the recursive list-product function\n***************\n")
      println(productRec(ls0)) //> 0
      println(productRec(ls1)) //> 0
      println(productRec(ls2)) //> 120
      println(productRec(ls3)) //> -120
*/

    /**
      * 4.a.  "Greater-than-or-equal-to-list" function:  given input list of integers, and integer x,
      * returns true iff x >= list element i, for all values of i.
      * This will ALWAYS return true on an empty list.
      * (This should be written iteratively, using var and loop.)
      */
    def geqList(x : Int, list: List[Int]) = {
      var isGreater = true
      for(item <- list if x < item) {
          isGreater = false;
      }
      isGreater
    }
/*      println("\n***************\nTesting the iterative geq-List function\n***************\n")
      println(geqList(4, ls0)) //> true
      println(geqList(4, ls1)) //> true
      println(geqList(4, ls2)) //> false
      println(geqList(4, ls3)) //> false*/


    /**
      * 4.b.  "Greater-than-or-equal-to-list" function:  given input list of integers, and integer x,
      * returns true iff x >= list element i, for all values of i.
      * This will ALWAYS return true on an empty list.
      * (This should be written recursively, without var and loop.)
      */
    def geqListRec(x: Int, list: List[Int]) : Boolean = list match{
      case head::tail => if (x >= head) geqListRec(x,tail) else false
      case Nil => true
    }

    /*  println("\n***************\nTesting the recursive geq-List function\n***************\n")
      println(geqListRec(4, ls0)) //> true
      println(geqListRec(4, ls1)) //> true
      println(geqListRec(4, ls2)) //> false
      println(geqListRec(4, ls3)) //> false
*/
    /**
      * Simple example of functional input. Un-comment to see how it works.
      */


      def apply(fun: Int => Int, x: Int) = if (x < 0) 0 else fun(x)

      def plus1(x: Int) = x + 1

     /* println("\n***************\nExample of function application\n***************\n")
      println(plus1(-1)) //> 0
      println(plus1(0)) //> 1
      println(plus1(2)) //> 3*/


    /**
      * 5. geqListRec modified to take in extra functional input and check
      * that the function returns true on every application to pair ( x, list-element ).
      */
    def functionListRec(x: Int, list: List[Int],fun: (Int, Int) => Boolean) : Boolean = list match{
      case head::tail => if (x >= head && fun(x,head)) functionListRec(x,tail,fun) else false
      case Nil => true
    }
/*      println("\n***************\nTesting functionListRec \n***************\n")
      def falsefun(x: Int, y: Int) = false //> falsefun: (x: Int, y: Int)Boolean
      def truefun(x: Int, y: Int) = true //> truefun: (x: Int, y: Int)Boolean

      println(functionListRec(4, ls0, truefun)) //> true
      println(functionListRec(4, ls0, falsefun)) //> true
      println(functionListRec(4, ls1, _ >= _)) //> true
      println(functionListRec(4, ls1, _ > _)) //> false
      println(functionListRec(4, ls1, _ != _)) //> false
      println(functionListRec(10, ls1, _ != _)) //> true*/


    /**
      * 6.  Function that takes an input list of Any type, and an object of Any type, and
      * returns the number of times that object occurs in the list.
      * If the list is empty, this value should be 0.
      * (Should be written using match and recursion, and basic Scala control structures.)
      */
      def countIn(list: List[Any], obj: Any):Int = {
        def countInHelp(listHelp: List[Any], count: Int): Int = listHelp match {
          case head::tail => if(head == obj) countInHelp(tail,count + 1) else countInHelp(tail,count)
          case Nil => count
        }
        countInHelp(list,0)
      }

/*      println("\n***************\nTesting the contains function \n***************\n")
      val lsa = List(1.0, 2.0, 3.0, 4.0)
      val lsb = List(1, 2, 3, 4)
      val lsc = List(1, 0, 1, 0, 0, 0, 1, 1)

      println(countIn(List(), 1)) //>0
      println(countIn(lsa, 1.0)) //> 1
      println(countIn(lsa, 1)) //> 1
      println(countIn(lsb, 1)) //> 1
      println(countIn(lsb, 1.0)) //> 1
      println(countIn(lsb, "1")) //> 0
      println(countIn(lsc, 1)) //> 4
      println(countIn(lsc, 0)) //> 4
      println(countIn(lsc, List(0))) //> 0*/



    /**
      * 7.  Function that takes an input list of pairs of integers (x, y) and returns the
      * list of integers formed by summing the pairs (x + y).
      * If the list is empty, the empty list should be returned.
      * (Should be written using match and recursion, and basic Scala control structures.)
      */
      def pairsum(pairs: List[(Int, Int)]): List[Int] = pairs match {
        case head::tail => head._1 + head._2 :: pairsum(tail)
        case Nil => List()
      }
    /*  println("\n***************\nTesting the pairsum function \n***************\n")
      val lsx = (1 to 5).toList //> lsx  : List[Int] = List(1, 2, 3, 4, 5)
      val lsy = (-5 to -1).toList.reverse //> lsy  : List[Int] = List(-1, -2, -3, -4, -5)
      val lsz = lsx zip lsx //> lsz  : List[(Int, Int)] = List((1,1), (2,2), (3,3), (4,4), (5,5))
      val lsw = lsx zip lsy //> lsw  : List[(Int, Int)] = List((1,-1), (2,-2), (3,-3), (4,-4), (5,-5))

      println(pairsum(lsz)) //> List(2, 4, 6, 8, 10)
      println(pairsum(lsw)) //> List(0, 0, 0, 0, 0)*/


    /**
      * 8.  Function that takes an input list of pairs of elements (e1, e2) and item x returns the
      * list of pairs formed by deleting any pair where x matches EITHER e1 or e2.
      * If the list is empty, the empty list should be returned.
      * (Should be written using match and recursion, and basic Scala control structures.)
      */
    def pairdel(pairs: List[(Any, Any)], x : Any): List[(Any,Any)] = pairs match {
      case head::tail => if(head._1 != x && head._2 != x) head::pairdel(tail,x) else pairdel(tail,x)
      case Nil => List()
    }
     // println("\n***************\nTesting the pairdel function \n***************\n")
      val ls4 = (1 to 5).toList //> ls4  : List[Int] = List(1, 2, 3, 4, 5)
      val ls5 = ls4.reverse //> ls5  : List[Int] = List(5, 4, 3, 2, 1)
      val ls6 = ls4 zip ls5 //> ls6  : List[(Int, Int)] = List((1,5), (2,4), (3,3), (4,2), (5,1))
      val ls7 = ('a' to 'e').toList //> ls7  : List[Char] = List(a, b, c, d, e)
      val ls8 = ls7 zip ls4 //> ls8  : List[(Char, Int)] = List((a,1), (b,2), (c,3), (d,4), (e,5))
      val ls9 = ls4 zip ls7 //> ls9  : List[(Int, Char)] = List((1,a), (2,b), (3,c), (4,d), (5,e))

//      println(pairdel(ls6, 2)) //> List((1,5), (3,3), (5,1))
//      println(pairdel(ls8, 'c')) //> List((a,1), (b,2), (d,4), (e,5))
//      println(pairdel(ls9, 4)) //> List((1,a), (2,b), (3,c), (5,e))*/


    /**
      * 9.  Function that takes an input list of elements of Any type and indexes start and end,
      * and returns the list of elements from positions start to (end - 1).
      * If the list is empty, the empty list should be returned.
      * If start >= end, the empty list should be returned.
      * If end > list.length, then return every element from start to the end of list.
      * (Should be written using match and recursion, and basic Scala control structures.
      * You must not use library methods from the List class, or any element indexing.)
      */
    def sublist(list: List[Any], start : Int, end : Int): List[Any] = {
      def sublistHelp(listHelp: List[Any],index : Int) : List[Any] = listHelp match {
        case head::tail => if(start <= index && index < end) head::sublistHelp(tail,index+1) else sublistHelp(tail,index+1)
        case Nil => List()
      }
      sublistHelp(list,0)
    }
  /*    println("\n***************\nTesting the sublist function \n***************\n")
      println(sublist(ls0, 2, 4)) //> List()
      println(sublist(ls1, 2, 1)) //> List()
      println(sublist(ls1, 2, 2)) //> List()
      println(sublist(ls1, 2, 3)) //> List(2)
      println(sublist(ls1, 2, 4)) //> List(2, 3)
      println(sublist(ls1, 2, 5)) //> List(2, 3, 4)
      println(sublist(ls1, 2, 10)) //> List(2, 3, 4)
      println(sublist(ls9, 1, 3)) //> List((2,b), (3,c))
      println(sublist(ls9, 3, 100)) //> List((4,d), (5,e))
*/
    /**
      * 10.a.  Function that takes in two lists of integers, and a function on integer pairs.
      * It returns the list of integers that results from applying the function to
      * matching elements of each list in turn.
      * If the lists are of unequal length, it should return a list for the first n pairs,
      * where n is the length of the shortest of the two lists.
      */
    def applyIntLists(list1: List[Int], list2: List[Int], func: (Int, Int) => Int): List[Int] = {
      def applyIntListsHelper(listHelp1: List[Int], listHelp2: List[Int]): List[Int] = (listHelp1,listHelp2) match {
        case (list1Head::list1Tail, list2Head::list2Tail) => func(list1Head,list2Head)::applyIntListsHelper(list1Tail,list2Tail)
        case (Nil,_) => List()
        case (_,Nil) => List()
      }
      applyIntListsHelper(list1,list2)
    }

      println("\n***************\nTesting the applyIntLists function \n***************\n")
      val lsaa: List[Int] = List() //> lsaa  : List[Int] = List()
      val lsbb = (1 to 5).toList //> lsbb  : List[Int] = List(1, 2, 3, 4, 5)
      val lscc = (1 to 10).toList //> lscc  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val lsdd = (-10 to -1).toList.reverse //> lsdd  : List[Int] = List(-1, -2, -3, -4, -5, -6, -7, -8, -9, -10)

//      println(applyIntLists(lsaa, lsbb, _ + _)) //> List()
//      println(applyIntLists(lsbb, lsbb, _ + _)) //> List(2, 4, 6, 8, 10)
//      println(applyIntLists(lsbb, lscc, _ + _)) //> List(2, 4, 6, 8, 10)
//      println(applyIntLists(lscc, lsbb, _ + _)) //> List(2, 4, 6, 8, 10)
//      println(applyIntLists(lscc, lsdd, _ + _)) //> List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//      println(applyIntLists(lscc, lsdd, _ * _)) //> List(-1, -4, -9, -16, -25, -36, -49, -64, -81, -100)



    /**
      * 10.b.  Function that takes in two lists of generic type, and a function on pairs of that type.
      * It returns the list of the same type that results from applying the function to
      * matching elements of each list in turn.
      * If the lists are of unequal length, it should return a list for the first n pairs,
      * where n is the length of the shortest of the two lists.
      */

    /**
      * Simple generic function example. Un-comment to see how it works.
      */
    /*
      def prln[T](x: T) = println(x) //> prln: [T](x: T)Unit

      println("\n***************\nDemo of basic generic function \n***************\n")
      prln[Int](3) //> 3
      prln[Double](3.5) //> 3.5
      prln[String]("3.75") //> 3.75
  */
    def applyLists[T](list1: List[T], list2: List[T], func: (T, T) => T): List[T] = {
      def applyListsHelper(listHelp1: List[T], listHelp2: List[T]): List[T] = (listHelp1,listHelp2) match {
        case (list1Head::list1Tail, list2Head::list2Tail) => func(list1Head,list2Head)::applyListsHelper(list1Tail,list2Tail)
        case (Nil,_) => List()
        case (_,Nil) => List()
      }
      applyListsHelper(list1,list2)
    }
//     println("\n***************\nTesting the applyLists function \n***************\n")
      val lssb = lsbb.map((x: Int) => x.toString) // List("1", "2", "3", "4", "5")
      val lssc = lscc.map((x: Int) => x.toString) // List("1", "2", "3", "4", "5", ..., "10")

   /*   println(applyLists[Int](lsaa, lsbb, _ + _)) //> List()
      println(applyLists[Int](lsbb, lsbb, _ + _)) //> List(2, 4, 6, 8, 10)
      println(applyLists[Int](lsbb, lscc, _ + _)) //> List(2, 4, 6, 8, 10)
      println(applyLists[Int](lscc, lsbb, _ + _)) //> List(2, 4, 6, 8, 10)
      println(applyLists[Int](lscc, lsdd, _ + _)) //> List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
      println(applyLists[Int](lscc, lsdd, _ * _)) //> List(-1, -4, -9, -16, -25, -36, -49, -64, -81, -100)
      println(applyLists[String](lssb, lssb, _ + _)) //> List(11, 22, 33, 44, 55)
      println(applyLists[String](lssc, lssc, _ + _)) //> List(11, 22, 33, 44, 55, 66, 77, 88, 99, 1010)
      println(applyLists[String](lssb, lssc, _ + _)) //> List(11, 22, 33, 44, 55)
      println(applyLists[String](lssc, lssb, _ + _)) //> List(11, 22, 33, 44, 55)*/


    /**
      * 10.c.  Function that takes in two lists of possibly distinct generic types,
      * and a function on pairs of those types (returning a possible third type).
      * It returns the list of the third type that results from applying the function to
      * matching elements of each list in turn.
      * If the lists are of unequal length, it should return a list for the first n pairs,
      * where n is the length of the shortest of the two lists.
      */

    /**
      * Simple two-type generic example. Un-comment to see how it works.
      */
    /*
      def prln2[T, U](x: T, y: U) = println("x: " + x + ", y: " + y) //> prln2: [T, U](x: T, y: U)Unit

      println("\n***************\nDemo of two-type generic function \n***************\n")
      prln2[Int, Int](3, 4) //> x: 3, y: 4
      prln2[Int, Double](3, 4.5) //> x: 3, y: 4.5
      prln2[Double, String](3, "4.75") //> x: 3.0, y: 4.75
  */
    def applyLists2[T,U,V](list1: List[T], list2: List[U], func: (T, U) => V): List[V] = {
      def applyLists2Helper(listHelp1: List[T], listHelp2: List[U]): List[V] = (listHelp1,listHelp2) match {
        case (list1Head::list1Tail, list2Head::list2Tail) => func(list1Head,list2Head)::applyLists2Helper(list1Tail,list2Tail)
        case (Nil,_) => List()
        case (_,Nil) => List()
      }
      applyLists2Helper(list1,list2)
    }
      println("\n***************\nTesting the applyLists function \n***************\n")
      val ls00: List[Int] = List() //> ls00  : List[Int] = List()
      val ls11 = (1 to 5).toList //> ls11  : List[Int] = List(1, 2, 3, 4, 5)
      val ls22 = (1 to 10).toList //> ls22  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val ls33 = List(1.0, 2.0, 3.0, 4.0, 5.0) //> ls33  : List[Double] = List(1.0, 2.0, 3.0, 4.0, 5.0)

      println(applyLists2[Int, Int, Int](ls00, ls11, _ + _)) //> List()
      println(applyLists2[Int, Int, Int](ls11, ls11, _ + _)) //> List(2, 4, 6, 8, 10)
      println(applyLists2[Int, Int, Int](ls11, ls22, _ + _)) //> List(2, 4, 6, 8, 10)
      println(applyLists2[Int, Double, Double](ls11, ls33, _ + _)) //> List(2.0, 4.0, 6.0, 8.0, 10.0)
      println(applyLists2[Int, Double, Double](ls22, ls33, _ * _)) //> List(1.0, 4.0, 9.0, 16.0, 25.0)
      println(applyLists2[Int, String, String](ls11, lssb, _ + _)) //> List(11, 22, 33, 44, 55)
      println(applyLists2[String, Int, String](lssc, ls11, _ + _)) //> List(11, 22, 33, 44, 55)
  }
}