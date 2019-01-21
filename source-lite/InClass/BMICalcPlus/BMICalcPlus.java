class Person {
    private float height = 0; // in m
    private float weight = 0; // in kg

    // Setters
    protected void setHeight(float heightIn) {
        height = heightIn;
    }

    protected void setWeight(float weightIn) {
        weight = weightIn;
    }

    // Constructors
    Person()
    {
        // Default Constuctor
    }

    Person(float heightIn, float weightIn) {
        setHeight(heightIn);
        setWeight(weightIn);
    }

    public float CalcBMI() {
        return weight / (height * height);
    }
}

class EnglishPerson extends Person {
    public void setHeight(float heightIn) { 
        super.setHeight(heightIn * (float)0.025); // convert inchs to meters
    }

    public void setWeight(float weightIn) { 
        super.setWeight(weightIn * (float)0.45); // Convert lbs to kg
    }

    EnglishPerson(float heightIn, float weightIn) {
        setHeight(heightIn);
        setWeight(weightIn);
    }
}

public class BMICalcPlus {
    public static void main(String[] args) {
        System.out.println("Starting BMICalcPlus...");

        Person myPerson = new Person(2,60);
        System.out.println(myPerson.CalcBMI());

        EnglishPerson myEnglishPerson = new EnglishPerson((6*12)+1,200); 
        System.out.println(myEnglishPerson.CalcBMI());

        EnglishPerson anotherPerson = new EnglishPerson(80, 190);
        System.out.println(anotherPerson.CalcBMI());
    }
}