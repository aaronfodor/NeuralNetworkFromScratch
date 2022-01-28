# Java neural network from scratch


This project builds a basic regression model to predict the boiling points of complex molecules (Artificial Intelligence (VIMIAC10) course home assignment, 2018). The intention of this task was to learn how to construct a neural network and learn its main concepts. To run the sample, start `Application` -> `main()`.

When the app is running, it first trains the network, then makes prediction.

![output](https://user-images.githubusercontent.com/37120889/151535165-f78052f0-f018-43a3-9536-d0d63feeccc8.PNG)

## Data structure

To define how many molecules are in the file, set `teacherNumber` and `testNumber` variables in `main()`. To set molecule element multiplicity, set `inputNeurons`.

- The first part of the data is the molecule's structure. There are 81 elements in a molecule (in the starter example). Each type of them (hydrogen, oxigen, neutron, etc...) has an arbitrary Id (which is learnt by the network), so a molecule is a sequence of 81 IDs separated by tabs. The *dummy_data.txt* has a few molecule lines.
- Under the molecule descriptors, the boiling points can be seen (one integer per line, same number of lines as the molecule lines).

The same molecule descriptor structure is repeated to represent the ones to calculate the boiling points of. Its multiplicity may differ from the train items.

There is a *dummy_data.txt* file to exemplify the required input.



## Neural network

The network can be tweaked in `main()` (input size, output size, number of hidden layers & their neurons, learning rate, epoch number).
