package org.gdg.suwon.galaga.model;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class Room extends EntityData{
	private static final String KEY_PLAYERS = "PLAYERS";
	private static final String KEY_STATE = "STATE";
	private static final String KEY_TITLE = "TITLE";
	private static final String KEY_TOKEN = "TOKEN";
	
	public static final String PLAYER_CHANGE = "player";
	public static final String ROOM_CHANGE = "room";
	
	private List<Key> players = new ArrayList<Key>();
	private String state = RoomState.READY;
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
		if(players == null){
			players = new ArrayList<Key>();
		}
		state = (String)entity.getProperty(KEY_STATE);
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
		validate();
	}
	
	public void setState(String newState_){
		state = newState_;
		validate();
	}
	
	public String getState(){
		return state;
	}
	
	public String getToken(){
		return token;
	}

	public void sync(){
		sync(ROOM_CHANGE);
	}
	
	public void sync(String msg_){
		if(token != null){
			ChannelServiceFactory.getChannelService().sendMessage(new ChannelMessage(token, "{\"type\":\"room_state_change\",\"kind\":\"room\"}"));
		}
	}
}
