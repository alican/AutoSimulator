package cars;

import autobahn.Location;
import utls.RandomString;

/**
 * Created by alican on 23.10.2015.
 */
public abstract class CarBaseClass extends Thread {


    String id;
    int speed;
    Location destination;
    int position;


    static RandomString randomString = new RandomString(8);


    public CarBaseClass() {
        /*
        random Car on random position
         */

        id = randomString.nextString();
    }

    public void update(){

        /*
        Anpassung aktueller Geschwindigkeit anhand Richtgeschwindigkeit und Geschwindkeit vorausfahrender Autos.
        Aktualisierung der position anhand aktueller Geschwindigkeit.
        */
    }


    public String getCarId() {
        return id; // eindeutige Indentifikation
    }



    public int getSpeed() {
        return speed;
    }

    private void setSpeed(int speed) {
        this.speed = speed; //privat
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
