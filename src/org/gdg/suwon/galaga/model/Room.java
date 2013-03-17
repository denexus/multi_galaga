package org.gdg.suwon.galaga.model;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class Room extends EntityData{
	private static final String KEY_PLAYERS = "PLAYERS";
	private static final String KEY_STATE = "STATE";
	private static final String KEY_TITLE = "TITLE";
	private static final String KEY_TOKEN = "TOKEN";
	
	private List<Key> players = new ArrayList<Key>();
	private RoomState state = RoomState.READY;
	private String title = "GDG_SUWON";	//FIXME : Temporary room's name until implementing list.
	private String token = null;
	
	public Room(String title_){
		super(Room.class.getSimpleName(), title_);
		
		entity.setProperty(KEY_PLAYERS, players);
		entity.setProperty(KEY_STATE, state);
		entity.setProperty(KEY_TITLE, title);
		
		ChannelService channel = ChannelServiceFactory.getChannelService();
		token = channel.createChannel(title_);
		entity.setProperty(KEY_TOKEN, token);
	}
	
	public Room(Entity entity_){
		super(entity_);
		
		players = (List<Key>)entity.getProperty(KEY_PLAYERS);
		state = (RoomState)entity.getProperty(KEY_STATE);
		title = (String)entity.getProperty(KEY_STATE);
		token = (String)entity.getProperty(KEY_TOKEN);
	}
	
	public List<Player> getPlayers(){
		List<Player> retList = new ArrayList<Player>();
		
		Player playerObj = null;
		for(Key player : players){
			playerObj = PlayerMgr.getPlayer(player);
			if(playerObj != null){
				retList.add(playerObj);
			}
		}
		
		return retList;
	}
	
	public void addPlayer(Player newPlayer_){
		players.add(newPlayer_.toEntity().getKey());
	}
	
	public void setState(RoomState newState_){
		state = newState_;
	}
	
	public RoomState getState(){
		return state;
	}
	
	public String getToken(){
		return token;
	}
}
