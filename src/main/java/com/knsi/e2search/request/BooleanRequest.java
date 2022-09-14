package com.knsi.e2search.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BooleanRequest extends SearchDocumentRequest {

    private List<? extends SearchDocumentRequest> should = new ArrayList<>();
    private List<? extends SearchDocumentRequest> must = new ArrayList<>();
    private List<? extends SearchDocumentRequest> mustNot = new ArrayList<>();

    @Override
    public Query getQuery() {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        should.forEach(query -> builder.add(query.getQuery(), BooleanClause.Occur.SHOULD));
        must.forEach(query -> builder.add(query.getQuery(), BooleanClause.Occur.MUST));
        mustNot.forEach(query -> builder.add(query.getQuery(), BooleanClause.Occur.MUST_NOT));
        return builder.build();
    }
}
