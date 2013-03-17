package org.gdg.suwon.galaga.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class PlayerMgr {
	public static Player createNewPlayer(String id_, String team_){
		Player retPlayer = getPlayer(id_);
		if(retPlayer == null){
			retPlayer = new Player(id_, team_);
			retPlayer.validate();
		}
		
		return retPlayer;
	}
	
	public static Player getPlayer(String id_){
		return getPlayer(KeyFactory.createKey(Player.class.getSimpleName(), id_));
	}
	
	public static Player getPlayer(Key key_){
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		
		Player retPlayer = null;
		try {
			Entity retEntity = dataStoreService.get(key_);
			retPlayer = new Player(retEntity);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return retPlayer;
	}
}
