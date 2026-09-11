class AccessRuleEngine {
    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {
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
                accessorContext.equals("SAME_PACKAGE") ||
                accessorContext.equals(
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"))
                return "ALLOWED";
            else
                return "DENIED";
        }
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }
        return "DENIED";
    }
    static String describeContext(String accessorContext) {
        String[] words = accessorContext.toLowerCase().split("_");
        String result = "";
        for (String word : words) {
            result += Character.toUpperCase(word.charAt(0))
                    + word.substring(1)
                    + " ";
        }
        return result.trim();
    }
}
class PatientRecord {
    private String patientId;
    String wardCode;
    protected double vitalScore;
    public String facilityName;
    public PatientRecord(String patientId,
                         String wardCode,
                         double vitalScore,
                         String facilityName) {
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
class ICURecord extends PatientRecord {
    public ICURecord(String patientId,
                     String wardCode,
                     double vitalScore,
                     String facilityName) {
        super(patientId, wardCode, vitalScore, facilityName);
    }
}
public class Cross_Package_Inheritance_Reach  {
    public static void main(String[] args) {
        System.out.println(
            AccessRuleEngine.classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
            )
        );
        System.out.println(
            AccessRuleEngine.classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );
        System.out.println(
            AccessRuleEngine.describeContext(
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );
    }
}