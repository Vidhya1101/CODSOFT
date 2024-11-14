import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswerIndex;

    public Question(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class QuizApplication {
    private static final int TIMER_DURATION = 10;
    private static List<Question> questions = new ArrayList<>();
    private static List<Integer> userAnswers = new ArrayList<>();
    private static int score = 0;

    public static void main(String[] args) {
        initializeQuestions();
        startQuiz();
        displayResult();
    }

    private static void initializeQuestions() {
        questions.add(new Question("What is the capital of India?", new String[]{"Kolkata", "Mumbai", "Delhi", "Andhra"}, 2));
        questions.add(new Question("What is 5 + 2?", new String[]{"8", "7", "5", "6"}, 1));
        questions.add(new Question("What is the color of the banana?", new String[]{"Yellow", "Green", "Red", "Blue"}, 0));
    }

    private static void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            System.out.println(question.question);
            for (int i = 0; i < question.options.length; i++) {
                System.out.println((i + 1) + ". " + question.options[i]);
            }

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Moving to the next question.");
                    userAnswers.add(-1);
                    timer.cancel();
                }
            };

            timer.schedule(task, TIMER_DURATION * 1000);

            boolean answered = false;
            while (!answered) {
                try {
                    System.out.print("Your answer (1-" + question.options.length + "): ");
                    if (scanner.hasNextInt()) {
                        int answer = scanner.nextInt() - 1;
                        if (answer >= 0 && answer < question.options.length) {
                            userAnswers.add(answer);
                            if (answer == question.correctAnswerIndex) {
                                score++;
                            }
                            answered = true;
                        } else {
                            System.out.println("Invalid choice, please try again.");
                        }
                    } else {
                        scanner.next();
                        System.out.println("Please enter a valid number.");
                    }
                } catch (Exception e) {
                    System.out.println("Error reading input.");
                    break;
                }
            }

            timer.cancel();
        }
        scanner.close();
    }

    private static void displayResult() {
        System.out.println("Your final score: " + score + "/" + questions.size());

        for (int i = 0; i < questions.size(); i++) {
            String result = (userAnswers.get(i) == questions.get(i).correctAnswerIndex) ? "Correct" : "Incorrect";
            System.out.println("Question " + (i + 1) + ": " + result);
        }
    }
}
