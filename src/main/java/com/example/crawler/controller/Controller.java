package com.example.crawler.controller;

import com.example.crawler.service.crawler.MyCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping( value = "/startcrawler")
    public void startCrawler() throws Exception {
        String crawlStorageFolder = "I:/MyCrawler";// 定义爬虫数据存储位置
        int numberOfCrawlers = 1;// 定义了7个爬虫，也就是7个线程

        CrawlConfig config = new CrawlConfig();// 定义爬虫配置
        config.setCrawlStorageFolder(crawlStorageFolder);// 设置爬虫文件存储位置

		/*
		 * 实例化爬虫控制器。
		 */
        PageFetcher pageFetcher = new PageFetcher(config);// 实例化页面获取器
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();// 实例化爬虫机器人配置
        // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件
        // 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // 实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * 对于每次抓取，您需要添加一些种子网址。 这些是抓取的第一个URL，然后抓取工具开始跟随这些页面中的链接
		 */
        //controller.addSeed("http://www.ics.uci.edu/~lopes/");
        //controller.addSeed("http://www.ics.uci.edu/~welling/");
        //controller.addSeed("http://www.ics.uci.edu/");
        controller.addSeed("https://search.jd.com/Search?keyword=%E7%9F%AD%E8%A2%96&enc=utf-8&suggest=1.his.0.0&wq=&pvid=d21d45be00bb46a88beaa34192238365");
        controller.addSeed("https://search.jd.com/Search?keyword=%E7%AC%94%E8%AE%B0%E6%9C%AC%E7%94%B5%E8%84%91&enc=utf-8&suggest=1.def.0.V07--12s0,20s0,38s0&wq=bijiben&pvid=1b855a3e0b0d49a189ea9205328a2474");
        /**
         * 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
    }

}
