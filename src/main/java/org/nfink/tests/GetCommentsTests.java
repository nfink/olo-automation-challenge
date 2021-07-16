package org.nfink.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Comment;
import org.nfink.model.Post;
import org.nfink.rule.CommentsClientRule;
import org.nfink.rule.PostsClientRule;

import java.io.IOException;
import java.util.UUID;

public class GetCommentsTests {
    @ClassRule
    public static CommentsClientRule commentsClientRule = new CommentsClientRule();

    @ClassRule
    public static PostsClientRule postsClientRule = new PostsClientRule();

    @Test
    public void returnsListOfPosts() {
        GetComments.returnsListOfComments(commentsClientRule.getCommentsClient());
    }

    @Test
    public void validPostId_ReturnsExistingComment() throws IOException {
        // If post actually created the resource we would use below instead of hardcoded existing items.
        // Post post = new Post(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1);
        // Post createdPost = postsClientRule.getPostsClient().createPost(post);
        // Comment comment = new Comment(createdPost.getId(), null, UUID.randomUUID().toString(), "test@example.com", UUID.randomUUID().toString());
        // Comment createdComment = commentsClientRule.getCommentsClient().createComment(comment);

        Comment comment = new Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");

        GetComments.validPostId_ReturnsExistingComment(commentsClientRule.getCommentsClient(), comment);
    }
}
