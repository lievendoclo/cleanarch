package be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import be.insaneprogramming.cleanarch.entity.Tenant;

public class TenantMapper implements ResultSetMapper<Tenant> {
	@Override
	public Tenant map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		String id = rs.getString("id");
		return new Tenant(id, rs.getString("name"));
	}
}
