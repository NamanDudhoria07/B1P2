import java.util.*;

interface ScoringRule {
    double calculateScore(double idea, double execution, double presentation);
}

class InnovationScoring implements ScoringRule {
    @Override
    public double calculateScore(double idea, double execution, double presentation) {
        return idea * 0.50 + execution * 0.30 + presentation * 0.20;
    }
}

class OpenScoring implements ScoringRule {
    @Override
    public double calculateScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
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

class Team {
    private String teamName;
    private List<Student> members;
    private String track;
    private ScoringRule scoringRule;
    private Project project;

    public Team(String teamName, String track, ScoringRule scoringRule) {
        this.teamName = teamName;
        this.track = track;
        this.scoringRule = scoringRule;
        this.members = new ArrayList<>();
    }

    public boolean addMember(Student student) {
        if (members.size() >= 4) {
            return false;
        }

        members.add(student);
        return true;
    }

    public int getMemberCount() {
        return members.size();
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTrack() {
        return track;
    }

    public ScoringRule getScoringRule() {
        return scoringRule;
    }

    public boolean containsStudent(Student student) {
        return members.contains(student);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}

class Project {
    private String projectName;
    private Team team;

    public Project(String projectName, Team team) {
        this.projectName = projectName;
        this.team = team;
    }

    public String getProjectName() {
        return projectName;
    }
}

class Judge {
    private int judgeId;
    private String name;

    public Judge(int judgeId, String name) {
        this.judgeId = judgeId;
        this.name = name;
    }

    public Score giveScore(Project project,
                           double idea,
                           double execution,
                           double presentation) {

        return new Score(
                project,
                idea,
                execution,
                presentation
        );
    }
}

class Score {
    private Project project;
    private double idea;
    private double execution;
    private double presentation;

    public Score(Project project,
                 double idea,
                 double execution,
                 double presentation) {

        this.project = project;
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public double calculate(ScoringRule rule) {
        return rule.calculateScore(
                idea,
                execution,
                presentation
        );
    }

    public void setIdea(double idea) {
        this.idea = idea;
    }

    public Project getProject() {
        return project;
    }
}

class Hackathon {
    private String name;
    private List<Team> teams;
    private List<Score> scores;
    private String status;

    public Hackathon(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.status = "Open";
    }

    public boolean registerTeam(Team team) {

        if (!status.equals("Open")) {
            System.out.println(
                "Registration failed: Hackathon is not open."
            );
            return false;
        }

        if (team.getMemberCount() < 2 ||
            team.getMemberCount() > 4) {

            System.out.println(
                "Registration failed: A team must have 2 to 4 members."
            );
            return false;
        }

        for (Team existingTeam : teams) {
            for (Student student : getStudents(team)) {

                if (existingTeam.containsStudent(student)) {
                    System.out.println(
                        "Registration failed: " +
                        student.getName() +
                        " is already registered in another team."
                    );
                    return false;
                }
            }
        }

        teams.add(team);

        System.out.println(
            "Team " + team.getTeamName() +
            " registered (" +
            team.getMemberCount() +
            " members, " +
            team.getTrack() +
            " track)."
        );

        return true;
    }

    private List<Student> getStudents(Team team) {
        List<Student> students = new ArrayList<>();

        try {
            java.lang.reflect.Field field =
                    Team.class.getDeclaredField("members");

            field.setAccessible(true);

            students =
                    (List<Student>) field.get(team);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public void submitProject(Team team, String projectName) {

        if (team.getProject() != null) {
            System.out.println(
                "Submission failed: Team can submit only one project."
            );
            return;
        }

        Project project =
                new Project(projectName, team);

        team.setProject(project);

        System.out.println(
            "Project '" + projectName +
            "' submitted by " +
            team.getTeamName() + "."
        );
    }

    public void startJudging() {
        if (status.equals("Open")) {
            status = "Judging";
        }
    }

    public void recordScore(Score score) {

        if (status.equals("Published")) {
            System.out.println(
                "Score rejected: Results have already been published."
            );
            return;
        }

        scores.add(score);

        System.out.println(
            "Score recorded for '" +
            score.getProject().getProjectName() +
            "'."
        );
    }

    public void publishResults() {

        status = "Published";

        System.out.println("Results published.");

        for (Score score : scores) {

            Project project = score.getProject();
            Team team = getTeamForProject(project);

            double finalScore =
                    score.calculate(team.getScoringRule());

            System.out.printf(
                "Final score: %.2f%n",
                finalScore
            );
        }
    }

    private Team getTeamForProject(Project project) {

        for (Team team : teams) {
            if (team.getProject() == project) {
                return team;
            }
        }

        return null;
    }

    public void changeScore(Score score, double newIdea) {

        if (status.equals("Published")) {
            System.out.println(
                "Rescore rejected: Results have already been published."
            );
            return;
        }

        score.setIdea(newIdea);

        System.out.println("Score updated.");
    }
}

public class one {

    public static void main(String[] args) {

        Hackathon hackathon =
                new Hackathon("Code Sprint");

        Student asha =
                new Student(1, "Asha");

        Student ravi =
                new Student(2, "Ravi");

        Student neha =
                new Student(3, "Neha");

        Student kiran =
                new Student(4, "Kiran");

        Team byteBusters =
                new Team(
                    "ByteBusters",
                    "Innovation",
                    new InnovationScoring()
                );

        byteBusters.addMember(asha);
        byteBusters.addMember(ravi);
        byteBusters.addMember(neha);

        hackathon.registerTeam(byteBusters);

        Team soloCoder =
                new Team(
                    "SoloCoder",
                    "Open",
                    new OpenScoring()
                );

        soloCoder.addMember(kiran);

        hackathon.registerTeam(soloCoder);

        hackathon.submitProject(
                byteBusters,
                "SmartAttend"
        );

        hackathon.startJudging();

        Judge judge =
                new Judge(101, "Judge A");

        Score score =
                judge.giveScore(
                    byteBusters.getProject(),
                    8,
                    7,
                    9
                );

        hackathon.recordScore(score);

        hackathon.publishResults();

        hackathon.changeScore(score, 10);
    }
}