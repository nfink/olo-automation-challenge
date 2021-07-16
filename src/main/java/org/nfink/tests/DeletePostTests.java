package org.nfink.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.rule.PostsClientRule;
import org.nfink.tests.shared.DeletePost;

public class DeletePostTests {
    @ClassRule
    public static PostsClientRule postsClientRule = new PostsClientRule();

    @Test
    public void validId_returns200() {
        // If post actually created the resource we would use below instead of hardcoded existing item.
        // Post post = new Post(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1);
        // Post createdPost = postsClientRule.getPostsClient().createPost(post);

        Post post = new Post(1, "title", "body", 1);

        DeletePost.validId_returns200(postsClientRule.getPostsClient(), post.getId().toString());
    }

    @Test
    public void postDoesNotExist_Return404() {
        // Would prefer to ensure that post does not exist by deleting the data via other means than the same API we're testing.
        // Another possibility might be to use a UUID as the post id, if those could be valid ids.
        Post post = new Post(999, "title", "body", 1);
        postsClientRule.getPostsClient().deletePost(post);

        DeletePost.postDoesNotExist_Returns404(postsClientRule.getPostsClient(), post.getId().toString());
    }
}
