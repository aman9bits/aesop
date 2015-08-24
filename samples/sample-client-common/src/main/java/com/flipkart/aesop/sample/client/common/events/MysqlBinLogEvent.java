package com.flipkart.aesop.sample.client.common.events;

import com.linkedin.databus.core.DbusOpcode;

import java.util.List;
import java.util.Map;

/**
 * @author yogesh.dahiya
 */

public interface MysqlBinLogEvent
{
	public Map<String, Object> getKeyValuePair();

	public Object get(String key);

	public boolean isCompositeKey();

	public List<String> getPrimaryKeyList();

	public String getEntityName();

	public DbusOpcode getEventType();

	public List<Object> getPrimaryKeyValues();

}
