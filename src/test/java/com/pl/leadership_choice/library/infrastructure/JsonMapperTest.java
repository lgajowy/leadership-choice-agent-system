package com.pl.leadership_choice.library.infrastructure;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonMapperTest {

    private final String testJSONString = "{\n" +
            "    \"testField\": \"xxx\"\n" +
            "}";

    @Test
    public void shouldReturnJsonStringFromPojo() throws Exception {
        assertThat(JsonMapper.createJsonStringFromObject(new Candidacy("someId", "groupId", 20D, null))).isNotEmpty();
    }

    @Test
    public void shouldMapToObjectProperly() throws Exception {
        assertThat(JsonMapper.mapJsonStringToObject(testJSONString, TestPojo.class)).isInstanceOf(TestPojo.class);

    }

    @Test
    public void shouldReturnNullIfStringIsEmpty() throws Exception {
        assertThat(JsonMapper.mapJsonStringToObject(new String(), TestPojo.class)).isNull();

    }

    @Test
    public void shouldReturnNullIfNullStringGiven() throws Exception {
        assertThat(JsonMapper.mapJsonStringToObject(null, TestPojo.class)).isNull();

    }

    public static class TestPojo {
        private String testField;

        public String getTestField() {
            return testField;
        }

        public void setTestField(String testField) {
            this.testField = testField;
        }
    }
}