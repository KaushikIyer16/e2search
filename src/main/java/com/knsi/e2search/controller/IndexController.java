package com.knsi.e2search.controller;

import com.knsi.e2search.indexer.E2Searcher;
import com.knsi.e2search.indexer.Indexer;
import com.knsi.e2search.request.CreateConfigRequest;
import com.knsi.e2search.request.CreateDocumentRequest;
import com.knsi.e2search.request.DataType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController()
@RequestMapping("index")
public class IndexController {

    private final Map<String, Map<String, DataType>> configBean;
    private final Indexer indexer;
    private final E2Searcher searcher;

    public IndexController(@Qualifier("configBean") Map<String, Map<String, DataType>> configBean, Indexer indexer, E2Searcher searcher) {
        this.configBean = configBean;
        this.indexer = indexer;
        this.searcher = searcher;
    }

    @PostMapping("/config")
    public ResponseEntity<Map<String, String>> createTypeMapping(@RequestBody CreateConfigRequest request) {
        if(Objects.isNull(request.getConfig())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        configBean.put(request.getType(), request.getConfig());
        HashMap<String, String> map = new HashMap<>();
        map.put("message", request.getType()+" type has been added");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, String>> indexDocument(@RequestBody CreateDocumentRequest request) throws IOException {
        if(Objects.isNull(request.getData())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        indexer.indexDocument(configBean.get(request.getType()), request.getData());
        indexer.commit();
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "docuemnt has been indexed");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, String>> getNumberOfDocs() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "there are "+searcher.getTotalDocument()+" number of documents");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/search/{term}/{value}")
    public ResponseEntity<List<Map<String, String>>> getDocuments(@PathVariable String term, @PathVariable String value) throws IOException {
        return new ResponseEntity<>(searcher.getDocs(term, value), HttpStatus.OK);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<Map<String, String>>> getDocuments() throws IOException {
        return new ResponseEntity<>(searcher.getAllDocs(), HttpStatus.OK);
    }
}
