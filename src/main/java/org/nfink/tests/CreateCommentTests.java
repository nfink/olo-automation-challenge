package org.nfink.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Comment;
import org.nfink.model.Post;
import org.nfink.rule.CommentsClientRule;
import org.nfink.tests.shared.CreateComment;

public class CreateCommentTests {
    @ClassRule
    public static CommentsClientRule commentsClientRule = new CommentsClientRule();

    @Test
    public void validBody_CreatesComment() {
        // If post actually created the resource we would use below instead of hardcoded existing item.
        // Post post = new Post(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1);
        // Post createdPost = postsClientRule.getPostsClient().createPost(post);

        Post post = new Post(1, "test title", "test body", 1);
        Comment comment = new Comment(post.getId(), null, "test name", "test@example.com", "test body");

        CreateComment.validBody_CreatesComment(commentsClientRule.getCommentsClient(), comment);
    }
}
