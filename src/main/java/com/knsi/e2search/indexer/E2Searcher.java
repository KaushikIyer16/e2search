package com.knsi.e2search.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class E2Searcher {
    private final Directory directory;

    public E2Searcher(@Value("/Users/kaushikiyer/Documents/sampleIndex") String directoryPath) throws IOException {
        directory = FSDirectory.open(Path.of(directoryPath));
    }

    public int getTotalDocument() throws IOException {
        IndexReader reader = DirectoryReader.open(directory);
        return reader.numDocs();
    }

    public Map<String, String> getDocs(String name, String value) throws IOException {
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Term t = new Term(name, value);
        Query query = new TermQuery(t);
        TopDocs docs = searcher.search(query, 10);
        Document document = searcher.doc(docs.scoreDocs[0].doc);
        Map<String, String> retMap = new HashMap<>();
        document.forEach(indexableField -> retMap.put(indexableField.name(), indexableField.stringValue()));
        return retMap;
    }

    public List<Map<String, String>> getAllDocs() throws IOException {
        List<Map<String, String>> retVal= new ArrayList<>();
        IndexReader reader = DirectoryReader.open(directory);
        for (int i=0; i<reader.maxDoc(); i++) {

            Document document = reader.document(i);
            Map<String, String> retMap = new HashMap<>();
            document.forEach(indexableField -> retMap.put(indexableField.name(), indexableField.stringValue()));
            retVal.add(retMap);
        }
        return retVal;
    }
}
