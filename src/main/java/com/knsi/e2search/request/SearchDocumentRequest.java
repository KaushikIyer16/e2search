package com.knsi.e2search.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.lucene.search.Query;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TermRequest.class, name = "term"),
        @JsonSubTypes.Type(value = BooleanRequest.class, name = "bool")
})
public abstract class SearchDocumentRequest {
    public abstract Query getQuery();
}
