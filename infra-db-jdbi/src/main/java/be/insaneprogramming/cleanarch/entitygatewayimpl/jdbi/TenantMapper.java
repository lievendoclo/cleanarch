package be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;

public class TenantMapper implements ResultSetMapper<Tenant> {
	@Override
	public Tenant map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		String buildingId = rs.getString("id");
		final String tenantName = rs.getString("name");
		return TenantFactory.create().createTenant(buildingId, tenantName);
	}
}
