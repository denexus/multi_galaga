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

public class RoomInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		Room room = RoomMgr.getRoom("gdgSuwon");
		List<Player> players = room.getPlayers();
		StringBuffer strBuf = new StringBuffer();
		for(Player player : players){
			if(strBuf.length() > 0){
				strBuf.append(",");
			}
			strBuf.append("\""+player.getId()+"\"");
		}
		String resultStr = "{\"players\": ["+strBuf.toString()+"]}";
		resp.getWriter().write(resultStr);
	}
}
