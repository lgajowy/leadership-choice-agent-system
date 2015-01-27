package com.pl.leadership_choice.library.infrastructure;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonMapperTest {

    private final String testPojoJSON = "{\n" +
            "    \"testField\": \"xxx\"\n" +
            "}";

    @Test
    public void shouldReturnJsonStringFromPojo() throws Exception {
        assertThat(JsonMapper.createJsonFromObject(new Candidacy("someId", "groupId", 20D, null))).isNotEmpty();
    }

    @Test
    public void shouldMapToObjectProperly() throws Exception {
        assertThat(JsonMapper.mapJsonStringToObject(testPojoJSON, TestPojo.class)).isInstanceOf(TestPojo.class);

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