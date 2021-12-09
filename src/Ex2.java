import api.DirectedWeighted;
import api.DirectedWeightedGraphAlgorithms;
import api.graph.DirectedWeightedGraphAlgorithmsClass;
import api.GUI.graphWindow;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeighted getGrapg(String json_file) {
        DirectedWeightedGraphAlgorithms graph = new DirectedWeightedGraphAlgorithmsClass();
        graph.load(json_file);
        return graph.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DirectedWeightedGraphAlgorithmsClass();
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        graphWindow g = new graphWindow(alg);
    }

    public static void main(String[] args) {
        String file=args[0];
        runGUI(file);
    }
}