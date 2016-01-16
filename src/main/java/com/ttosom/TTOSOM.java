package com.ttosom;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import com.ttosom.distance.Distance;
import com.ttosom.neuron.Neuron;
import com.ttosom.neuron.NodeValue;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
 
/**
 * This class represents the implementation of the TTOSOM following the specification given in:
 * 
 * <p>
 * Changes in version 1.0.1 (17, March 2014)
 * 
 * <ol>
 * <li>Fixed a bug in the computation of the bubble of activity.</li>
 * <li>Added modules for the pre-computation of the bubble of activity which speed-up the algorithm dramatically.</li>
 * <li>Improved javadoc documentation.</li>
 * <li>Change interface getNeurons() for getWeights() which returns an array of weights.</li>
 * <li>All the files containing topology information where moved to the folder "data".</li>
 * <li>Added a series of modules written in R for the visualization of the TTOSOM.</li>
 * <li>change the internal implementation of FindBMU(...) so as to perform iterative search instead of recursion.</li>
 * </ol>
 * </p>
 * 
 * @author Cesar Astudillo, Gonzalo Maldonado
 * @version 1.0.1
 */
 
public class TTOSOM extends Classifier implements Serializable{
    private static final long serialVersionUID = -8464084336600641535L;
    private Neuron root; 
 
 
    private Map<Neuron, Map<Integer, ArrayList<Neuron>>> map; 
    private ArrayList<Instance> weights = new ArrayList<Instance>();
    private ArrayList<Neuron> neuronList = new ArrayList<Neuron>();
 
 
    private double initialLearning;
    private double initialRadius;
    private double finalLearning;
    private double finalRadius;
    private int iterations;
    private Distance distance;
    private Instances dataSet;
    private boolean clustering;
    private Random random;
 
    public TTOSOM(Instances dataSet, double initialLearning, double initialRadius, double finalLearning,
            double finalRadius,int iterations, Distance distance,
            boolean clustering, Random random){
        this.initialLearning = initialLearning;
        this.initialRadius = initialRadius;
        this.finalLearning = finalLearning;
        this.finalRadius = finalRadius;
        this.iterations = iterations;
        this.distance = distance;
        this.dataSet = dataSet;
        this.clustering=clustering;
        this.random = random;
    }
 
    /**
     * Assign classes to every neuron in the tree. 
     * @param node neuron to which assign the class
     * @param labeledData labeled data in the data set.
     * @param clusterVector cluster vector.
     */
    private void assignClassToNeurons(Neuron node,Instances labeledData,
            int[] clusterVector){
 
        if ( node != null ){
            int [] classes = new int[labeledData.numClasses()];
 
            for(int i=0;i<classes.length;i++){
                classes[i] = 0;
            }
 
            int classIndex =-1;
            int numAttributes = labeledData.numAttributes();
            for(int i=0;i<clusterVector.length;i++){
                if(clusterVector[i]==node.getId()){
                    classIndex = (int) labeledData.instance(i).value(numAttributes-1);
                    classes[classIndex]++;
                }
            }
 
            int counter=0;
            for(int i=0;i<classes.length;i++){
                counter+=classes[i];
            }
            if(counter!=0){
                int maxValue = Integer.MIN_VALUE;
                int popularClassIndex=-1;
                for(int i=0;i<classes.length;i++){
                    if(classes[i]>maxValue){
                        maxValue=classes[i];
                        popularClassIndex = i;
                    }
                }
 
                node.getWeight().setClassValue(popularClassIndex);
            }
            else{
                //node.getWeight().setClassMissing();   
                Random rdm = new Random();
                node.getWeight().setClassValue(rdm.nextInt(labeledData.numClasses()));
 
            }
            for(Neuron child : node.getChildren()){
                assignClassToNeurons(child,labeledData,clusterVector);
            }
        }
    }
 
 
    /**
     * Method which creates the model, from we will classify the instances
     * later.
     * @param data data set from we will train the classifier.
     * @throws Exception 
     */
    @Override
    public void buildClassifier(Instances data) throws Exception {
        cluster(data);
        computeLabels();
 
 
    }
 
 
    /**
     * Calculates the bubble of activity from a given Neuron and a given radius.
     * @param bubbleOfActivity current bubble of activity 
     * @param current current node in which is traversing
     * @param radius radius to calculate the BoA
     * @param origin origin node 
     */
    public void calculateNeighborhood(ArrayList<Neuron> bubbleOfActivity,
            Neuron current, long radius, Neuron origin){  
        if(radius<=0){
            return;
        }
        else{
            for(Neuron child : current.getChildren()){
                if(child != origin ){
                    bubbleOfActivity.add(child);
                    calculateNeighborhood(bubbleOfActivity,child,radius-1, current);
                }
            }
            Neuron parent = current.getParent();
            if(parent!=null && parent!=origin){
                bubbleOfActivity.add(parent);
                calculateNeighborhood(bubbleOfActivity,parent,radius-1, current);
            }
        }
        return;
 
    }
 
 
 
 
 
    /**
     * This method it is used to calculate the value of a paremeter (like 
     * learning rate or radius)
     * @param currentValue current value of the parameter
     * @param initialValue initial value of the parameter
     * @param finalValue final value of the parameter
     * @param iterations iterations of the process
     * @return the new value of the parameter 
     */
    public double calculateValue(double currentValue, double initialValue, 
            double finalValue,
            int iterations){
 
        double delta = (initialValue - finalValue)/iterations;
        currentValue -= delta;
        return currentValue;
 
    }
 
    /**
     * Method which classify an instance
     * @param instance instance to classify
     * @return the class which we will asign to the instance.
     */
    @Override
    public double classifyInstance(Instance instance){
        Neuron bmu = findBMU(root,instance, root);
        Double classValue = bmu.getWeight().classValue();
        //System.out.println("Instance: "+instance+" predicted class: "+instance.classAttribute().value(classValue.intValue()));
        return bmu.getWeight().classValue();
    }
 
 
 
    /**
     * cluster the data.
     * 
     * @param data the data to be clustered.
     */
    private void cluster(Instances data) {
 
//        System.out.println("OK1");
 
        double radius = initialRadius;
        double learningRate = initialLearning;
 
        //Let's create the clusters
        for(int i=0;i<iterations;i++){
            int j = random.nextInt(data.numInstances());
            train(data.instance(j), root,Math.round(radius), learningRate); 
            radius = calculateValue(radius, initialRadius, finalRadius, iterations);
            learningRate = calculateValue(learningRate, initialLearning, finalLearning, iterations);  
 
        }
 
//        System.out.println("OK2");
    }
 
    /**
     * This method gets the id of the BMU of a given instance.
     * @param instance Instance from we will find the BMU.
     * @return id of the BMU.
     */
    private int clusterInstance(Instance instance){
        Neuron bmu = findBMU(root, instance, root);
        return bmu.getId();
    }
 
 
    /**
     * Compute the labels.
     * 
     * @param data The data to be clustered.
     */
 
 
    private void computeLabels() {
        //System.out.println("ok");
        if(!clustering){
            //Let's get the labeled data to generate the classifier 
            Instances labeledData = new Instances(dataSet,0);
            Instance aux;
 
            for(int i=0;i<dataSet.numInstances();i++){
            	aux = dataSet.instance(i);
            	if(!aux.classIsMissing()){
                    labeledData.add((Instance) aux.copy());
                }
            }
 
            //Let's assign a class to each neuron in the tree.
            int[] clusterVector = generateClusterVector(labeledData, distance);
            assignClassToNeurons(root, labeledData, clusterVector);
        }
    }
 
    /**
     * Creates a new tree from a given ArrayList 
     * @param elements description of every Neuron in the tree whit its id and children
     * @param dataSet given data set
     */
    public void describeTopology(List<NodeValue> elements){
        root = new Neuron(null,elements,dataSet);
        map= new HashMap<Neuron, Map<Integer, ArrayList<Neuron>>> ();
         
        //keep a list of references to the neurons.
        //this avoid the full tree traversal using recursion an allow the use of a for loop
        neuronList.clear();
        treeToArray(root);//update the neuron array retrieving all the neurons in pre-order.
         
        preComputeNeighbors();
 
    }
 
    /**
     * Finds the BMU to a given data sample of the data set
     * @param currentNode current node being examined
     * @param inputSample input sample to calculate the BMU
     * @param bestSoFar Current best neuron so far.
     * @param distance distance to calculate the BMU
     * @param dataSet data set containing the input sample
     * @return best matching unit corresponding to the input sample
     */
    public Neuron findBMU(Neuron currentNode,Instance inputSample, Neuron bestSoFar){
         
        /*
        if(actualNode == null){
            return bmu;
        }
        else{
            if(distance.calculateDistance(inputSample, bmu.getWeight(), dataSet) >
            distance.calculateDistance(inputSample, actualNode.getWeight(), dataSet)){
                bmu = actualNode;
            }
            for(Neuron child : actualNode.getChildren()){
                bmu = findBMU(child, inputSample, bmu);
            }
        }
        return bmu;
        */
         
        double d =distance.calculateDistance(inputSample, bestSoFar.getWeight(), dataSet);
        double c;
        Neuron candidateNeuron=null;
         
        for (Iterator<Neuron> iterator = neuronList.iterator(); iterator.hasNext();) {
            candidateNeuron = (Neuron) iterator.next();
            c=distance.calculateDistance(inputSample, candidateNeuron.getWeight(), dataSet);
            if(c < d){ // new BMU
                bestSoFar = candidateNeuron; //take a note of it
                d=c; // remember the distance
            }
        }
        return bestSoFar;
    }
 
 
 
 
 
    /**
     * This method generates a cluster vector. This vector represents 
     * the ID of the BMU of a given instance in position i. 
     * @param data data to generate the cluster vector.
     * @param distance distance which will be used to find the BMU.
     * @return cluster vector.
     */
    public int[] generateClusterVector(Instances data,Distance distance){
        int[] clusterVector = new int[data.numInstances()];
        for(int i=0;i<data.numInstances();i++){
            clusterVector[i] = clusterInstance(data.instance(i));
        }
        return clusterVector;
    }
 
    /**
     * return the respective bubble of activity that was pre-computed previously.
     * @param radius the radius of the bubble
     * @param bmu the best matching unit.
     * @return the corresponding bubble of activity
     */
    private ArrayList<Neuron> getPrecomputedBubble(long radius, Neuron bmu) {
        ArrayList<Neuron> bubbleOfActivity;
        Map<Integer, ArrayList<Neuron>> list = map.get(bmu);
 
        //BUG solved! 
        //solve bug in pre-computation of the bubble of activity.
        //System.out.println("list.size():"+list.size()+"  radius:"+radius+"  bmu:"+bmu.getId());
 
        //print list (debug)
 
        //  Set<Entry<Integer, ArrayList<Neuron>>> e = list.entrySet();
        //  for (Iterator iterator = e.iterator(); iterator.hasNext();) {
        //      Entry<Integer, ArrayList<Neuron>> entry = (Entry<Integer, ArrayList<Neuron>>) iterator
        //              .next();
        //      System.out.print(entry.getKey()+" -> ");
        //      ArrayList<Neuron> v = entry.getValue();
        //          for (Iterator iterator2 = v.iterator(); iterator2.hasNext();) {
        //              Neuron neuron = (Neuron) iterator2.next();
        //                  System.out.print(neuron.getId()+" ");
        //          }
        //      System.out.println();
        //  }
        //  System.out.println(list.get(new Integer((int)radius)));
 
 
        if(radius+1>list.size())
            bubbleOfActivity=list.get(new Integer((int)list.size()-1));
        else
            bubbleOfActivity=list.get(new Integer((int)radius));
 
//        if(bubbleOfActivity==null){
//            System.out.println("bubble situation!");  
//            System.out.println("bmu:"+bmu.getId());
//            System.out.println("radius:"+radius);
//        }
        return bubbleOfActivity;
    }
 
    /**
     * @return The root of the tree.
     */
    public Neuron getRoot(){
        return root;
    }
 
 
    /**
     * Gets all the prototype vectors of every neuron (i.e. the weights).
     * @return list with all weight vectors of every neuron.
     */
    public ArrayList<Instance> getWeights() {
        return weights;
    }
 
    /**
     * pre-compute the neighbors of the bubble of activity.
     */
    private void preComputeNeighbors() {
        map.clear();// reset the pre-computed bubble of activities.
        ArrayList<Neuron> bubbleOfActivity;
        Map<Integer, ArrayList<Neuron>> radiusToNeurons;
        for (Neuron neuron : neuronList ){
            int r=0;//local variable for the BoA 
            radiusToNeurons = new HashMap<Integer, ArrayList<Neuron>>() ;// a new radiusToNeurons mapping for the new neuron
            do{
                bubbleOfActivity = new ArrayList<Neuron>(); // a new bubble of activity
                bubbleOfActivity.add(neuron);//add current neuron being examined to the bubble of activity
                calculateNeighborhood(bubbleOfActivity, neuron, r, null);
                radiusToNeurons.put(r, bubbleOfActivity); //add bubble of the current neuron using a specific radius
                r++; //increase the radius of the BoA
            }
            while(bubbleOfActivity.size()<neuronList.size());
            map.put(neuron,radiusToNeurons); //add the mapping for all the bubbles around a particular neuron 
        }
        //printPrecomputedNeighborhoods();
    }
 
    
     /* Prints the vectors of the tree and adds the weight vectors of the 
     * neurons to the weights list.
     * @param node root tree
     * @param indent indentation level to print every branch in the tree*/
     
    public void printWeightVectors(Neuron node, int indent){  
        if ( node != null )
        {  if ( indent > 0 )
            for ( int k = 0; k < indent; k++ )
                System.out.print (" ");
        System.out.println(node.getWeightWithoutClass());
        weights.add(node.getWeight());
 
        for(Neuron child : node.getChildren()){
            printWeightVectors(child, indent+2);
        }
        }
    }
 /**
    private void printPrecomputedNeighborhoods() {
        System.out.println("Total of neurons pre-processed: "+map.size());
        System.out.println();
        Set<Entry<Neuron, Map<Integer, ArrayList<Neuron>>>> group = map.entrySet();
        for (Iterator it1 = group.iterator(); it1.hasNext();) {
            Entry<Neuron, Map<Integer, ArrayList<Neuron>>> groupEntry = (Entry<Neuron, Map<Integer, ArrayList<Neuron>>>) it1
                    .next();
            Map<Integer, ArrayList<Neuron>> e = groupEntry.getValue();
            Set<Entry<Integer, ArrayList<Neuron>>> neuronsEntry = e.entrySet();
            for (Iterator it2 = neuronsEntry.iterator(); it2.hasNext();) {
                Entry<Integer, ArrayList<Neuron>> entryRadius = (Entry<Integer, ArrayList<Neuron>>) it2.next();
                int r=entryRadius.getKey();
                System.out.print("neuron="+groupEntry.getKey().getId()+" \tradius="+r+" \tbubble={");
                int cont=1;
                for(Neuron n:entryRadius.getValue()){
                    System.out.print(""+n.getId());
                    if(cont!=entryRadius.getValue().size())
                        System.out.print(",");
                    cont++;
                }
                System.out.println("}");
            }
        }
    }*/
 
 
 
    /**
     * Training process to a given sample 
     * @param inputSample input sample to train 
     * @param root root of the tree
     * @param distance distance which is being used to the training process
     * @param radius radius which is being used to the training process
     * @param learningRate learning rate which is being used to the training process
     * @param dataSet dataset containing the input sample
     */
    public void train(Instance inputSample, Neuron root, 
            long radius,double learningRate){
 
        Neuron bmu = findBMU(root, inputSample, root);
 
 
        //ERROR!
        //calculateNeighborhood(bubbleOfActivity, root, radius, null);
        //The correct code is...
        //bubbleOfActivity.add(bmu);
        //calculateNeighborhood(bubbleOfActivity, bmu, radius, null);
 
        //but pre-computing the BoAs results in the following code
        //bubbleOfActivity=map.get(bmu).get(radius);
 
        ArrayList<Neuron> bubbleOfActivity = getPrecomputedBubble(radius, bmu);
 
//        if (bubbleOfActivity==null)
//            System.out.println("Bubble error!");
 
 
        //the update the respective neurons within the bubble of activity
        for(Neuron neuron : bubbleOfActivity){
            updateRule(neuron.getWeight(), learningRate, inputSample);
        }
    }
 
 
 
 
    /**
     * @param node the root of the subtree to be processed. If originally node is the root of the tree, 
     * then the whole tree is stored as an arrayList.
     * 
     */
    private void treeToArray(Neuron node){  
        if(node!=null){
            neuronList.add(node);
            for(Neuron child : node.getChildren()){
                treeToArray(child); 
            }
        }
    }
 
 
    /**
     * Update rule to the weight vector
     * @param weight weight vector to be updated 
     * @param learningRate learning rate to use in the update rule 
     * @param inputSample input sample to use in the update rule
     */
    public void updateRule(Instance weight, double learningRate,Instance inputSample){
        for(int i=0;i<weight.numAttributes();i++){
            double value = weight.value(i)+learningRate*(inputSample.value(i) - weight.value(i));
            weight.setValue(i,value);        
        }
 
    }
 
    public ArrayList<Neuron> getNeuronList(){
    	return neuronList;
    }
    
}