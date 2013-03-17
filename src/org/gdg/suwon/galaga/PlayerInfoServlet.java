package org.gdg.suwon.galaga;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdg.suwon.galaga.model.Player;
import org.gdg.suwon.galaga.model.Room;
import org.gdg.suwon.galaga.model.RoomMgr;

public class PlayerInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String userId = req.getParameter("id");
		Room room = RoomMgr.getRoom("gdgSuwon");
		List<Player> players = room.getPlayers();
		for(Player player : players){
			if(player.getId().equals(userId)){
				String playerState = player.getState();
				Long playerScore = player.getScore();
				resp.getWriter().write("{\"state\" : \""+playerState+"\", \"score\" : \""+playerScore+"\"}");
				break;
			}
		}
	}
}
