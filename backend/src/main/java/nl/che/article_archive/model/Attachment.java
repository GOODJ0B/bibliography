package nl.che.article_archive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // if fields exist in the document that we don't have in this class
public class Attachment {
    public Date date;
    public String content_type;
    public String author;
    public String format;
    public Date modified;
    public String language;
    public Date metadata_date;
    public String creator_tool;
    public String content;
    public int content_length;
}

