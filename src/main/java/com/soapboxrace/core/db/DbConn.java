package com.soapboxrace.core.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.soapboxrace.core.db.interfaces.IDbConn;

public class DbConn implements IDbConn {

	@Resource(lookup = "java:jboss/datasources/ExampleDS")
	private DataSource dataSource;

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

}
