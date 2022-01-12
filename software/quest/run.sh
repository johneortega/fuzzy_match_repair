#!/bin/bash
ant clean
ant
java -cp QuEst++.jar shef.mt.SentenceLevelFeatureExtractor -tok -case true -lang english spanish -input input/source.en input/target.es -config config/config.sentence-level.properties
