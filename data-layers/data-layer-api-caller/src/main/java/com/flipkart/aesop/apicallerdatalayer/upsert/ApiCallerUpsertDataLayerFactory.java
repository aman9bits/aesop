package com.flipkart.aesop.apicallerdatalayer.upsert;

import org.springframework.beans.factory.FactoryBean;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Generates objects of {@link ApiCallerUpsertDataLayer } and ensures that it is singleton.
 * @author Jagadeesh Huliyar
 */
public class ApiCallerUpsertDataLayerFactory implements FactoryBean<ApiCallerUpsertDataLayer>
{
    private URL url;

    public ApiCallerUpsertDataLayer getObject() throws Exception
    {
	    return new ApiCallerUpsertDataLayer(url);
    }

	public Class<?> getObjectType()
    {
	    return ApiCallerUpsertDataLayer.class;
    }

	public boolean isSingleton()
    {
	    return true;
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
