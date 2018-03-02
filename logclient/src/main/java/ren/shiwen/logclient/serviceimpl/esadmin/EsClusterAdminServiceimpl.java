package ren.shiwen.logclient.serviceimpl.esadmin;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ren.shiwen.logclient.configuration.ElasticSearchConfiguration;
import ren.shiwen.logclient.service.esadmin.EsClusterAdminService;

@Service
public class EsClusterAdminServiceimpl implements EsClusterAdminService {

	@Resource
	private ElasticSearchConfiguration es;

	@Override
	public void RefreshAll() throws UnknownHostException {
		es.esclient().admin().indices().prepareRefresh().get();

	}

	@Override
	public void RefreshIndecesByNames(String[] names) throws UnknownHostException {
		es.esclient().admin().indices().prepareRefresh(names).get();

	}

}
