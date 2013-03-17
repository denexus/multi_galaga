package org.gdg.suwon.galaga;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdg.suwon.galaga.model.Player;
import org.gdg.suwon.galaga.model.PlayerMgr;
import org.gdg.suwon.galaga.model.PlayerState;
import org.gdg.suwon.galaga.model.Room;
import org.gdg.suwon.galaga.model.RoomMgr;

public class EnterServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String userId = req.getParameter("id");
		Room room = RoomMgr.getRoom("gdgSuwon");
		if(room == null){
			room = RoomMgr.createNewRoom("gdgSuwon");
		}
		
		List<Player> players = room.getPlayers();
		String roomToken = room.getToken();
		Boolean samePlayer = false;
		for(Player player : players){
			if(player.getId().equals(userId) && !player.getState().equals(PlayerState.IDLE)){
				//send error.
				resp.getWriter().write("{status : \"fail\", token:\""+roomToken+"\"}");
				samePlayer = true;
				break;
			}else{
				samePlayer = false;
			}
		}
		if(!samePlayer){
			//send success
			String team="BLUE";
			if(players.size()%2 == 1){
				team = "RED";
			}
			Player newPlayer = PlayerMgr.createNewPlayer(userId, team);
			room.addPlayer(newPlayer);
			resp.getWriter().write("{\"status\" : \"success\", \"token\":\""+roomToken+"\"}");
		}
	}
}
