package com.knsi.e2search.indexer;

import com.knsi.e2search.request.DataType;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class Indexer {
    private final IndexWriter writer;

    public Indexer(@Value("/Users/kaushikiyer/Documents/sampleIndex") String indexPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
        writer = new IndexWriter(indexDirectory, new IndexWriterConfig());
    }

    public long indexDocument(Map<String, DataType> config, Map<String, String> data) throws IOException {
        Document document = new Document();

        for(Map.Entry<String, DataType> entry: config.entrySet()) {
            if(DataType.TEXT.equals(entry.getValue())) {
                document.add(new TextField(entry.getKey(), data.get(entry.getKey()), Field.Store.YES));
            }
            else {
                document.add(new NumericDocValuesField(entry.getKey(),Long.valueOf(data.get(entry.getKey()))));
            }
        }
        return writer.addDocument(document);
    }

    public void commit() throws IOException {
        writer.commit();
    }
}
