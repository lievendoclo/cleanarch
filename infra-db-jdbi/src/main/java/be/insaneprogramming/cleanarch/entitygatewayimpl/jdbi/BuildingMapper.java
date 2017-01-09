package be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;

public class BuildingMapper implements ResultSetMapper<Building> {
	@Override
	public Building map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		final String buildingId = rs.getString("id");
		final String buildingName = rs.getString("name");
		return BuildingFactory.create().createBuilding(buildingId, buildingName);
	}
}
