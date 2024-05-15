package org.example.dataService;

import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import static org.example.utils.Location.FILTER_PATH;

public class DataProcessor {

    private final String filterPath;
    private StringToWordVector filter;

    public DataProcessor(String filterPath) {
        this.filterPath = filterPath;
    }

    public Instances loadAndPreprocessData(String dataPath) throws Exception {
        DataSource dataSource = new DataSource(dataPath);
        Instances instances = dataSource.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
        return instances;
    }

    public Instances createAndApplyFilter(Instances instances) throws Exception {
        filter = new StringToWordVector();
        filter.setAttributeIndices("first-last");
        filter.setInputFormat(instances);
        Instances filterData = Filter.useFilter(instances, filter);
        SerializationHelper.write(filterPath, filter);
        System.out.println("===== Model saved to " + filterPath + " =====");
        return filterData;
    }

    public Instances applyFilter(Instances data) throws Exception {
        filter =  (StringToWordVector) SerializationHelper.read(FILTER_PATH);
        return Filter.useFilter(data, filter);
    }
}