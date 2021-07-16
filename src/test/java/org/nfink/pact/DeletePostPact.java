package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.service.PostsClient;
import org.nfink.tests.shared.DeletePost;

public class DeletePostPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static final Post post = new Post(7, "test title", "test body", 8);
    private static final Integer doesNotExist = 999;

    private PostsClient postsClient;

    @Before
    public void setup() {
        postsClient = new PostsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact validId(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is a post with id %s", post.getId()))
                .uponReceiving(String.format("A request to delete post %s", post.getId()))
                .path(PostsClient.getDeletePath(post.getId().toString()))
                .method("DELETE")
                .willRespondWith()
                .status(200)
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact doesNotExist(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving(String.format("A request to delete post %s that does not exist", doesNotExist))
                .path(PostsClient.getDeletePath(doesNotExist.toString()))
                .method("DELETE")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "validId")
    public void validId_returns200() {
        DeletePost.validId_returns200(postsClient, post.getId().toString());
    }

    @Test
    @PactVerification(fragment = "doesNotExist")
    public void postDoesNotExist_Returns404() {
        DeletePost.postDoesNotExist_Returns404(postsClient, doesNotExist.toString());
    }
}
