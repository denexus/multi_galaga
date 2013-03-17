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

public class ReadyServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String userId = req.getParameter("id");
		Room room = RoomMgr.getRoom("gdgSuwon");
		List<Player> players = room.getPlayers();
		for(Player player : players){
			if(player.getId().equals(userId)){
				//change status into Ready
				player.setState(PlayerState.READY);
				resp.getWriter().write("{\"status\" : \"success\"}");
				break;
			}else{
				resp.getWriter().write("{\"status\" : \"fail\"}");
			}
		}
	}
}
