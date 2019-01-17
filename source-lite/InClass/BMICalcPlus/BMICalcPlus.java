
class Person {
    private float height = 0; // in m
    private float weight = 0; // in kg

    // Setters
    public void setHeight(float heightIn) { 
        height = heightIn; 
    }

    public void setWeight(float weightIn) { 
        weight = weightIn; 
    }

    // Constructors
    Person() 
    {
        // Default Constructor
    }

    Person(float heightIn, float weigthIn) {
        setHeight(heightIn);
        setWeight(weigthIn);
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

    // Constructors
    EnglishPerson(float heightIn, float weigthIn) {
        setHeight(heightIn);
        setWeight(weigthIn);
    }
}

public class BMICalcPlus {
    public static void main(String[] args) {
        System.out.println("Starting BMICalcPlus...");

        EnglishPerson person = new EnglishPerson((6*12)+1 /*hight in inches*/,
            200 /* weight in lbs */ );
        //person.setHeight((6*12)+1);
        //person.setWeight(200);
        float BMI = person.CalcBMI();

        System.out.println("BMI: "+BMI);
    }
}