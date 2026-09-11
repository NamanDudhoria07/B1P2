import java.util.*;
public class Q4_LibraryMemberJavaBean {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    // No-argument constructor
    public Q4_LibraryMemberJavaBean() {
        this(null, null);
    }

    // Name-only constructor
    public Q4_LibraryMemberJavaBean(String name) {
        this(null, name);
    }

    // ID + name constructor
    public Q4_LibraryMemberJavaBean(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    // Getter for membershipId
    public String getMembershipId() {
        return membershipId;
    }

    // Write-once setter for membershipId
    public void setMembershipId(String id) {

        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    // Getter for premiumMember
    public boolean isPremiumMember() {
        return premiumMember;
    }

    // Setter for premiumMember
    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only securityAnswer
    public void setSecurityAnswer(String answer) {

        if (answer != null) {
            this.securityAnswer =
                Integer.toHexString(answer.hashCode());
        }
    }

    // Main method
    public static void main(String[] args) {

        // Sample 1
        Q4_LibraryMemberJavaBean m1 =
            new Q4_LibraryMemberJavaBean("Priya Nair");

        System.out.println(
            m1.getMembershipId()
        );

        // Sample 2
        Q4_LibraryMemberJavaBean m2 =
            new Q4_LibraryMemberJavaBean(
                "LIB-8841",
                "Priya Nair"
            );

        System.out.println(
            m2.getMembershipId()
        );

        // Sample 3
        Q4_LibraryMemberJavaBean m3 =
            new Q4_LibraryMemberJavaBean();

        m3.setMembershipId("LIB-8841");
        m3.setMembershipId("FAKE-0000");

        System.out.println(
            m3.getMembershipId()
        );
    }
}