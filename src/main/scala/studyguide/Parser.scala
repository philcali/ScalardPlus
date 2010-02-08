package studyguide

import scala.io.Source.{fromFile => open}

case class FlashCard(val term: String, val definition: String)

object Parser {
    def apply(loc: String) = {
        val lines = (for (l <- open(loc).getLines) 
                     yield(l.substring(0, l.length - 1))).toList
        chunk(lines)
    }

    def chunkInto[A, B](size: Int, ls: List[B]) 
                    (fun: (List[B])=> A): List[A] = {
        ls match {
            case Nil => List()
            case _ => { 
                val (card, rest) = ls.splitAt(size) 
                fun(card) :: chunkInto(size, rest)(fun)
            }
        }
    }

    def chunk(ls: List[String]) = {
        chunkInto(2, ls) { c => FlashCard(c(0), c(1)) }
    }
}
