package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.pact.util.PostUtil;
import org.nfink.service.PostsClient;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class CreatePostPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static Post post = new Post(null, "test title", "test body", 4);
    private static Post createdPost = new Post(101, post.getTitle(), post.getTitle(), post.getUserId());

    private PostsClient postsClient;

    @Before
    public void setup() {
        postsClient = new PostsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact validBody(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving("A request to create a post")
                .path(PostsClient.getPath())
                .headers("Content-type", "application/json")
                .body(PostUtil.buildBody(post))
                .method("POST")
                .willRespondWith()
                .status(201)
                .body(PostUtil.buildBody(createdPost))
                .toPact();
    }

    @Test
    @PactVerification(fragment = "validBody")
    public void validBody_CreatesPost() throws IOException {
        given()
                .contentType(ContentType.JSON)
                .body(post)
        .when()
                .post(postsClient.getPostUrl())
        .then()
                .statusCode(201)
                .body("$", equalTo(createdPost.toJsonObject()));
    }
}
