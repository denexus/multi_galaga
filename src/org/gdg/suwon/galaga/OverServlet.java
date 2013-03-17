package org.gdg.suwon.galaga;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdg.suwon.galaga.model.Player;
import org.gdg.suwon.galaga.model.PlayerState;
import org.gdg.suwon.galaga.model.Room;
import org.gdg.suwon.galaga.model.RoomMgr;
import org.gdg.suwon.galaga.model.RoomState;

public class OverServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String userId = req.getParameter("id");
		Room room = RoomMgr.getRoom("gdgSuwon");
		List<Player> players = room.getPlayers();
		Integer countEnd = 0;
		
		Long blueScore = (long)0;
		Long redScore = (long)0;
		
		for(Player player : players){
			if(player.getId().equals(userId)){
				//change status into END
				player.setState(PlayerState.END);
				resp.getWriter().write("{\"status\" : \"success\"}");
				
				if(player.getTeam().equals("BLUE")){
					blueScore += player.getScore();
				}else{
					redScore += player.getScore();
				}
				
				
				continue;
			}
		}
		
		if(isGameOver(players)){
			room.setState(RoomState.END);
			room.validate();
			
			for(Player player : players){
				if(player.getTeam().equals((blueScore>redScore?"BLUE":"RED"))){
					player.increaseWins();
				}else{
					player.increaseLoses();
				}
				
				player.addTotalScore(player.getScore());
				player.setState(PlayerState.IDLE);
				player.validate();
			}
			
		}
		
	}
	
	private boolean isGameOver(List<Player> players_){
		boolean isGameOver = true;
		
		for(Player player : players_){
			if(player.getState().equals(PlayerState.PLAYING)){
				return false;
			}
		}
		
		return isGameOver;
	}


}
