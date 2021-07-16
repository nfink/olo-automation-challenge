package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.nfink.model.Comment;
import org.nfink.pact.util.CommentUtil;
import org.nfink.service.CommentsClient;
import org.nfink.tests.shared.CreateComment;

public class CreateCommentPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static final Comment comment = new Comment(1, null, "test name", "test@example.com", "test body");
    private static final Comment createdComment = new Comment(comment.getPostId(), 5, comment.getName(), comment.getEmail(), comment.getBody());
    private static final Comment postDoesNotExist = new Comment(999, null, "test name", "test@example.com", "test body");

    private CommentsClient commentsClient;

    @Before
    public void setup() {
        commentsClient = new CommentsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact validBody(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is a post with id %s", comment.getPostId()))
                .uponReceiving(String.format("A request to create a comment for post %s", comment.getPostId()))
                .path(CommentsClient.getPostPath(comment.getPostId().toString()))
                .headers("Content-type", "application/json")
                .body(CommentUtil.buildBody(comment))
                .method("POST")
                .willRespondWith()
                .status(201)
                .body(CommentUtil.buildBody(createdComment))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact postDoesNotExist(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is not a post with id %s", postDoesNotExist.getPostId()))
                .uponReceiving(String.format("A request to create a comment for post %s", postDoesNotExist.getPostId()))
                .path(CommentsClient.getPostPath(postDoesNotExist.getPostId().toString()))
                .headers("Content-type", "application/json")
                .body(CommentUtil.buildBody(postDoesNotExist))
                .method("POST")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "validBody")
    public void validBody_CreatesComment() {
        Assert.assertEquals(createdComment, CreateComment.validBody_CreatesComment(commentsClient, comment));
    }

    @Test
    @PactVerification(fragment = "postDoesNotExist")
    public void postDoesNotExist_Returns404() {
        CreateComment.postDoesNotExist_Returns404(commentsClient, postDoesNotExist);
    }
}
