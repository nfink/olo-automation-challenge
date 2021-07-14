package org.nfink.pact.util;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import org.nfink.model.Post;

public class PostUtil {
    public static DslPart buildBody(Post post) {
        return new PactDslJsonBody()
                .integerType("id", post.getId())
                .stringType("title", post.getTitle())
                .stringType("body", post.getBody())
                .integerType("userId", post.getUserId())
                .asBody();
    }

    public static DslPart buildArrayBody(Post post) {
        return PactDslJsonArray.arrayMinLike(1)
                .integerType("id", post.getId())
                .stringType("title", post.getTitle())
                .stringType("body", post.getBody())
                .integerType("userId", post.getUserId())
                .closeObject();
    }
}
