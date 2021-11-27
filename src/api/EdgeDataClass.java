package api;

public class EdgeDataClass implements api.EdgeData {
    private int src;
    private int dest;
    private double weight;
    private int tag;

    public EdgeDataClass(int src, int dest, int weight, int tag){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.tag = tag;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
