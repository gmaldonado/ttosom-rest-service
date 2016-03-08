# TTOSOM REST service
This is a RESTful implementation of TTOSOM using Maven and Spring MVC.

## Requirements

* Maven 3
* Java 8

## Parameters
Here is the full description of all possible parameters to be used with the algorithm. The parameters will vary if this is we are doing classification or clustering. 

* **Dataset URL:** Represents the URL of the dataset to be used. This **MUST** be an ARFF file.
* **Tree description (topology):** This is the tree description specified in one line as an array, specifying the number of childs per node.
* **Iterations:** Total number of iterations.
* **Initial radius:** Initial radius of the **Bubble of Activity (BoA)**.
* **Final radius:** Final radius of the **BoA**.
* **Initial learning rate:** Initial learning rate.
* **Final learning rate:** Final learning rate.
* **Distance function:** Distance function to be used. At the moment this can be "Euclidean" or "Manhattan"

**Currently in development**
* **Folds:** Number of folds to be used in **k-cross validation**
* **Seed value:** Seed value to be used in the random generator.

## Operation modes

## Request sample

### Classification

**Endpoint**
POST /ttosomservice/ttosom/classification

**Headers**
Content-Type: application/json

**Request**
```json
{
	"dataSetUrl": "http://www.helloworld.com/dataset.arff",
	"treeAsArray": [3, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0],
	"iterations": 1000,
	"initialRadius": 8,
	"finalRadius": 0,
	"initialLearningRate": 0.9,
	"finalLearningRate": 0.0,
	"distanceFunction": "Euclidean"
}
```
**Response**

Here we have 3 instances to be classified (without label).

```json
{
	"0": [5.1, 3.5, 1.4, 0.2, 0.0],
	"1": [7.0, 3.2, 4.7, 1.4, 1.0],
	"2": [6.3, 3.3, 6.0, 2.5, 2.0]
}
```

### Clustering

**Endpoint**
POST /ttosomservice/ttosom/clustering

**Headers**
Content-Type: application/json

**Request**

The same one for classification.

```json
{
	"dataSetUrl": "http://www.helloworld.com/dataset.arff",
	"treeAsArray": [3, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0],
	"iterations": 1000,
	"initialRadius": 8,
	"finalRadius": 0,
	"initialLearningRate": 0.9,
	"finalLearningRate": 0.0,
	"distanceFunction": "Euclidean"
}
```
**Response**
```json
{"cluster vector":[7,12,13,13,9,8,7,7,10,12,8,7,10,10,8,8,8,9,8,8,7,9,9,7,7,12,7,7,7,13,13,7,8,8,12,13,6,12,10,7,7,11,13,9,8,12,8,13,6,7,29,28,29,22,31,21,28,14,29,21,15,30,20,30,0,29,30,21,37,19,35,30,37,30,31,29,29,27,30,0,20,20,21,36,30,30,29,37,30,21,21,30,21,14,21,30,30,30,23,21,3,38,2,33,3,4,38,4,32,5,33,32,3,38,34,3,33,4,4,37,3,38,4,32,3,4,32,35,34,4,4,4,34,27,36,4,3,33,35,3,3,3,38,3,3,3,38,34,3,35]}
```

