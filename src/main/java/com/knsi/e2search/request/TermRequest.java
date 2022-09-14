package com.knsi.e2search.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TermRequest extends SearchDocumentRequest{

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    @Override
    public Query getQuery() {
        Term term = new Term(this.key, this.value);
        return new TermQuery(term);
    }
}
