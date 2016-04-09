# TTOSOM REST service
This is a RESTful implementation of TTOSOM using Maven and Spring MVC. 

[Here](http://www.sciencedirect.com/science/article/pii/S002002551100212X) you can find extra information about the algorithm.

## Requirements

* [Maven 3.](https://maven.apache.org/install.html)
* Java 8.
* Tomcat or any application server.


## How to build and deploy

Once you have installed Maven, build the application by doing **mvn clean install** and this will generate the **war file**. Once you have done this deploy it in the application server that you're using. 

**Note:** If you cant to import this code in eclipse use **mvn eclipse:eclipse**.

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

**CURRENTLY IN DEVELOPMENT (NOT FULLY IMPLEMENTED)**
* **Folds:** Number of folds to be used in **k-cross validation**
* **Seed value:** Seed value to be used in the random generator.

## Operation modes

This algorithm is capable of doing classification, clustering and regression. At the moment we have only implemented classification and clustering, which are documented in the following sections.

Also there's an extra option which gives us the weights of each node in the tree.

## Request sample

### Classification

**Endpoint**
POST /ttosomservice/ttosom/classification

**Headers**
Content-Type: application/json

**Request**

This is only dummy data (by example, the tree is not consistent).
```json
{
	"dataSetUrl": "http://www.helloworld.com/dataset.arff",
	"treeAsArray": [3, 3, 3, 0, 0, 0],
	"iterations": 1000,
	"initialRadius": 8,
	"finalRadius": 0,
	"initialLearningRate": 0.9,
	"finalLearningRate": 0.0,
	"distanceFunction": "Euclidean"
}
```
**Response**

In this dataset we had 3 instances to be classified and here is the classification. 

```json
{
	"0": [5.1, 3.5, 1.4, 0.2, 0.0],
	"1": [7.0, 3.2, 4.7, 1.4, 1.0],
	"2": [6.3, 3.3, 6.0, 2.5, 2.0]
}
```

### Clustering
This is only dummy data (by example, the tree is not consistent).

**Endpoint**
POST /ttosomservice/ttosom/clustering

**Headers**
Content-Type: application/json

**Request**

The same one for classification.

```json
{
	"dataSetUrl": "http://www.helloworld.com/dataset.arff",
	"treeAsArray": [3, 3, 3, 0, 0, 0],
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

### Weights of the tree
This is only dummy data (by example, the tree is not consistent).

**Endpoint**
POST /ttosomservice/ttosom/vectors

**Headers**
Content-Type: application/json

**Request**

The same one for classification and clustering.

```json
{
	"dataSetUrl": "http://www.helloworld.com/dataset.arff",
	"treeAsArray": [3, 3, 3, 0, 0, 0],
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
{"0":"5.8741908524843565,2.860523366886409,4.238965602256368,1.4668688987061103",
"1":"5.1278948867565,3.169202564326191,1.97979569904357,0.45193456736620985",
"2":"5.084161393024253,3.5777183253719906,1.4414577697832849,0.28306871135947653",
"3":"4.808170839350214,3.2060549039800934,1.4378375183211347,0.19934010179505657",
"4":"5.322828977089305,3.839812673146597,1.4966434613098518,0.31384220332443974",
"5":"5.0845496127278125,3.470736011875208,1.5029018427162166,0.274515328491678",
"6":"5.334843432217271,2.4769957846912956,3.4299338983725574,1.0140655620384948",
"7":"5.5598127262718275,2.5004443875864135,3.836530512023161,1.1142453037800593",
"8":"5.413732078058179,2.4415704956272437,3.60345719158967,1.0658359993355115",
"9":"5.272407415260005,2.3849963552644335,3.4680649068027765,1.0623286549061746",
"10":"5.178973337694091,2.941625298287521,2.512275036737307,0.7185041975901588",
"11":"5.353886912540957,2.9795853760180266,3.0407456979442493,0.9420306859843103",
"12":"5.353886912540957,2.9795853760180266,3.0407456979442493,0.9420306859843103",
"13":"5.353886912540957,2.9795853760180266,3.0407456979442493,0.9420306859843103",
"14":"6.580380168234193,2.9429746045603706,5.2632667635976205,1.8698032133701605",
"15":"6.570225961360529,2.958790104927367,4.977109347755145,1.638913960690107",
"16":"6.727357171969606,2.927463101804085,4.738786314035317,1.4036111434461342",
"17":"6.697862752639755,3.0244428365114437,4.694463336779689,1.4441478099459508",
"18":"6.737767363183547,2.957377014889016,4.753600419081492,1.424203180710611",
"19":"6.772373462310406,3.0396576616194158,5.711766491562934,2.137779278954336",
"20":"6.670356875334524,3.182884295897704,5.574133356783446,2.29310970804397",
"21":"7.1420019817567875,2.9620946972289706,5.973574727719291,2.0539854098972734",
"22":"6.710049471462079,2.8928283676337707,5.582923356888402,2.061672648607949",
"23":"6.31414958533403,2.8143536332688464,4.844202273672224,1.5467303731892126",
"24":"6.173779864812025,2.848934728877994,4.608754690015881,1.3774082974359527",
"25":"6.235366646203621,2.6421704955173886,4.695926404706619,1.442080105851734",
"26":"6.2979081539177555,2.776884164631634,4.662963947994993,1.453495979662957",
"27":"5.825491636398072,2.781911680024963,4.775546125642854,1.7758330221154548",
"28":"5.507103933312642,2.7342182972376525,4.487214931996308,1.6568145057042423",
"29":"5.344180460459436,2.7008822188324135,4.206485064804304,1.5284596480003805",
"30":"5.393608806391629,2.7103035979510515,4.254599325742407,1.546393573100072",
"31":"5.393608806391629,2.7103035979510515,4.254599325742407,1.546393573100072",
"32":"5.89290808631698,2.8553788314709676,4.843957701060405,1.7374828760003518",
"33":"5.781104903811907,2.8880603905249194,4.455988409295422,1.5435778110193288",
"34":"5.797064871858336,2.9456952883611263,4.4308576904687005,1.564774064636068",
"35":"5.7749443800267946,2.896174814725711,4.423320986955579,1.5609343399781843",
"36":"5.813114212740662,2.7999646253914547,4.730240548527579,1.6737047253468047",
"37":"5.673072010074538,2.8478860005678266,4.1401833537512625,1.3627336070243101",
"38":"5.676838992101082,2.8208208818847753,4.094543578837557,1.4022349840465187",
"39":"5.676838992101082,2.8208208818847753,4.094543578837557,1.4022349840465187"}
```

