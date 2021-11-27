package api;

public class GeoLocationClass implements GeoLocation {
    private double x;
    private double y;
    private double z;


    public GeoLocationClass(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GeoLocationClass(GeoLocationClass other){
        this.x = other.x();
        this.y = other.y();
        this.z = other.z();
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(api.GeoLocation g) {
        double otherX = g.x();
        double otherY = g.y();
        double otherZ = g.z();
        double xDist = this.x - otherX;
        double yDist = this.y - otherY;
        double zDist = this.z - otherZ;

        double dist = Math.pow((Math.pow(xDist,2) + Math.pow(zDist, 2) + Math.pow(zDist, 2)), 0.5);
        return dist;
    }
}
