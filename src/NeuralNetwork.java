import java.util.ArrayList;
import java.util.Random;

//represents the network
public class NeuralNetwork {
	
	private int numLayers;
	private int[] layerSizes;
	//tendency to learn
	private final double learningRate;
	//how many times the network needs to repeat the learning process
	private final int epochs;
	private double[][][] weights;
	private Neuron[][] neurons;
	//for random number generating - static final to be immutable
	private static final Random random = new Random(1);
	
	public NeuralNetwork(int[] layers, double learningRate, int repetition_factor) {
		
		this.learningRate = learningRate;
		this.epochs = repetition_factor;
		this.layerSizes = layers;
		this.numLayers = layerSizes.length;
		
		weights = new double[numLayers][][];
		neurons = new Neuron[numLayers][];
		
		for(int layer_counter = 0; layer_counter < numLayers; layer_counter++) {
			
			neurons[layer_counter] = new Neuron[layerSizes[layer_counter]];
			
			for(int neuron_counter = 0; neuron_counter < layerSizes[layer_counter]; neuron_counter++) {
				neurons[layer_counter][neuron_counter] = new Neuron("layer" + layer_counter + " - neuron" + neuron_counter);
			}
			
			if(layer_counter > 0){
				weights[layer_counter] = CreateLayerConnection(layerSizes[layer_counter], layerSizes[layer_counter-1]);
			}
			
		}

		System.out.println("Network created");
		
	}
	
	//returns the result to the given input
	private ArrayList<Double> Calculate(ArrayList<Double> input) {
		
		//setting the output of the input layer neurons to the given input
		for(int neuron_counter = 0; neuron_counter < layerSizes[0]; neuron_counter++) {
			neurons[0][neuron_counter].SetOutput(input.get(neuron_counter));
		}
		
		//setting the output of the hidden- and output layer neurons
		for(int layer_counter = 1; layer_counter < numLayers; layer_counter++){
			
			for(int neuron_counter = 0; neuron_counter < layerSizes[layer_counter]; neuron_counter++){
				
				double summed_input = 0;
				
				//collecting the previous layer output multiplied by the weights to those neurons
				for(int prevneuron_counter = 0; prevneuron_counter < layerSizes[layer_counter-1]; prevneuron_counter++){
					
					summed_input+= neurons[layer_counter-1][prevneuron_counter].GetOutput() * weights[layer_counter][neuron_counter][prevneuron_counter];
					
				}
				
				//adding the bias to the collected value
				summed_input+= neurons[layer_counter][neuron_counter].GetBias();
				
				//setting the output and the derivative of the current neuron
				neurons[layer_counter][neuron_counter].CalculateOutput(summed_input);
				neurons[layer_counter][neuron_counter].CalculateDerivative(summed_input);
				
			}
			
		}
		
		ArrayList<Double> result = new ArrayList<Double>();
		
		//writes the answers to the result list
		for(int i = 0; i < layerSizes[numLayers -1]; i++) {
			
			result.add(neurons[numLayers -1][i].GetOutput());
			
		}
		
		return result;
	}
	
	//teaches the given data to the network
	public void train(Data data) {

		System.out.println("*Training started*");
		long start = System.currentTimeMillis();
		
		//the network needs to repeat the learning process
		for(int repetitionCounter = 0; repetitionCounter < this.epochs; repetitionCounter++) {
		
			for(int trainCounter = 0; trainCounter < data.GetTeacher().size(); trainCounter++) {
				
				ArrayList<Double> currentTrainItem = data.GetTeacher().get(trainCounter);
				ArrayList<Double> currentValidationItem = data.GetValidation().get(trainCounter);
				
				Calculate(currentTrainItem);
				BackpropagateError(currentValidationItem);
				UpdateWeights();
				
			}
		
		}

		long elapsedTime = System.currentTimeMillis() - start;
		System.out.printf("*Training finished* Duration: %d [ms]", elapsedTime);
		System.out.println();
		
	}
	
	//predicts the result from the input
	public void predict(Data data) {
		
		for(int test_counter = 0; test_counter < data.GetTest().size(); test_counter++) {
			
			ArrayList<Double> current_test_line = data.GetTest().get(test_counter);
			ArrayList<Double> current_result_line = new ArrayList<Double>();
			
			current_result_line = Calculate(current_test_line);
			data.GetResult().add(current_result_line);
			
		}
		
	}
	
	//calculates and back propagates the error to the teacher data
	private void BackpropagateError(ArrayList<Double> validation) {
		
		//calculates the error of the output layer neurons
		for(int neuron_counter = 0; neuron_counter < layerSizes[numLayers -1]; neuron_counter++){
			
			double expected_result = validation.get(neuron_counter);
			neurons[numLayers -1][neuron_counter].SetError((neurons[numLayers -1][neuron_counter].GetOutput() - expected_result) * neurons[numLayers -1][neuron_counter].GetDerivative());
			
		}
		
		//calculates the error of the hidden layer neurons
		for(int layer_counter = numLayers -2; layer_counter > 0; layer_counter--){
			
			for(int neuron_counter = 0; neuron_counter < layerSizes[layer_counter]; neuron_counter++){
				
				double summed_error = 0;
				
				//collecting the next layer errors multiplied by the weights to those neurons
				for(int nextneuron_counter = 0; nextneuron_counter < layerSizes[layer_counter+1]; nextneuron_counter++){
					
					summed_error+= neurons[layer_counter+1][nextneuron_counter].GetError() * weights[layer_counter+1][nextneuron_counter][neuron_counter];
					
				}
				
				//multiply the value by the derivative of the current neuron
				neurons[layer_counter][neuron_counter].SetError(summed_error * neurons[layer_counter][neuron_counter].GetDerivative());
				
			}
			
		}
		
	}
	
	//updates the weights according to the error and the learning rate of the network
	private void UpdateWeights() {
		
		for(int layer_counter = 1; layer_counter < numLayers; layer_counter++){
			
			for(int neuron_counter = 0; neuron_counter < layerSizes[layer_counter]; neuron_counter++){
				
				Neuron current = neurons[layer_counter][neuron_counter];
				double delta = -learningRate * current.GetError();
				double previous_bias = current.GetBias();
				
				neurons[layer_counter][neuron_counter].SetBias(previous_bias + delta);
				
				//updating the weights between the current neuron and the previous layer's neurons
				for(int prevneuron_counter = 0; prevneuron_counter < layerSizes[layer_counter-1]; prevneuron_counter++){
					
					weights[layer_counter][neuron_counter][prevneuron_counter]+= delta * neurons[layer_counter-1][prevneuron_counter].GetOutput();
					
				}
				
			}
			
		}
		
	}

	//returns a 2 dimensional array of random weighted connections from layer_a to layer_b
    private static double[][] CreateLayerConnection(int layer_a_size, int layer_b_size) {
    	
        double[][] layers_connection = new double[layer_a_size][layer_b_size];
        
        for(int i = 0; i < layer_a_size; i++){
        	layers_connection[i] = CreateRandom(layer_b_size);
        }
        
        return layers_connection;
        
    }
    
    //returns an array of random numbers in the given size between 0 and 1
    private static double[] CreateRandom(int size) {
    	
        double[] random_weights = new double[size];
        
        for(int i = 0; i < size; i++){
        	random_weights[i] = randomVal();
        }
        
        return random_weights;
        
    }

    //returns a random number between 0 and 1
    private static double randomVal() {
    	return random.nextGaussian();
    }
    
}
