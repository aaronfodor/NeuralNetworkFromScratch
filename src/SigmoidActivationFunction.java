//Sigmoid activation function
//output = 1 / (1 + e^(-slope * x) )
public class SigmoidActivationFunction implements ActivationFunction{

	private double slope;
	
	public SigmoidActivationFunction() {
		this.slope = 1;
	}
	
	@Override
	public double CalculateDerivative(double input) {
		double temp = this.CalculateOutput(input);
		return (temp * (1 - temp));
	}

	@Override
	public double CalculateOutput(double input) {
		double denominator = 1 + Math.exp( -slope * input );
		return (1d / denominator);
	}
	
}
