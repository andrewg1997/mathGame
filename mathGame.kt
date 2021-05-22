// imports
import kotlin.random.Random
import java.util.Scanner
import kotlin.math.pow

// The main function will just call the user interface class UI
fun main () {
    val ui = UI()
    ui.run()
}

// UI will handle displaying and user interface
class UI {
    // Declare member variables
    private var numberOfQuestions: Int = 0
    private var high: Int = 0
    private var low: Int = 0
    private val input = Scanner(System.`in`)
    private var type: Int = 0
    // run() will be called from main and will call the other functions
    fun run() {
        // Declare the member variables with user input
        print("Please enter how many questions you would like: ")
        numberOfQuestions = input.nextInt()
        print("Please enter the highest random number to be generated: ")
        high = input.nextInt()
        print("Please enter the lowest random number to be generated: ")
        low = input.nextInt()
        print("Please enter which problem type you want (1. for quadratic, 2. for addition): ")
        type = input.nextInt()
        println()
        // Use a when construct to pick which function to use based on type of problem
        when (type){
            1 -> quadraticSet(numberOfQuestions)
            2 -> additionSet(numberOfQuestions)
        }
    }
    // prompt the user to solve x amount of addition problems
    private fun additionSet(amount: Int) {
        var count = amount
        while(count > 0) {
            val problem :Problem = Addition(low, high)
            print(askQuestion("Addition", problem))
            val userAns = input.nextInt()
            println(checkAnswer(userAns, problem))
            count--
        }
    }
    // prompt the user to solve x amount of quadratic problems
    private fun quadraticSet(amount: Int) {
        var count = amount
        while(count > 0) {
            val problem :Problem = QuadraticFormula(low, high)
            print(askQuestion("quadratic", problem))
            val userAns = input.nextDouble()
            println(checkAnswer(userAns, problem))
            count--
        }
    }
    // Returns a string to be printed out to prompt the user for an answer
    private fun askQuestion(type: String, problem: Problem): String {
        return "Solve the $type problem: ${problem.getQuestion()}: "
    }
    // returns a string letting the user know if their answer is correct - for Doubles
    private fun checkAnswer(userAns: Double, problem: Problem): String {
        return if (problem.isAnswer(userAns)) {
            "Correct! The answer is $userAns"
        } else {
            "Incorrect! The answer is ${problem.getAnswerString()}"
        }
    }
    // returns a string letting the user know if their answer is correct - for Ints
    private fun checkAnswer(userAns: Int, problem: Problem): String {
        return when{
            problem.isAnswer(userAns) -> "Correct! The answer is $userAns"
            else -> "Incorrect! The answer is ${problem.getAnswerString()}"
        }
    }
}
// The superclass Problem. Basically just serves as a template for the subclasses so the coupling goes smoother
open class Problem
{
    open fun getQuestion(): String{
        return ""
    }
    open fun isAnswer(userAns: Int): Boolean {
        return (false)
    }
    open fun isAnswer(userAns: Double): Boolean {
        return (false)
    }
    open fun getAnswerString(): String {
        return ""
    }
}
// addition problem generator
class Addition constructor(low: Int, high: Int) : Problem() {
    private var answer: Int = 0
    private var num1: Int = 0
    private var num2: Int = 0
    init {
        answer = Random.nextInt(low, high)
        num1 = Random.nextInt(low, high)
        num2 = answer - num1
    }
    override fun getQuestion(): String {
        return ("x = $num1 + $num2")
    }
    override fun isAnswer(userAns: Int): Boolean{
        return (userAns == answer)
    }
    override fun getAnswerString(): String {
        return "" + answer
    }
}
// Quadratic problem generator
class QuadraticFormula(low:Int, high:Int) : Problem() {
    private var answer1: Double = 0.0
    private var answer2: Double = 0.0
    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var num3: Double = 0.0
    init {
        while (answer1 == 0.0 || answer1.isNaN() && answer2.isNaN()) {
            num1 = Random.nextInt(low, high) + .0
            num2 = Random.nextInt(low, high) + .0
            num3 = Random.nextInt(low, high) + .0
            answer1 = (-num2 + (kotlin.math.sqrt(num2.pow(2.0) - 4.0 * num1 * num3)))
            answer2 = (-num2+ kotlin.math.sqrt(num2.pow(2.0) - 4.0 * num1 * num3))
        }
    }
    override fun getQuestion(): String {
        return ("x = $num1 + ${num2}x + ${num3}x^2")
    }
    override fun getAnswerString(): String {
        return "$answer1 and $answer2"
    }
    override fun isAnswer(userAns: Double): Boolean {
        return (userAns == answer1 || userAns == answer2)
    }
}
