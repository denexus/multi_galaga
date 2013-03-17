package org.gdg.suwon.galaga;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdg.suwon.galaga.model.Player;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class RankServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Query query = new Query(Player.class.getSimpleName()).addSort(
				Player.KEY_WINS, SortDirection.DESCENDING).addSort(
				Player.KEY_SCORE, SortDirection.DESCENDING);

		List<Entity> entitys = DatastoreServiceFactory.getDatastoreService()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());

		StringBuffer strBuf = new StringBuffer();
		Entity entity = null;
		for (int i = 1; i <= entitys.size(); i++) {
			if (i != 1) {
				strBuf.append(",");
			}
			strBuf.append("{\"rank\":" + i + ",\"score\":"
					+ entity.getProperty(Player.KEY_SCORE) + ",\"wins\":"
					+ entity.getProperty(Player.KEY_WINS) + ",\"loses\":"
					+ entity.getProperty(Player.KEY_LOSES) + "}");
		}
		
		resp.getWriter().write("["+strBuf.toString()+"]");
	}

}
