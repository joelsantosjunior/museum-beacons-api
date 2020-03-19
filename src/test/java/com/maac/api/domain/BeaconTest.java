package com.maac.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.maac.api.web.rest.TestUtil;

public class BeaconTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beacon.class);
        Beacon beacon1 = new Beacon();
        beacon1.setId("id1");
        Beacon beacon2 = new Beacon();
        beacon2.setId(beacon1.getId());
        assertThat(beacon1).isEqualTo(beacon2);
        beacon2.setId("id2");
        assertThat(beacon1).isNotEqualTo(beacon2);
        beacon1.setId(null);
        assertThat(beacon1).isNotEqualTo(beacon2);
    }
}
