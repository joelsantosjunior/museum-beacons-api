package com.maac.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.maac.api.web.rest.TestUtil;

public class VisitanteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visitante.class);
        Visitante visitante1 = new Visitante();
        visitante1.setId("id1");
        Visitante visitante2 = new Visitante();
        visitante2.setId(visitante1.getId());
        assertThat(visitante1).isEqualTo(visitante2);
        visitante2.setId("id2");
        assertThat(visitante1).isNotEqualTo(visitante2);
        visitante1.setId(null);
        assertThat(visitante1).isNotEqualTo(visitante2);
    }
}
