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
import org.nfink.pact.util.PostUtil;
import org.nfink.service.PostsClient;
import org.nfink.tests.GetPost;

import java.io.IOException;

public class GetPostPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static final Post post = new Post(2, "test title", "test body", 3);
    private static final Integer noDataId = 999;
    private static final String invalidId = "abcd";

    private PostsClient postsClient;

    @Before
    public void setup() {
        postsClient = new PostsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact valid(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is a post with id %s", post.getId()))
                .uponReceiving(String.format("A request for post with id %s", post.getId()))
                .path(PostsClient.getPath() + "/" + post.getId())
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(PostUtil.buildBody(post))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact noData(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving(String.format("A request for post with id %s that does not exist", noDataId))
                .path(PostsClient.getPath() + "/" + noDataId)
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact invalidId(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving(String.format("A request for post with invalid id %s", invalidId))
                .path(PostsClient.getPath() + "/" + invalidId)
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "valid")
    public void validId_ReturnsPost() throws IOException {
        GetPost.validId_ReturnsPost(postsClient, post);
    }

    @Test
    @PactVerification(fragment = "noData")
    public void noData_Returns404() {
        GetPost.noData_Returns404(postsClient, noDataId.toString());
    }

    @Test
    @PactVerification(fragment = "invalidId")
    public void invalidId_Returns404() {
        GetPost.invalidId_Returns404(postsClient, invalidId);
    }
}
