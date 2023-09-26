package nl.che.article_archive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // if fields exist in the document that we don't have in this class
public class Article {
    private UUID id;
    private String name;
    private List<Author> authors;
    private List<String> tags;
    private String publisher;
    @ToString.Exclude
    @JsonProperty("data")
    private String attachmentBase64;
    private Attachment attachment;
}
