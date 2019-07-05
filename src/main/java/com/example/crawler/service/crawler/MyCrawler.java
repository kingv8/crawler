package com.example.crawler.service.crawler;

import com.example.crawler.repository.CrawlerRepository;
import com.example.crawler.entity.CrawlerGoods;
import com.example.crawler.utils.SpringUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.regex.Pattern;

public class MyCrawler extends WebCrawler {

    CrawlerRepository crawlerRepository = SpringUtil.getBean(CrawlerRepository.class);

    //指示抓取工具忽略具有css，js，git，...扩展名的网址
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * 这个方法主要是决定哪些url我们需要抓取，返回true表示是我们需要的，返回false表示不是我们需要的Url
     * 第一个参数referringPage封装了当前爬取的页面信息 第二个参数url封装了当前爬取的页面url信息
     * 在这个例子中，我们指定爬虫忽略具有css，js，git，...扩展名的url，只接受以“http://www.ics.uci.edu/”开头的url。
     * 在这种情况下，我们不需要referringPage参数来做出决定。
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();// 得到小写的url
        return !FILTERS.matcher(href).matches()// 正则匹配，过滤掉我们不需要的后缀文件
                && href.startsWith("https://search.jd.com/Search");// 只接受以“http://www.ics.uci.edu/”开头的url
    }

    /**
     * 当一个页面被提取并准备好被你的程序处理时，这个函数被调用。
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();// 获取url
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {// 判断是否是html数据
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();//// 强制类型转换，获取html数据对象
            String text = htmlParseData.getText();//获取页面纯文本（无html标签）
            String html = htmlParseData.getHtml();//获取页面Html
            Set<WebURL> links = htmlParseData.getOutgoingUrls();// 获取页面输出链接

            //System.out.println("纯文本长度: " + text.length());
            // System.out.println("html长度: " + html.length());
            //System.out.println("链接个数 " + links.size());

            Document doc = Jsoup.parse(html);//采用jsoup解析html，这个大家不会可以简单搜一下

            //使用选择器的时候需要了解网页中的html规则，自己去网页中F12一下，
            Elements elements = doc.select(".gl-item");
            if(elements.size()==0){
                return;
            }
            for (Element element : elements) {
                //Elements img = element.select(".err-product");
                Elements img = element.select("img[source-data-lazy-img]");
                if(img!=null){//输出图片链接        
                   //System.out.println(img.attr("src"));
                   // System.out.println(img.attr("source-data-lazy-img"));
                    CrawlerGoods crawlerGoods = new CrawlerGoods();
                    crawlerGoods.setSrc(img.attr("source-data-lazy-img"));
                    crawlerRepository.save(crawlerGoods);
                }
            }
        }
    }
}
