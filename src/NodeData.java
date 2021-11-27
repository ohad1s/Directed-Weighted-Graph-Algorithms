package api;

public class NodeData implements NodeDataInterface {
    private int key;
    private double x;
    private double y;
    private double z;

    /**
     * NodeData constructor
     * @param key
     * @param x
     * @param y
     * @param z
     */
    public NodeData(int key, double x, double y, double z){
        this.key = key;
        this.x = x;
        this.y = y;
        this.z = z;

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
    public GeoLocationInterface getLocation() {
        return null;
    }

    @Override
    public void setLocation(GeoLocationInterface p) {

    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

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
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
