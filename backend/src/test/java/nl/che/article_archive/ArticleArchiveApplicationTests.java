package nl.che.article_archive;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import nl.che.article_archive.model.Article;
import nl.che.article_archive.model.Author;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ArticleArchiveApplicationTests {

    @Test
    void contextLoads() throws IOException {
        RestClient restClient = RestClient
                .builder(HttpHost.create("http://localhost:9200"))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);


        ClassPathResource resource = new ClassPathResource("Van der Meiden - 2023 - After all they are still my parents.pdf");
        InputStream inputStream = resource.getInputStream();
        byte[] pdfBytes = inputStream.readAllBytes();
        String base64Encoded = Base64.encodeBase64String(pdfBytes);

        Article article = Article.builder()
                .id(UUID.fromString("cb25796f-d7ea-4f33-a5b6-980551cf4b90"))
                .name("How to be rich.")
                .authors(List.of(new Author("Pieter"), new Author("Jaap")))
                .tags(List.of("Cringy", "Master thesis", "quite good"))
                .publisher("ABN Amro")
                .attachmentBase64(base64Encoded)
                .build();
//
//        IndexResponse response = client.index(i -> i
//                .index("article")
//                .id(article.getId().toString())
//                .document(article)
//                .pipeline("attachment")
//        );
//
//        System.out.println(response.version());
//        System.out.println(response.result());
//        System.out.println(response.index());
//        System.out.println(response.id());


        SearchResponse<Article> searchResponse = client.search(s -> s
                        .index("article")
                        .query(q -> q.match(t -> t.field("attachment.content").query("still my parents")))
//                        .query(q -> q.match(t -> t.field("tags").query("Cringy")))
                , Article.class
        );
        System.out.println(searchResponse.hits().hits().size());
        System.out.println(searchResponse.hits().hits().stream().findFirst().orElse(null));

    }

}
