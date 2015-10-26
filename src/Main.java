import cars.AutonomCar;


public class Main {

    public static void main(String[] args){
        AutonomCar autonomCar = new AutonomCar();
        AutonomCar autonomCar2 = new AutonomCar();


        autonomCar.start();
        autonomCar2.start();




        /*
        String carClassName = "cars.AutonomCar";
        Class<?> CarClass = Class.forName(carClassName);
        Object car = CarClass.newInstance();

        String methodName = "";

        methodName = "printId";
        Method printIdMethod = car.getClass().getMethod(methodName);
        printIdMethod.invoke(car);
        */
    }

}
