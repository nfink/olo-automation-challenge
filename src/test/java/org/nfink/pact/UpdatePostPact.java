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
import org.nfink.tests.UpdatePost;

import java.io.IOException;

public class UpdatePostPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static final Post post = new Post(5, "test title", "test body", 6);
    private static final Post postDoesNotExist = new Post(999, "test title", "test body", 1);

    private PostsClient postsClient;

    @Before
    public void setup() {
        postsClient = new PostsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact validBody(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is a post with id %s", post.getId()))
                .uponReceiving(String.format("A request to update post with id %s", post.getId()))
                .path(PostsClient.getPutPath(post.getId().toString()))
                .headers("Content-type", "application/json")
                .body(PostUtil.buildBody(post))
                .method("PUT")
                .willRespondWith()
                .status(200)
                .body(PostUtil.buildBody(post))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact postDoesNotExist(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving(String.format("A request to update post with id %s that does not exist", postDoesNotExist.getId()))
                .path(PostsClient.getPutPath(postDoesNotExist.getId().toString()))
                .headers("Content-type", "application/json")
                .body(PostUtil.buildBody(postDoesNotExist))
                .method("PUT")
                .willRespondWith()
                .status(409)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "validBody")
    public void validBody_UpdatesPost() throws IOException {
        UpdatePost.validBody_UpdatesPost(postsClient, post);
    }

    @Test
    @PactVerification(fragment = "postDoesNotExist")
    public void postDoesNotExists_Returns409() {
        UpdatePost.postDoesNotExist_Returns409(postsClient, postDoesNotExist);
    }
}
