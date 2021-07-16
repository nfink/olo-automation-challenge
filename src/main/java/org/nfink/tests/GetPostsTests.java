package org.nfink.tests;

import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.rule.PostsClientRule;
import org.nfink.tests.shared.GetPosts;

import java.io.IOException;

public class GetPostsTests {
    @ClassRule
    public static PostsClientRule postsClientRule = new PostsClientRule();

    @Test
    public void returnsListOfPosts() {
        GetPosts.returnsListOfPosts(postsClientRule.getPostsClient());
    }

    @Test
    public void returnsExistingPost() throws IOException {
        // If post actually created the resource we would use below instead of hardcoded existing item.
        // Post post = new Post(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1);
        // Post createdPost = postsClientRule.getPostsClient().createPost(post);

        Post post = new Post(100, "at nam consequatur ea labore ea harum", "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut", 10);

        GetPosts.returnsExistingPost(postsClientRule.getPostsClient(), post);
    }

}
