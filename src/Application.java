public class Application {
	
	public static void main(String[] args) throws java.io.IOException {
		
		//neural network parameters
		//number of input neurons 81 -> dim(input) = 81
		int inputNeurons = 81;
		//number of output neurons 1 -> dim(output) = 1 -> regression task
		int outputNeurons = 1;
		//between the input and the output there are the hidden layer(s) and their neurons' number
		int[] layerSizes = new int[]{inputNeurons, 35, outputNeurons};
		
		//tendency to learn
		double learningRate = 0.1;
		//how many times the network needs to repeat the learning process
		int numEpochs = 1000;

		//input parameters
		int teacherNumber = 2;
		int testNumber = 1;
		
		NeuralNetwork network = new NeuralNetwork(layerSizes, learningRate, numEpochs);
		Data data = new InputReader("dummy_data.txt").readInput(teacherNumber, outputNeurons, testNumber);
		data.normalize();
		network.train(data);
		network.predict(data);
		data.denormalize();
		data.writeResult();
		
	}
	
}
