//represents a single neuron which stores and calculates its data
public class Neuron {

	private String Id;
	private double output;
	private double derivative;
	private double bias;
	private double error;
	//for output and derivative calculations
	private ActivationFunction activation_function;
	
	public Neuron(String id) {
		this.Id = id;
		this.activation_function = new SigmoidActivationFunction();
	}
	
	public String GetId() {
		return this.Id;
	}
	
	public void SetOutput(double value) {
		this.output = value;
	}
	
	public void CalculateOutput(double input) {
		this.output = this.activation_function.CalculateOutput(input);
	}
	
	public double GetOutput() {
		return this.output;
	}
	
	public void CalculateDerivative(double input) {
		this.derivative = this.activation_function.CalculateDerivative(input);
	}
	
	public double GetDerivative() {
		return this.derivative;
	}
	
	public void SetBias(double value) {
		this.bias = value;
	}
	
	public double GetBias() {
		return this.bias;
	}
	
	public void SetError(double value) {
		this.error = value;
	}
	
	public double GetError() {
		return this.error;
	}
	
}
