package be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import be.insaneprogramming.cleanarch.entity.Building;

public interface BuildingDao extends AutoCloseable {
	@SqlUpdate("INSERT INTO building(id, name) VALUES (:b.id, :b.name)")
	void insert(@BindBean("b") Building building);

	@SqlQuery("SELECT id, name FROM building")
	@Mapper(BuildingMapper.class)
	List<Building> findAll();

	@SqlQuery("SELECT id, name FROM building WHERE id = :id")
	@Mapper(BuildingMapper.class)
	Building findById(@Bind("id") String id);

	@SqlQuery("SELECT id, name FROM building WHERE name LIKE concat(:name,'%')")
	@Mapper(BuildingMapper.class)
	List<Building> findByNameStartingWith(@Bind("name") String name);

	@SqlUpdate("UPDATE building SET name = :b.name")
	void update(@BindBean("b") Building building);

	@Override
	void close();
}
