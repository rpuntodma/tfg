package ramon.del.moral.buscadormtg.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import ramon.del.moral.buscadormtg.dtos.CardDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardStringToCardDtoConverter implements Converter<String, List<CardDto>> {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * This is used to manage the pagination, and do multiple calls to the API
     */
    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<CardDto> convert(String source) {
        Assert.notNull(source, "Source must not be null");

        List<CardDto> cardList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(source);
            cardList.addAll(extractCardsFromNode(rootNode));

            JsonNode nextPageNode = rootNode.path("next_page");

            while (!nextPageNode.isMissingNode() && nextPageNode.asText() != null) {
                String nextPageUrl = nextPageNode.asText();
                String nextPageJson = restTemplate.getForObject(nextPageUrl, String.class);
                JsonNode nextPageRootNode = objectMapper.readTree(nextPageJson);
                cardList.addAll(extractCardsFromNode(nextPageRootNode));
                nextPageNode = nextPageRootNode.path("next_page");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to List<CardDto>", e);
        }

        return cardList;
    }

    private List<CardDto> extractCardsFromNode(JsonNode rootNode) {
        List<CardDto> cardList = new ArrayList<>();
        JsonNode dataNode = rootNode.path("data");

        if (dataNode.isArray()) {
            for (JsonNode node : dataNode) {
                CardDto card = CardDto.builder()
                                      .name(node.path("name")
                                                .asText())
                                      .types(node.path("type_line")
                                                 .asText())
                                      .manaCost(node.path("mana_cost")
                                                    .asText())
                                      .oracle(node.path("oracle_text")
                                                  .asText())
                                      .imageUrl(node.path("image_uris")
                                                    .path("normal")
                                                    .asText())
                                      .build();
                cardList.add(card);
            }
        }
        return cardList;
    }
}