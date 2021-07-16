package org.nfink.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.rule.PostsClientRule;

import java.io.IOException;

public class UpdatePostTests {
    @ClassRule
    public static PostsClientRule postsClientRule = new PostsClientRule();

    @Test
    public void validBody_UpdatesPost() throws IOException {
        // If post actually created the resource we would use below instead of hardcoded existing item.
        // Post post = new Post(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1);
        // Post createdPost = postsClientRule.getPostsClient().createPost(post);

        Post updatedPost = new Post(1, "new title", "new body", 1);
        UpdatePost.validBody_UpdatesPost(postsClientRule.getPostsClient(), updatedPost);
    }

    @Test
    public void postDoesNotExist_Returns409() {
        Post post = new Post(99999, "title", "body", 1);
        postsClientRule.getPostsClient().deletePost(post);
        UpdatePost.postDoesNotExist_Returns409(postsClientRule.getPostsClient(), post);
    }
}
