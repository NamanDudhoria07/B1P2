import java.util.*;

abstract class Question {
    protected int questionNumber;
    protected String questionText;
    protected String correctAnswer;

    public Question(int questionNumber, String questionText, String correctAnswer) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion(int questionNumber, String questionText, String correctAnswer) {
        super(questionNumber, questionText, correctAnswer);
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(int questionNumber, String questionText, String correctAnswer) {
        super(questionNumber, questionText, correctAnswer);
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Student {
    private int studentId;
    private String name;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Examination {
    private String title;
    private List<Question> questions;

    public Examination(String title) {
        this.title = title;
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Integer, String> answers;
    private boolean submitted;
    private int score;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.answers = new HashMap<>();
        this.submitted = false;
    }

    public void answerQuestion(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }

        answers.put(questionNumber, answer);
        System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) {
            System.out.println("Examination '" + examination.getTitle() + "' has already been submitted.");
            return;
        }

        submitted = true;
        evaluate();

        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle() +
                "' attempt: " + score + "/" +
                examination.getQuestions().size() + " correct.");
    }

    private void evaluate() {
        score = 0;

        for (Question question : examination.getQuestions()) {
            String answer = answers.get(question.getQuestionNumber());

            if (answer != null && question.isCorrect(answer)) {
                score++;
            }
        }
    }
}

public class one{
    public static void main(String[] args) {

        Student student = new Student(101, "John");

        Examination mathQuiz = new Examination("Math Quiz");

        mathQuiz.addQuestion(
                new MultipleChoiceQuestion(
                        1,
                        "What is 2 + 2?",
                        "A"
                )
        );

        mathQuiz.addQuestion(
                new MultipleChoiceQuestion(
                        2,
                        "What is 5 + 5?",
                        "B"
                )
        );

        System.out.println(
                "Examination '" + mathQuiz.getTitle() +
                "' started by " + student.getName() + "."
        );

        Attempt attempt = new Attempt(student, mathQuiz);

        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        attempt.submit();
    }
}