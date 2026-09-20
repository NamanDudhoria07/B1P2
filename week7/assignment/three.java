import java.util.*;

interface Insurable {
    String getInsuranceInfo();
}

abstract class ServiceableVehicle {
    private double mileage;

    public ServiceableVehicle() {
        this.mileage = 0.0;
    }

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km < 0) {
            System.out.println("rejected, distance unchanged");
        } else {
            this.mileage += km;
        }
    }

    public abstract String performMaintenance();
}

class Forklift extends ServiceableVehicle implements Insurable {
    private String assetTag;

    public Forklift(String assetTag) {
        super();
        this.assetTag = assetTag;
    }

    public String getAssetTag() {
        return assetTag;
    }

    @Override
    public String performMaintenance() {
        return "\"Forklift " + assetTag + ": hydraulic and fork inspection complete\"";
    }

    @Override
    public String getInsuranceInfo() {
        return "\"Insured under fleet policy - Asset " + assetTag + "\"";
    }
}

class HeavyDutyForklift extends Forklift {

    public HeavyDutyForklift(String assetTag) {
        super(assetTag);
    }

    @Override
    public String performMaintenance() {
        String baseMaintenance = super.performMaintenance();
        String unquoted = baseMaintenance.substring(1, baseMaintenance.length() - 1);
        return "\"" + unquoted + " | high-pressure hydraulic check complete\"";
    }
}

public class three {

    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            Insurable insurableRef = (Insurable) v;
            return insurableRef.getInsuranceInfo();
        } else {
            return "\"No insurance record exists\"";
        }
    }

    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println(f.getMileage());

        System.out.println(f.performMaintenance());

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());

        System.out.println(getInsuranceIfApplicable(f));
    }
}