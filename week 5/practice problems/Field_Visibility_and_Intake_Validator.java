import java.util.*;
class AccessRuleEngine {
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }
        return "DENIED";
    }
    static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        for (String[] attempt : attempts) {
            String result = classifyAccess(attempt[0], attempt[1]);
            if (result.equals("ALLOWED"))
                allowed++;
            else
                denied++;
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}
class PatientRecord {
    private String patientId;
    String wardCode;
    protected double vitalScore;
    public String facilityName;
    public PatientRecord(String patientId, String wardCode,
                         double vitalScore, String facilityName) {
        if (patientId == null) {
            throw new IllegalArgumentException("Invalid patientId");
        }
        String id = patientId.trim();
        if (id.isEmpty() || id.length() < 4) {
            throw new IllegalArgumentException("Invalid patientId");
        }
        this.patientId = id;
        this.wardCode = wardCode;
        this.vitalScore = vitalScore;
        this.facilityName = facilityName;
    }
}
public class Field_Visibility_and_Intake_Validator {

    public static void main(String[] args) {
        System.out.println(
            AccessRuleEngine.classifyAccess(
                "private", "SAME_CLASS"
            )
        );
        System.out.println(
            AccessRuleEngine.classifyAccess(
                "default", "DIFFERENT_PACKAGE"
            )
        );
        String[][] attempts = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(
            AccessRuleEngine.summarizeBatch(attempts)
        );
        try {
            PatientRecord p1 = new PatientRecord(
                "PT9",
                "W3",
                98.2,
                "MediTrack Central"
            );

            System.out.println("Patient record created");
        }
        catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        try {
            PatientRecord p2 = new PatientRecord(
                "PT94",
                "W3",
                98.2,
                "MediTrack Central"
            );
            System.out.println("Patient record created");
        }
        catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}