package com.example.crawler.repository;

import com.example.crawler.entity.CrawlerGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerRepository extends JpaRepository<CrawlerGoods, Integer> {

}
