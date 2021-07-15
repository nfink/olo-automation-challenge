package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.pact.util.PostUtil;
import org.nfink.service.PostsClient;
import org.nfink.tests.CreatePost;

import java.io.IOException;

public class CreatePostPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static final Post post = new Post(null, "test title", "test body", 4);
    private static final Post createdPost = new Post(101, post.getTitle(), post.getBody(), post.getUserId());
    private static final Post missingUserId = new Post(null, "test title", "test body", null);

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

    @Pact(consumer = "Olo")
    public RequestResponsePact incorrectContentType(PactDslWithProvider builder) throws JsonProcessingException {
        return builder
                .given("default")
                .uponReceiving("A request to create a post without an application/json content-type header")
                .path(PostsClient.getPath())
                .headers("Content-type", "text/plain")
                .body(new ObjectMapper().writeValueAsString(post))
                .method("POST")
                .willRespondWith()
                .status(415)
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact missingUserId(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving("A request to create a post without a userId in the body")
                .path(PostsClient.getPath())
                .headers("Content-type", "application/json")
                .body(PostUtil.buildBody(missingUserId))
                .method("POST")
                .willRespondWith()
                .status(400)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "validBody")
    public void validBody_CreatesPost() {
        Assert.assertEquals(createdPost, CreatePost.validBody_CreatesPost(postsClient, post));
    }

    @Test
    @PactVerification(fragment = "incorrectContentType")
    public void incorrectContentType_Returns415() throws IOException {
        CreatePost.incorrectContentType_Returns415(postsClient, post);
    }

    @Test
    @PactVerification(fragment = "missingUserId")
    public void missingUserId_Returns400() {
        CreatePost.missingUserId_Returns400(postsClient, missingUserId);
    }
}
