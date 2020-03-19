package com.maac.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.maac.api.web.rest.TestUtil;

public class VisitaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visita.class);
        Visita visita1 = new Visita();
        visita1.setId("id1");
        Visita visita2 = new Visita();
        visita2.setId(visita1.getId());
        assertThat(visita1).isEqualTo(visita2);
        visita2.setId("id2");
        assertThat(visita1).isNotEqualTo(visita2);
        visita1.setId(null);
        assertThat(visita1).isNotEqualTo(visita2);
    }
}
