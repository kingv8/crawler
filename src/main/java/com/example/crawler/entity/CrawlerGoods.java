package com.example.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@javax.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CrawlerGoods")
public class CrawlerGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_gen")
    private Integer id;
    @Column(length = 128)
    private String src;
}
