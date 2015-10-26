package cars;

import autobahn.Location;

/**
 * Created by alican on 26.10.2015.
 */
public class CarDataPackage{

    String id;
    int speed;
    Location destination;
    int position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

