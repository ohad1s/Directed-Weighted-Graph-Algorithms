# Directed Weighted Graph Algorithms
**About This Project**

In this project we implmented a directed weighted graph and graph algorithms. In addition, we created a GUI that can load grpahs json file, show them, run algorithms on them, make changes and save them as json file.

**What is Directed Weighted Graph?**

A Directed Weighted Graph is a graph in which the edges that connect the vertices has direction. For example: lets say vertex 1 is connected to vertex 3, than it is possible to travel from vertex 1 to vertex 3 but not the other way aroud unless the is a different edge in the graph that is connecting vertex 3 to vertex 1.

In addition, each edge has a weight, which set the "cost" of traveling along the edge, meaning some times it can be faster to go from one vertex to second vertex through a third vertex even if there is a direct edge from the first vertex to the second vertex. For Example: If there is an edge between vertex 1 and vertex 3 that has a weight of 3, and edges from vertex 1 to 2 and from 2 to 3 that has a weight of 1 each, it is better to get from vertex 1 to 3 through vertex 2.

**Algorithms That Are Implemented:**

Is the Graph Strongly Connected - Checking if the graph is strongly connected using Breadth-first search on the graph, than running Breadth-first search on the inverted graph.

Shortest Path between two vertices - Finding the path and distance using Dijkstra's algorithm.

Finding the Graph Center - Finding the vertex in the graph, that has the minimal distance to the farthest vertex using Dijkstra's algorithm.

Finding Shortest Path For List of Vertices - Using a greedy algorithm and Dijkstra's algorithm in order to find the shortest path that goes through all the vertices in the list.

**How To Run The Jar File?**

  First, run CMD from the driectory in which the jar file is located.

  Next, run the following command: java -jar Ex2.jar "Json File" 
  
  Json files are located in the data directory.
  
  After running the jar file, the next window should apper (loading G3.json file):
  
  ![image](https://user-images.githubusercontent.com/78217803/145712799-cafa53ca-7a66-4055-abbb-78e03aa3f6a3.png)

**how to use the GUI:**

After the GUI is opened, on the top left there is the menu button.

After preesing the menu button, a drop down menu should appear.

At the drop down menu there are four options:

1. load - this button allows to load a graph from a json file.
2. save - this button allows to save a graph as a json file.
3. Choose Algorithm - this allows to choose the algorithm we want to run on the current graph.
4. Actions on the Graph - this allows us to preform actions on the graph such as removing and adding vertices, Edges Etc.

 
**Project UML**
  
![DirectedWeightedDiagram](https://user-images.githubusercontent.com/78217803/145467408-0bf84cc2-5bcd-4ba8-92b8-62a12266cbbf.jpeg)

![GUI Diagram](https://user-images.githubusercontent.com/78217803/145467424-23ffe92c-7804-4410-9148-8866a886b6d5.jpeg)

  
**Performance analysis:**
*1,000 Vertices 10,000 Edges Graph:*

isConnected: 47 ms

center: 2 sec 794 ms

*10,000 Vertices 100,000 Edges Graph:*

isConnected: 86 ms

Center: 10 min 57 sec

*100,000 Verticies 1,000,000 Edges Graph:*

isConnected: 858 ms

Center: timeout

*1,000,000 Vertices 10,000,000 Edges Graph:*

Could not create such connected graph.
