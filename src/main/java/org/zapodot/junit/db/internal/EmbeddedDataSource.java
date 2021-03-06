package org.zapodot.junit.db.internal;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * A simple DataSource implementation that simply wraps a single Connection
 * Needs to be public to be used by ByteBuddy. Part of internal api, so it may be changed or removed without prior warning
 */
public class EmbeddedDataSource implements DataSource {

    private final Connection connection;

    private EmbeddedDataSource(final Connection connection) {
        this.connection = connection;
    }

    public static EmbeddedDataSource create(final Connection connection) {
        return new EmbeddedDataSource(connection);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return CloseSuppressedConnectionFactory.createProxy(connection);
    }

    @Override
    public Connection getConnection(final String username, final String password) throws SQLException {
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return new PrintWriter(System.err);
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getGlobal();
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("unWrap(Class) is not supported for embedded datasource");
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return false;
    }
}
