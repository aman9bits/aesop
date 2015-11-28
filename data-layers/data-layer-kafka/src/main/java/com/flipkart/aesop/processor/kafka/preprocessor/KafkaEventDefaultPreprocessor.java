/*******************************************************************************
 * Copyright 2012-2015, the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obta a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.flipkart.aesop.processor.kafka.preprocessor;

import com.flipkart.aesop.event.AbstractEvent;
import com.flipkart.aesop.processor.kafka.client.KafkaClient;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by r.yadav on 10/04/15.
 * This class generated KafkaProducer payload to be pushed to Kafka. The payload is pushed as Map<String,Object>
 */
public  class KafkaEventDefaultPreprocessor  {

    /* Kafka Initializer Client. */
    private KafkaClient kafkaClient;
    private static final String KEY = "KEY";
    private static final String FIELD_MAP = "FIELD_MAP";
    private static final String ENTITY = "ENTITY";
    private static final String NAMESPACE = "NAMESPACE";
    private static final String OPCODE = "OPCODE";

    public ProducerRecord createProducerRecord(AbstractEvent event) {
        /*Kafka Record is created assuming default key based partitioning
         * String serialiation has been used for key and byte for value
         * Topic is configured via properties file
         */
        ProducerRecord record = new ProducerRecord(kafkaClient.getTopic(event.getNamespaceName()), getPrimaryKey(event), SerializationUtils.serialize(getPayload(event
        )));

        return record;
    }

    /*
     * Helper method to extract primary key from event
     */
    private Object getPrimaryKey(AbstractEvent event) {
        /*Picks the first element from the list
         * This could be modified further based on requirements
         */
        if (event.getPrimaryKeyValues() != null && event.getPrimaryKeyValues().size() > 0) {
            return event.getPrimaryKeyValues().get(0);
        } else {
            /*
            If key not found in event this method returns null , relies on the client to partition appropriately
             */
            return null;
        }
    }

    /*
     * Helper method to generate kafka Payload from event
     */
    private Map<String, Object> getPayload(AbstractEvent event) {
        Object key = getPrimaryKey(event);
        Map<String, Object> fieldMap = event.getFieldMapPair();
        String entityName = event.getEntityName();
        String nameSpaceName = event.getNamespaceName();
        String opCode = event.getEventType().name();

        Map<String, Object> payload = new HashMap<String, Object>();
        mapput(payload, KEY, key);
        mapput(payload, FIELD_MAP, fieldMap);
        mapput(payload, ENTITY, entityName);
        mapput(payload, NAMESPACE, nameSpaceName);
        mapput(payload, OPCODE, opCode);

        return payload;
    }

    private void mapput(Map<String, Object> map, String key, Object value) {
        if (key != null && value != null)
            map.put(key, value);
    }

    /* Getters and Setters start */
    public void setKafkaClient(KafkaClient kafkaClient) {
        this.kafkaClient = kafkaClient;
    }

    public KafkaClient getKafkaClient() {
        return this.kafkaClient;
    }
    /* Getters and Setters end */
}
