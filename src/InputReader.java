import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//opens and reads the input from the standard input
public class InputReader {

	private String filePath = "";
	
	public InputReader(String filePath) {
		this.filePath = filePath;
	}
	
	//reads and records the input
	public Data readInput(int teacher_input_number, int output_neurons, int test_input_number) throws java.io.IOException {
		
		FileReader isr = new FileReader(filePath);
		//InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		ArrayList<String> raw_teacher_input = new ArrayList();
		ArrayList<String> raw_teacher_validation = new ArrayList();
		ArrayList<String> raw_test_input = new ArrayList();
		
		//teacher, validation, test input
		ArrayList<ArrayList<Double>> teacher_input;
		ArrayList<ArrayList<Double>> teacher_validation;
		ArrayList<ArrayList<Double>> test_input;
		
		String line;
		
		//reading the teacher input numbers
		for(int i = 0; i < teacher_input_number; i++) {
			line = br.readLine();
			raw_teacher_input.add(line);
		}
		
		//reading the teacher validation numbers
		for(int i = 0; i < teacher_input_number; i++) {
			
			line = br.readLine();
			raw_teacher_validation.add(line);
			
		}
		
		//reading the test numbers
		for(int i = 0; i < test_input_number; i++) {
			
			line = br.readLine();
			raw_test_input.add(line);
			
		}
		
		//conversions from raw string input to structured double data
		teacher_input = FromRawToStructured(raw_teacher_input);
		teacher_validation = FromRawToStructured(raw_teacher_validation);
		test_input = FromRawToStructured(raw_test_input);
		
		br.close();

		System.out.println("Input data read successful");
		return new Data(teacher_input, teacher_validation, test_input);
			
	}
	
	//method to handle data conversion from List<String> to List<List<Double>>
	private ArrayList<ArrayList<Double>> FromRawToStructured(ArrayList<String> input){
		
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();

		for (String s : input) {

			String[] parts = s.split("\t");
			ArrayList<Double> double_line = new ArrayList<Double>();

			for (String part : parts) {
				double_line.add(Double.valueOf(part));
			}

			result.add(double_line);

		}
		
		return result;
		
	}
	
}