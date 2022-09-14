package com.knsi.e2search.controller;

import com.knsi.e2search.indexer.E2Searcher;
import com.knsi.e2search.request.SearchDocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("search")
public class SearchController {

    private final E2Searcher searcher;

    @Autowired
    public SearchController(E2Searcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/")
    public ResponseEntity<List<Map<String, String>>> getTermResponse(@RequestBody SearchDocumentRequest request) throws IOException {
        return new ResponseEntity<>(searcher.getDocs(request.getQuery(),10), HttpStatus.OK);
    }

}
