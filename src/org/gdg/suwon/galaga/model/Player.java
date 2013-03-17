package org.gdg.suwon.galaga.model;

import com.google.appengine.api.datastore.Entity;


public class Player extends EntityData {
	
	public static final String KEY_SCORE = "SCORE";
	public static final String KEY_TOTAL_SCORE = "TOTALSCORE";
	public static final String KEY_WINS = "WINS";
	public static final String KEY_LOSES = "LOSES";
	
	private String id;
	
	private String state = PlayerState.IDLE;
	
	private Long score = (long)0;
	
	private Long wins = (long)0;
	private Long loses = (long)0;
	
	private Long totalScore = (long)0;
	
	private String team;
	
	public Player(String id_, String team_){
		super(Player.class.getSimpleName(), id_);
		id = id_;
		team = team_;
		entity.setProperty("ID", id);
		entity.setProperty("STATE", state);
		entity.setProperty(KEY_SCORE,score);
		entity.setProperty(KEY_WINS, wins);
		entity.setProperty(KEY_LOSES, loses);
		entity.setProperty("TEAM", team);
		entity.setProperty(KEY_TOTAL_SCORE, totalScore);
	}
	
	public Player(Entity entity_){
		super(entity_);
		id = (String)entity.getProperty("ID");
		state = (String)entity.getProperty("STATE");
		score = (Long)entity.getProperty(KEY_SCORE);
		loses = (Long)entity.getProperty(KEY_LOSES);
		wins = (Long)entity.getProperty(KEY_WINS);
		team = (String)entity.getProperty("TEAM");
		totalScore = (Long)entity.getProperty(KEY_TOTAL_SCORE);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	
	public void increaseWins(){
		++wins;
	}
	
	public void increaseLoses(){
		++loses;
	}
	
	public String getTeam(){
		return team;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	public void addTotalScore(Long totalScore) {
		this.totalScore += totalScore;
	}
}
