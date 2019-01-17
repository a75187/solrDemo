package com.user.solrcenter.controller;

import com.user.solrcenter.bens.User;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class SolrController {
    @Autowired
    private SolrClient client;

    @RequestMapping("/")
    public String testSolr() throws IOException, SolrServerException {
        SolrDocument document = client.getById("userInfo", "8");
        System.out.println(document);
        return document.toString();
    }

    @RequestMapping("/add")
    public String add() throws IOException, SolrServerException {
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.addField("id","3");
        solrInputFields.addField("feedbackTime",new Date());
        solrInputFields.addField("userinfoId","9854654564");
        solrInputFields.addField("content","傻逼都哈诺");
        UpdateResponse add = client.add("userInfo",solrInputFields);
        client.commit();
        return add.getRequestUrl();
    }

    @RequestMapping("/del")
    public String del() throws IOException, SolrServerException {
        UpdateResponse updateResponse = client.deleteById("userInfo","8");
        return  updateResponse.getStatus()+"";
    }

    @RequestMapping("/query")
    public String query() throws IOException, SolrServerException {
        /*简单查询*/
        SolrQuery params = new SolrQuery("content:傻逼* AND userinfoId:*56*");
        QueryResponse userInfo = client.query("userInfo", params);
        List<User> beans = userInfo.getBeans(User.class);
        return userInfo.getRequestUrl();
    }

    @RequestMapping("/addBean")
    public String addBean() throws IOException, SolrServerException {
        User user = new User();
        user.setContent("你们这些傻逼");
        user.setFeedbackTime(new Date());
        user.setUserinfoId("450454056");
        user.setId("6");
        UpdateResponse updateResponse = client.addBean("userInfo",user);
        return updateResponse.getStatus()+"";
    }

    @RequestMapping("/addDoc")
    public String addDoc() throws IOException, SolrServerException {
        UpdateResponse userInfo = client.optimize("userInfo");
        return userInfo.getStatus()+"";
    }
}
