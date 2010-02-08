package studyguide

import java.util.Random

class Test(val cards: List[FlashCard]) {
    def shuffle() = {
        val rnd = new Random()
        def reorder[A](ls: List[A]): List[A] = {
            ls match {
                case h :: t => { 
                    val (left, right) = ls.splitAt(rnd.nextInt(ls.size))
                    right.head :: reorder(left ::: right.drop(1))
                }
                case Nil => List()
            }
        }

        new Test(reorder(cards))
    }    

    def start() {
        println("Welcome to the test!")
        println("There are " + cards.size + " cards in your deck.")

        // Inner function to define the test logic
        // It's recursive and stores the cards the user got correct
        def test(ls: List[(FlashCard, Int)], 
                 correct: List[FlashCard]): List[FlashCard] = { ls match {
            case (f, current) :: t => {
                println("Card " + (current + 1) + "/" + cards.size)
                println("Definition: " + f.definition)
                print("Term (spell correctly; Q to quit): ")
                val userInput = Console.readLine()
                if (userInput.equalsIgnoreCase("q")) { 
                    correct
                } else if(userInput.equalsIgnoreCase(f.term)) {
                    println("Correct!"); 
                    test(t, f :: correct)
                } else {
                    println("Wrong the correct answer was: " + f.term)
                    test(t, correct)
                }
            }
            case Nil => correct
        }}

        val correct = test(cards.zipWithIndex, List())
        // We can find the cards they got incorrect based on the ones they got right
        val incorrect = for(c <- cards; if(!correct.exists(_==c))) yield(c)
        println("You got " + correct.size + 
                "/"+ cards.size +" correct!")
        print("Try Again? (S-huffled, R-etry this one")
        if(!incorrect.isEmpty) print(", M-issed ones only")
        print(", Q-uit): ")
        val input = Console.readChar
        input.toLowerCase match {
            case 's' => shuffle.start()
            case 'r' => start()
            case 'm' => new Test(incorrect).start()
            case 'q' => println("See you next time!")
            case _ => println("Misunderstood... Exiting")
        }
    }
}

