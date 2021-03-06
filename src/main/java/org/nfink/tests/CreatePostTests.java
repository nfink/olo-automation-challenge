package org.nfink.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.rule.PostsClientRule;
import org.nfink.tests.shared.CreatePost;

public class CreatePostTests {
    @ClassRule
    public static PostsClientRule postsClientRule = new PostsClientRule();

    @Test
    public void validBody_CreatesPost() {
        Post body = new Post(null, "test title", "test body", 1);
        CreatePost.validBody_CreatesPost(postsClientRule.getPostsClient(), body);
    }
}
