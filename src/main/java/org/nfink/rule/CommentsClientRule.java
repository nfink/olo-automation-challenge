package org.nfink.rule;

import org.junit.rules.ExternalResource;
import org.nfink.Config;
import org.nfink.service.CommentsClient;

public class CommentsClientRule extends ExternalResource {
    private final CommentsClient commentsClient;

    public CommentsClientRule() {
        commentsClient = new CommentsClient(Config.getProperty("jsonplaceholder.url"));
    }

    public CommentsClient getCommentsClient() {
        return commentsClient;
    }
}
