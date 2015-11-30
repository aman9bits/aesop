package com.flipkart.aesop.apicallerdatalayer.delete;

import com.flipkart.aesop.destinationoperation.DeleteDestinationStoreProcessor;
import com.flipkart.aesop.event.AbstractEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linkedin.databus.client.pub.ConsumerCallbackResult;
import org.json.JSONObject;
import org.trpr.platform.core.impl.logging.LogFactory;
import org.trpr.platform.core.spi.logging.Logger;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by aman.gupta on 12/08/15.
 */
public class ApiCallerDeleteDataLayer extends DeleteDestinationStoreProcessor {

    private static final Logger LOGGER = LogFactory.getLogger(ApiCallerDeleteDataLayer.class);
    private URL url;
    private JSONObject headers;
    public ApiCallerDeleteDataLayer(URL url, JSONObject headers) {
        this.url = url;
        this.headers = headers;
    }

    @Override
    protected ConsumerCallbackResult delete(AbstractEvent event) {
        try {
            Map<String,Object> eventMap = event.getFieldMapPair();
            Gson gson = new GsonBuilder().serializeNulls().create();
            String param = gson.toJson(eventMap);
            LOGGER.info("Making a post call to url: "+url+" with payload as: "+param);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            Iterator<String> it = headers.keys();
            //If a header value starts with $, the corresponding value from the payload is used. See documentation for details.
            while(it.hasNext()){
                String header = it.next();
                String value = headers.get(header).toString();
                if(value.charAt(0)=='$'){
                    value = eventMap.get(value.substring(1)).toString();
                }
                con.setRequestProperty(header,value);
            }
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(param);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            LOGGER.info("Call successful with response code as "+responseCode+ "for payload: "+param);
            return ConsumerCallbackResult.SUCCESS;
        } catch(Exception e){
            LOGGER.error("Call unsuccessful with error:",e);
            return ConsumerCallbackResult.ERROR;
        }

    }
}