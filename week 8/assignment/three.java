import java.util.*;

interface Capability {
    String getName();
    boolean setValue(int value);
    String getValueText();
}

class PowerCapability implements Capability {
    private boolean on = false;

    public String getName() {
        return "Power";
    }

    public boolean setValue(int value) {
        if (value != 0 && value != 1) {
            return false;
        }
        on = value == 1;
        return true;
    }

    public String getValueText() {
        return on ? "ON" : "OFF";
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    public String getName() {
        return "Brightness";
    }

    public boolean setValue(int value) {
        if (value < 0 || value > 100) {
            return false;
        }
        brightness = value;
        return true;
    }

    public String getValueText() {
        return brightness + "%";
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 16;

    public String getName() {
        return "Temperature";
    }

    public boolean setValue(int value) {
        if (value < 16 || value > 30) {
            return false;
        }
        temperature = value;
        return true;
    }

    public String getValueText() {
        return temperature + "°C";
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities = new LinkedHashMap<>();

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
        System.out.println(name + ": " + capability.getName() + " capability added.");
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public boolean setCapability(String capabilityName, int value) {
        Capability capability = capabilities.get(capabilityName);

        if (capability == null) {
            return false;
        }

        return capability.setValue(value);
    }

    public String getCapabilityValue(String capabilityName) {
        return capabilities.get(capabilityName).getValueText();
    }
}

class Lab {
    private List<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        devices.add(device);
    }

    public List<Device> getDevices() {
        return devices;
    }
}

class SceneStep {
    private String capabilityName;
    private int value;

    public SceneStep(String capabilityName, int value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public int execute(List<Device> devices) {
        int count = 0;

        for (Device device : devices) {
            if (device.hasCapability(capabilityName)) {
                device.setCapability(capabilityName, value);

                String valueText = device.getCapabilityValue(capabilityName);
                System.out.println(device.getName() + ": "
                        + capabilityName.toLowerCase()
                        + " set to " + valueText + ".");
                count++;
            }
        }

        return count;
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps = new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public void addStep(SceneStep step) {
        steps.add(step);
    }

    public void execute(Lab lab) {
        System.out.println("Scene '" + name + "' started.");

        int totalActions = 0;

        for (SceneStep step : steps) {
            totalActions += step.execute(lab.getDevices());
        }

        System.out.println("Scene '" + name + "' completed: "
                + totalActions + " actions applied.");
    }
}

public class three {
    public static void main(String[] args) {

        Lab lab = new Lab();

        Device labAC = new Device("Lab AC");
        labAC.addCapability(new PowerCapability());
        labAC.addCapability(new TemperatureCapability());

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        lab.addDevice(labAC);
        lab.addDevice(ceilingLights);
        lab.addDevice(projector);

        Scene lectureMode = new Scene("Lecture Mode");

        lectureMode.addStep(new SceneStep("Power", 1));
        lectureMode.addStep(new SceneStep("Brightness", 40));
        lectureMode.addStep(new SceneStep("Temperature", 24));

        lectureMode.execute(lab);

        System.out.println();

        if (!labAC.setCapability("Temperature", 12)) {
            System.out.println(
                "Rejected: Lab AC temperature must be between 16°C and 30°C."
            );
        }

        System.out.println();

        projector.addCapability(new BrightnessCapability());
        projector.setCapability("Brightness", 70);

        System.out.println("Projector: brightness set to "
                + projector.getCapabilityValue("Brightness") + ".");
    }
}