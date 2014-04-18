package com.givval.tobedeleted.version;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VersionBasedOnDateTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void major_is_2014_for_year_2014() {
        SplittedDate currentDate = mock(SplittedDate.class);
        when(currentDate.year()).thenReturn(2014);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        assertThat(currentVersion.major(), equalTo(2014));
    }

    @Test
    public void minor_is_1_for_month_january() {
        SplittedDate currentDate = mock(SplittedDate.class);
        when(currentDate.month()).thenReturn(0);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        assertThat(currentVersion.minor(), equalTo(1));
    }

    @Test
    public void minor_is_12_for_month_december() {
        SplittedDate currentDate = mock(SplittedDate.class);
        when(currentDate.month()).thenReturn(11);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        assertThat(currentVersion.minor(), equalTo(12));
    }

    @Test
    public void buildnumber_is_0_after_creation() {
        SplittedDate currentDate = mock(SplittedDate.class);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        assertThat(currentVersion.buildnumber(), equalTo(0));
    }

    @Test
    public void increase_the_buildnumber_creates_a_new_VersionObject() {
        SplittedDate currentDate = mock(SplittedDate.class);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        VersionBasedOnDate versionWithIncreasedBuildnumber = currentVersion.increaseBuildnumber();

        assertThat(versionWithIncreasedBuildnumber, not(sameInstance(currentVersion)));
    }

    @Test
    public void increase_the_buildnumber_from_0_to_1() {
        SplittedDate currentDate = mock(SplittedDate.class);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        VersionBasedOnDate versionWithIncreasedBuildnumber = currentVersion.increaseBuildnumber();

        assertThat(versionWithIncreasedBuildnumber.buildnumber(), equalTo(1));
    }

    @Test
    public void increasing_the_buildnumber_doesnt_effect_the_current_version() {
        SplittedDate currentDate = mock(SplittedDate.class);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        VersionBasedOnDate versionWithIncreasedBuildnumber = currentVersion.increaseBuildnumber();

        assertThat(currentVersion.buildnumber(), equalTo(0));
    }

    @Test
    public void for_version_in_year_2014_month_february_and_build_0_the_toString_is_YYYY_DOT_M_DOT_0() {
        SplittedDate currentDate = mock(SplittedDate.class);
        when(currentDate.year()).thenReturn(2014);
        when(currentDate.month()).thenReturn(1);

        VersionBasedOnDate currentVersion = new VersionBasedOnDate(currentDate);

        assertThat(currentVersion.toString(), equalTo("2014.2.0"));
    }

    @Test
    public void null_is_not_allowed_for_splittedDate() {
        exception.expect(IllegalArgumentException.class);

        new VersionBasedOnDate(null);
    }

    @Test
     public void negative_major_is_not_allowed() {
        exception.expect(IllegalArgumentException.class);

        new VersionBasedOnDate(-1, 1, 0);
    }

    @Test
    public void minor_less_than_1_is_not_allowed() {
        exception.expect(IllegalArgumentException.class);

        new VersionBasedOnDate(0, 0, 0);
    }

    @Test
    public void minor_greater_than_12_is_not_allowed() {
        exception.expect(IllegalArgumentException.class);

        new VersionBasedOnDate(0, 13, 0);
    }

    @Test
    public void negative_buildnumber_is_not_allowed() {
        exception.expect(IllegalArgumentException.class);

        new VersionBasedOnDate(0, 1, -1);
    }

    @Test
    public void equals_hashcode_works_according_to_the_contract() {
        EqualsVerifier.forClass(VersionBasedOnDate.class).allFieldsShouldBeUsed().verify();
    }
}
