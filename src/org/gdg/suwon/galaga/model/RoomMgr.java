package org.gdg.suwon.galaga.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;


public class RoomMgr {
	public static Room createNewRoom(String room_){
		Room retRoom = getRoom(room_);
		
		if(retRoom == null){
			retRoom = new Room(room_);
			retRoom.validate();
		}
		
		return retRoom;
	}
	
	public static Room getRoom(String room_){
		Room retData = null;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		try {
			Entity roomEntity = dataStore.get(KeyFactory.createKey(Room.class.getSimpleName(), room_));
			retData = new Room(roomEntity);
		} catch (EntityNotFoundException e) {
//			e.printStackTrace();
		}
		
		return retData;
	}
}
