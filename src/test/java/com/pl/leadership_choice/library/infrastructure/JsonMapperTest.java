package com.pl.leadership_choice.library.infrastructure;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonMapperTest {

    @Test
    public void shouldReturnJsonStringFromPojo() throws Exception {
        assertThat(JsonMapper.createJsonFromObject(new Candidacy("someId", "groupId", 20D, null))).isNotEmpty();
    }
}