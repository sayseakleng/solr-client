package com.kdemo.solr.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Ignore;
import org.junit.Test;

public class TestSolr {

	@Test
	public void testQuery() throws SolrServerException, IOException {
		
		String url = "http://localhost:8983/solr/solr_sample";
		
		SolrClient solr = new HttpSolrClient.Builder(url)
			.build();
		
		SolrQuery query = new SolrQuery();
		
		query.setFields("title", "price", "id");  // fl
		query.setQuery("tags:Hello1");      // q
		query.setFilterQueries("price:[10 TO *]");  //fq
		
		query.setStart(0);
		query.setRows(10);
		query.set("wt", "csv");
		query.setSort("_version_", ORDER.desc);  // last update first
		
		query.addOrUpdateSort("score", ORDER.desc);  // last update first
		query.setIncludeScore(true);
		
		// as document
		QueryResponse response = solr.query(query);
		SolrDocumentList results = response.getResults();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument);
		}
		
		// as beans
		List<Product> beans = response.getBeans(Product.class);
		
		for (Product product : beans) {
			System.out.println(product);
		}
		
	}
	
	
	@Ignore
	@Test
	public void testCreate() throws SolrServerException, IOException {
		
		String url = "http://localhost:8983/solr/solr_sample";
		
		SolrClient solr = new HttpSolrClient.Builder(url)
			.build();
		
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", "552192");
		document.setField("title", "Gouda cheese wheel book");
		document.setField("price", 49.99);
		
		document.setField("tags", "Hello1");
		document.addField("tags", "Hello2");
		
		UpdateResponse response = solr.add(document);
		
		int status = response.getStatus();
		if(status == 0) {
			solr.commit();
		}
		else {
			solr.rollback();
		}
		
	}
	
	@Test
	public void testCreateWithPojo() throws SolrServerException, IOException {
		
		String url = "http://localhost:8983/solr/solr_sample";
		
		SolrClient solr = new HttpSolrClient.Builder(url)
			.build();
		
		
		Product product = new Product();
		product.setId("552191");
		product.setTitle("Gouda cheese wheel book");
		product.setPrice(49.99);
		List<String> tags = Arrays.asList("Hello1", "Hello2");
		product.setTags(tags);
		
		 
		
		UpdateResponse response = solr.addBean(product);
		
		int status = response.getStatus();
		if(status == 0) {
			solr.commit();
		}
		else {
			solr.rollback();
		}
		
	}

}