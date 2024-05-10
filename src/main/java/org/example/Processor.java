package org.example;

import org.example.analysis.Analyzer;
import org.example.preProcess.PreProcessor;

public class Processor {
    private PreProcessor preProcessor;
    private Analyzer analyzer;

    public Processor() {
        this.preProcessor = new PreProcessor();
        this.analyzer = new Analyzer();
    }

    public PreProcessor getPreProcessor() {
        return preProcessor;
    }

    public void setPreProcessor(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }
}
