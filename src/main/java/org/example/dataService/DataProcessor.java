package org.example.dataService;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class DataProcessor {
    public Instances loadAndPreprocessData(String filePath) throws Exception {
        DataSource dataSource = new DataSource(filePath);
        Instances instances = dataSource.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);

        // Apply StringToWordVector filter
        StringToWordVector filter = new StringToWordVector();
        filter.setAttributeIndices("first-last");
        filter.setInputFormat(instances);

        return Filter.useFilter(instances, filter);
     }
}