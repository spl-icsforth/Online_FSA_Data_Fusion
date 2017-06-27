# FORTH-TRACE library

# Table of contents
1. [Introduction](#introduction)
2. [Modules Description](#modules)
3. [Data Fusion](#fusion)
4. [Datasets](#datasets)
5. [Useful methods](#useful)
6. [Installation Instructions](#execution)
7. [Licence](#licence)

## Introduction <a name="introduction"></a>
Online_FSA_Data_Fusion, is an Android extension of FORTH-TRACE library, a Java implemented software tool that covers the entire feature-level fusion chain applied in Human Activity Recognition (HAR) applications.
Our extension supports the parallel execution of the different modules (Acquisition, Segmentation, Feature Extraction, Feature Selection) for multiple sensor locations.
We also support the fusion of data at the feature level, considering two approaches; a Classification scheme and a Feature Selection Architecture.


The Machine Learning pipeline consists of the following modules.

*  **Acquisition**
*  **Segmentation**
*  **Feature Extraction**
*  **Feature Selection**

## Modules Description <a name="modules"></a>
* **Acquisition:** Collect data from wearable sensors and convert them into the appropriate data structures. This module supports multiple types of HAR datasets. Dynamic Acquisition is supported by acquiring batches of data instead of the whole dataset.
* **Segmentation:** Split data into sliding windows of observations. The key parameters of the Segmentation component is the size _w_ pf the sliding windows, as well as the overlapping factor _a_.
* **Feature Extraction:** Extract _M_ features for each data segment of each sensor modality. Currently, we support 11 types of statistical features and the Pairwise Correlation among sensor channels, listed below. 
    *  Mean
    *  Median
    *  Standard Deviation
    *  Variance
    *  Root Mean Square
    *  Skewness
    *  Kurtosis
    *  Interquartile Range
    *  Mean Crossing Rate
    *  Zero Crossing Rate
    *  Spectral Entropy
    *  Pairwise Correlation
    
* **Feature Selection:** Contains a pool of available Feature Selection Algorithms (FSA) in order to calculate the 
_R (R <<M)_ representative features for each segment. Currently 4 types of FSA are implemented. 
    * Unsupervised graph-based (Graph Clustering with Node Centrality, GCNC)
    * Unsupervised graph-based (Graph Clustering with Node Centrality and Representation Entropy, NCRE)


## Data Fusion Architectures <a name="fusion"></a>
For the Data Fusion we implement two approaches, both consisting of two stages.
The first stage of both approaches includes the parallel execution of the machine learning pipeline for multiple sensor locations, using parallel threads.
Then we consider
* a Classification Scheme
    * the features from multiple sensor locations are fused into a concatenated matrix, and are given as input to the kNN classifier,
* a Feature Selection Architecture
    * the features from multiple sensor locations are fused into a concatenated matrix
    * the inter-location Pairwise Correlation features are extracted based on the dominant sensor channels that occur from the features
    * a second stage of Feature Selection is applied.

## Datasets <a name="datasets"></a>
The library is designed to support any given HAR dataset.
Our implementation is testedon the FORTH-TRACE dataset.
* FORTH-TRACE dataset -- multiple locations - multiple modalities (Available at https://github.com/spl-icsforth/FORTH_TRACE_DATASET)

## Useful Methods <a name="useful"></a>
Besides the methods used that implement the Machine Learning pipeline, this project contains methods to write the feature matrixes and the experiment statistics into .csv for post-processing evaluation purposes.
These methods are located in the IO package of the project. It is recommended to save the .csv files in the files/ directory of the project.


## Installation Instructions <a name="execution"></a>
1. Download the project source files.
2. Import the project to Android Studio.
3. Add the following libraries into the build path (located in the libs/ directory of the project):
    * commons-math3-3.5.jar
    * javaml-0.1.7.jar
    * weka.java

## License <a name="licence"></a>
Use of this source code in publications must be acknowledged by referencing the following publications:

* Katerina Karagiannaki, Athanasia Panousopoulou, Panagiotis Tsakalides. A Benchmark Study on Feature Selection for Human Activity Recognition. ACM International Joint Conference on Pervasive and Ubiquitous Computing (UbiComp), ACM, 2016.
* Karagiannaki Katerina, Panousopoulou Athanasia and Tsakalides Panagiotis. An Online Feature Selection Architecture for Human Activity Recognition. Proceedings of IEEE International Conference on Acoustics, Speech and Signal Processing 2017 (ICASSP).