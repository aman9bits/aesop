package com.flipkart.aesop.apicallerdatalayer.upsert;

import com.flipkart.aesop.apicallerdatalayer.delete.ApiCallerDeleteDataLayer;
import com.flipkart.aesop.destinationoperation.UpsertDestinationStoreProcessor;
import com.flipkart.aesop.event.AbstractEvent;
import com.linkedin.databus.client.pub.ConsumerCallbackResult;
import com.linkedin.databus.core.DbusOpcode;
import org.trpr.platform.core.impl.logging.LogFactory;
import org.trpr.platform.core.spi.logging.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Sample Upsert Data Layer. Persists {@link DbusOpcode#UPSERT} events to Logs.
 * @author Jagadeesh Huliyar
 * @see ApiCallerDeleteDataLayer
 */
public class ApiCallerUpsertDataLayer extends UpsertDestinationStoreProcessor
{
	/** Logger for this class*/
    private static final Logger LOGGER = LogFactory.getLogger(ApiCallerUpsertDataLayer.class);
    URL url;

    public ApiCallerUpsertDataLayer(URL url_path) {
        this.url=url_path;
    }

    @Override
	protected ConsumerCallbackResult upsert(AbstractEvent event)
	{
        try {
            final String USER_AGENT = "Mozilla/5.0";
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String urlParameters = "";
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
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
        }
        LOGGER.info("API COULD NOT BE CALLED!! Some shit happened");
        return ConsumerCallbackResult.ERROR;
	}
}
