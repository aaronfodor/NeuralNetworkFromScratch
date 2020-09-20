//represents a differentiable function used by the neural network
public interface ActivationFunction {

	double CalculateOutput(double input);
	
	double CalculateDerivative(double input);
	
}
