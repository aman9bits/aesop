package com.flipkart.aesop.apicallerdatalayer.upsert;

import com.flipkart.aesop.apicallerdatalayer.delete.ApiCallerDeleteDataLayer;
import com.flipkart.aesop.destinationoperation.UpsertDestinationStoreProcessor;
import com.flipkart.aesop.event.AbstractEvent;
import com.linkedin.databus.client.pub.ConsumerCallbackResult;
import com.linkedin.databus.core.DbusOpcode;
import org.json.JSONException;
import org.json.JSONObject;
import org.trpr.platform.core.impl.logging.LogFactory;
import org.trpr.platform.core.spi.logging.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Sample Upsert Data Layer. Persists {@link DbusOpcode#UPSERT} events to Logs.
 * @author Jagadeesh Huliyar
 * @see ApiCallerDeleteDataLayer
 */
public class ApiCallerUpsertDataLayer extends UpsertDestinationStoreProcessor
{
	/** Logger for this class*/
    private static final Logger LOGGER = LogFactory.getLogger(ApiCallerUpsertDataLayer.class);
    private URL url;
    private JSONObject headers;
    public ApiCallerUpsertDataLayer(URL url_path, JSONObject headers) {
        this.url=url_path;
        this.headers=headers;
    }

    @Override
	protected ConsumerCallbackResult upsert(AbstractEvent event) {
        try {
            final String USER_AGENT = "Mozilla/5.0";
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            Iterator<String> it = headers.keys();
            while(it.hasNext()){
                String header = it.next();
                con.setRequestProperty(header,headers.get(header).toString());
            }
            JSONObject param =new JSONObject();
            Object[] keyset = event.getFieldMapPair().keySet().toArray();
            Object[] values = event.getFieldMapPair().values().toArray();
            for(int i=0;i<event.getFieldMapPair().size();i++)
            {
                param.put(String.valueOf(keyset[i]),String.valueOf(values[i]));
            }
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(param.toString());
            LOGGER.info(param.toString());
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            LOGGER.info("Received response code from service:" + responseCode);
            switch (responseCode) {
                case 200:
                    LOGGER.info("API called successfully");
                    return ConsumerCallbackResult.SUCCESS;
                case 201:
                    LOGGER.info("API called successfully");
                    return ConsumerCallbackResult.SUCCESS;
                case 202:
                    LOGGER.info("API called successfully");
                    return ConsumerCallbackResult.SUCCESS;
                case 204:
                    LOGGER.info("API called successfully");
                    return ConsumerCallbackResult.SUCCESS;
                default:
                    LOGGER.info("API COULD NOT BE CALLED!! Response Code:"+responseCode);
                    return ConsumerCallbackResult.ERROR;
            }
        }
        catch (ProtocolException e) {
            e.printStackTrace();
            LOGGER.info("API COULD NOT BE CALLED!! ProtocolException Occurred");
        }
        catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("API COULD NOT BE CALLED!! IOException Occurred");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LOGGER.info("API COULD NOT BE CALLED!! Some shit happened");
        return ConsumerCallbackResult.ERROR;
	}
}
