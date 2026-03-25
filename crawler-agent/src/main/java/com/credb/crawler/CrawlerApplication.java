package com.credb.crawler;

import com.credb.crawler.config.CrawlerConfig;
import com.credb.crawler.service.CrawlerAgentService;

public final class CrawlerApplication {
    private CrawlerApplication() {
    }

    public static void main(String[] args) {
        CrawlerConfig config = CrawlerConfig.fromEnvironment(args);
        CrawlerAgentService service = new CrawlerAgentService(config);
        service.start();
    }
}
