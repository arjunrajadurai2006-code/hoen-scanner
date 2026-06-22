package com.skyscanner.resources;

import com.skyscanner.core.Search;
import com.skyscanner.core.SearchResult;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final List<SearchResult> searchResults;

    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @POST
    public List<SearchResult> search(Search search) {

        return searchResults.stream()
                .filter(result ->
                        result.getCity().equalsIgnoreCase(search.getCity()))
                .collect(Collectors.toList());
    }
}