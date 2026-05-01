package com.clustering;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.StopAnalyzer;

import weka.core.stopwords.StopwordsHandler;

class StopWordsHandler implements StopwordsHandler {

    public String[] getStopwords() {
        CharArraySet stopwordSet = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
        String[] stopwordsArray = stopwordSet.toArray(new String[0]);
        return stopwordsArray;
    }

	@Override
	public boolean isStopword(String word) {
		// TODO Auto-generated method stub
		return false;
	}
}