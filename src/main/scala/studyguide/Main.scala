package studyguide

object Main {
    def main(args: Array[String]) {
        if (args.size < 1) {
            println("Please provide the path to a flash card text file.")
            exit(0)
        }
        val test = new Test(Parser(args(0)))
        test.start()
    }
}
