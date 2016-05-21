package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOTablaUsuarios extends DAOTablaGenerica{
	public boolean esRol(int id, String rol) throws Exception {
		boolean resp = false;

		String sql = "SELECT NOMBRE "
		           + "FROM ROLUSUARIO R, USUARIOS U "
		           + "WHERE U.ID=" + id + " AND U.ROL=R.ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			resp = rs.getString("NOMBRE").equals(rol);
		}
		return resp;
	}
}
