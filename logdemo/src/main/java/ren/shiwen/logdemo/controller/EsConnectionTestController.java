package ren.shiwen.logdemo.controller;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ren.shiwen.logdemo.configuration.ElasticSearchConfiguration;

@Controller
@RestController
public class EsConnectionTestController {

	private Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	private ElasticSearchConfiguration es;

	@RequestMapping(value="/testescon")
	public String GetActiveShards() throws UnknownHostException {
		ClusterHealthResponse healths = es.esclient().admin().cluster().prepareHealth().get();
		logger.info(String.valueOf(healths.getActiveShards()));
		return String.valueOf(healths.getActiveShards());

	}

}
