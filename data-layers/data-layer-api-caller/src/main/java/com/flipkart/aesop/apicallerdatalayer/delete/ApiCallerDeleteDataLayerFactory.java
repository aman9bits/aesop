package com.flipkart.aesop.apicallerdatalayer.delete;

import org.springframework.beans.factory.FactoryBean;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aman.gupta on 12/08/15.
 */
public class ApiCallerDeleteDataLayerFactory implements FactoryBean<ApiCallerDeleteDataLayer> {
    private URL url;
    public ApiCallerDeleteDataLayer getObject() throws Exception
    {
        return new ApiCallerDeleteDataLayer(url);
    }

    public Class<?> getObjectType()
    {
        return ApiCallerDeleteDataLayer.class;
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
