package org.nfink.pact;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.nfink.model.Comment;
import org.nfink.pact.util.CommentUtil;
import org.nfink.service.CommentsClient;
import org.nfink.tests.GetComments;

import java.io.IOException;

public class GetCommentsPact {
    @Rule
    public PactProviderRule provider = new PactProviderRule("jsonplaceholder", "localhost", 0, this);

    private static final Comment comment = new Comment(2, 3, "test name", "test@example.com", "test body");
    private static final Integer postDoesNotExist = 999;

    private CommentsClient commentsClient;

    @Before
    public void setup() {
        commentsClient = new CommentsClient(provider.getUrl());
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact happyPath(PactDslWithProvider builder) {
        return builder
                .given("default")
                .uponReceiving("A request to get comments")
                .path(CommentsClient.getGetPath())
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(CommentUtil.buildArrayBody(comment))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact returnsExistingComment(PactDslWithProvider builder) {
        return builder
                .given(String.format("There a comment with id %s", comment.getId()))
                .uponReceiving("A request to get comments")
                .path(CommentsClient.getGetPath())
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(CommentUtil.buildArrayBody(comment))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact validPostId(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is a post with id %s", comment.getPostId()))
                .uponReceiving(String.format("A request to get comments for post %s", comment.getPostId()))
                .path(CommentsClient.getGetPath(comment.getPostId().toString()))
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(CommentUtil.buildArrayBody(comment))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact validPostIdReturnsExistingComment(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is a post with id %s that has a comment with id %s", comment.getPostId(), comment.getId()))
                .uponReceiving(String.format("A request to get comments for post %s", comment.getPostId()))
                .path(CommentsClient.getGetPath(comment.getPostId().toString()))
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(CommentUtil.buildArrayBody(comment))
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact postDoesNotExist(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is not a post with id %s", postDoesNotExist))
                .uponReceiving(String.format("A request to get comments for post %s", postDoesNotExist))
                .path(CommentsClient.getGetPath(postDoesNotExist.toString()))
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Pact(consumer = "Olo")
    public RequestResponsePact postDoesNotExistWithParameter(PactDslWithProvider builder) {
        return builder
                .given(String.format("There is not a post with id %s", postDoesNotExist))
                .uponReceiving(String.format("A request to get comments for post %s using a postId parameter", postDoesNotExist))
                .path(CommentsClient.getGetPath())
                .query("postId=" + postDoesNotExist)
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonArray())
                .toPact();
    }

    @Test
    @PactVerification(fragment = "happyPath")
    public void returnsListOfComments() {
        GetComments.returnsListOfComments(commentsClient);
    }

    @Test
    @PactVerification(fragment = "returnsExistingComment")
    public void returnsExistingComment() throws IOException {
        GetComments.returnsExistingComment(commentsClient, comment);
    }

    @Test
    @PactVerification(fragment = "validPostId")
    public void validPostId_ReturnsListOfComments() {
        GetComments.validPostId_ReturnsListOfComments(commentsClient, comment.getPostId());
    }

    @Test
    @PactVerification(fragment = "validPostIdReturnsExistingComment")
    public void validPostId_ReturnsExistingComment() throws IOException {
        GetComments.validPostId_ReturnsExistingComment(commentsClient, comment);
    }

    @Test
    @PactVerification(fragment = "postDoesNotExist")
    public void postDoesNotExist_Returns404() {
        GetComments.postDoesNotExist_Returns404(commentsClient, postDoesNotExist);
    }

    @Test
    @PactVerification(fragment = "postDoesNotExistWithParameter")
    public void postDoesNotExist_WithParameter_ReturnsEmptyList() {
        GetComments.postDoesNotExist_WithParameter_ReturnsEmptyList(commentsClient, postDoesNotExist);
    }
}
