package org.gdg.suwon.galaga;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ImageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = req.getParameter("id");
		System.out.println("id->"+id);
		BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
		blobService.serve(blobService.createGsBlobKey("/img/player"+id+".png"), resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		System.out.println("id->"+id);
		
		BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
		
		blobService.createUploadUrl("/img/player/"+id+".png");
		Map<String,List<BlobKey>> keyMap = blobService.getUploads(req);
		List<BlobKey> keys = null;
		for(String mapKey : keyMap.keySet()){
			keys = keyMap.get(mapKey);
			for(BlobKey key:keys){
				System.out.println("["+mapKey+"]->["+key+"]");
			}
		}
	}

}
