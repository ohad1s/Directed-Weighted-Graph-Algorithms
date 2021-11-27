package api;

public class NodeDataClass implements NodeData {
    private int key;
    private GeoLocationClass location;
    private double weight;
    private int tag;

    /**
     * NodeData constructor
     * @param key
     * @param x
     * @param y
     * @param z
     */
    public NodeDataClass(int key, double x, double y, double z, double weight, int tag){
        this.key = key;
        this.location = new GeoLocationClass(x, y, z);
        this.weight = weight;
        this.tag = tag;
    }

    /**
     * this method returns the key of the node
     * @return key
     */
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocationClass getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        GeoLocationClass newLocation = new GeoLocationClass(p);
        this.location = newLocation;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
