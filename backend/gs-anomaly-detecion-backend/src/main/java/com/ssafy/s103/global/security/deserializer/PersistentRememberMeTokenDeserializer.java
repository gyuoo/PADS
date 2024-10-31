package com.ssafy.s103.global.security.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

@Slf4j
public class PersistentRememberMeTokenDeserializer extends
    JsonDeserializer<PersistentRememberMeToken> {

    @Override
    public PersistentRememberMeToken deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String username = node.get("username").asText();
        String series = node.get("series").asText();
        String tokenValue = node.get("tokenValue").asText();

        // date 값은 long
        JsonNode dateNode = node.get("date");
        long date = dateNode.asLong();

        return new PersistentRememberMeToken(username, series, tokenValue, new Date(date));
    }
}