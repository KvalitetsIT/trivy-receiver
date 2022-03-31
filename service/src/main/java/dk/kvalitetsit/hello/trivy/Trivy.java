package dk.kvalitetsit.hello.trivy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Trivy {
    @JsonProperty("ArtifactName")
    private String artifactName;

    @JsonProperty("Results")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }
}
