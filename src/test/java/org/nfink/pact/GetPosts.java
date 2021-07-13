package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.common.mapper.TypeRef;
import org.junit.*;
import org.nfink.model.Post;
import org.nfink.service.PostsClient;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GetPosts {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static Post post = new Post(1, "test title", "test body", 2);

    private PostsClient postsClient;

    @Before
    public void setup() {
        postsClient = new PostsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact happyPath(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving("A request for all posts")
                    .path(PostsClient.getPath())
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body(buildBody(post))
                .toPact();
    }

    @Test
    @PactVerification(fragment = "happyPath")
    public void returnsListOfPosts() {
        List<Post> response = when()
                .get(postsClient.getGetUrl())
        .then()
                .statusCode(200)
        .extract()
                .as(new TypeRef<List<Post>>() {});

        List<Post> expected = Arrays.asList(post);
        Assert.assertEquals(expected, response);
    }

    private static DslPart buildBody(Post post) {
        return PactDslJsonArray.arrayMinLike(1)
                .integerType("id", post.getId())
                .stringType("title", post.getTitle())
                .stringType("body", post.getBody())
                .integerType("userId", post.getUserId())
                .closeObject();
    }
}
