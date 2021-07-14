package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.*;
import org.nfink.model.Post;
import org.nfink.pact.util.PostUtil;
import org.nfink.service.PostsClient;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GetPostsPact {
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
                    .body(PostUtil.buildArrayBody(post))
                .toPact();
    }

    @Test
    @PactVerification(fragment = "happyPath")
    public void returnsListOfPosts() throws IOException {
        when()
                .get(postsClient.getGetUrl())
        .then()
                .statusCode(200)
                .body(String.format("find { it.id == %s }", post.getId()), equalTo(post.toJsonObject()));
    }
}
