package api;

public class NodeDataClass implements NodeData {
    private int key;
    private GeoLocationClass location;
    private double weight;
    private int tag;
    private String info;

    /**
     * this method is the NodeData constructor.
     *
     * @param key
     * @param x
     * @param y
     * @param z
     */
    public NodeDataClass(int key, double x, double y, double z, double weight) {
        this.key = key;
        this.location = new GeoLocationClass(x, y, z);
        this.weight = weight;
        this.tag = 0;
        this.info = "key: " + key + ", location: (" + x + ", " + y + ", " + z + "), weight: " + weight + ", tag: " + tag;

    }

    /**
     * this method returns the vertex' key.
     *
     * @return key
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * this method returns the vertex' GeoLocation.
     *
     * @return GeoLocation
     */
    @Override
    public GeoLocationClass getLocation() {
        return this.location;
    }

    /**
     * this method sets vertex' GeoLocation based ont given GeoLocation.
     *
     * @param p - other GeoLocation
     */
    @Override
    public void setLocation(GeoLocation p) {
        double newX = p.x();
        double newY = p.y();
        double newZ = p.z();
        GeoLocationClass newLocation = new GeoLocationClass(newX, newY, newZ);
        this.location = newLocation;
    }

    /**
     * this method returns the vertex' weight.
     *
     * @return this.weight
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * this method sets the vertex' weight.
     *
     * @return
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * this method returns the vertex' info String.
     *
     * @return - this.info
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * this method sets the vertex' info String
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * this method returns the vertex' tag.
     * @return - this.tag
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * this method sets the vertex' tag.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
