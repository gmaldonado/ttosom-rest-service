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
	"dataSetUrl": "http://www.cas.mcmaster.ca/~cs4tf3/iris.arff",
	"treeAsArray": [3, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0],
	"iterations": 1000,
	"initialRadius": 8,
	"finalRadius": 0,
	"initialLearningRate": 0.9,
	"finalLearningRate": 0.0,
	"distanceFunction": "Euclidean"
}
```


### Clustering
