package com.thoughtworks.nho.service;

import com.thoughtworks.nho.configuration.BuddyConfiguration;
import com.thoughtworks.nho.domain.Buddy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class BuddyServiceTest {
    private Logger logger = Logger.getLogger(BuddyServiceTest.class.getName());
    private List<Buddy> buddies;

    @BeforeEach
    void setup(){
        buddies = initBuddies();
        when(buddyConfiguration.getBuddies()).thenReturn(buddies);
    }

    @InjectMocks
    private BuddyService buddyService = new BuddyService();

    @Mock
    private BuddyConfiguration buddyConfiguration;

    private List<Buddy> initBuddies() {
        return Arrays.asList(
                new Buddy("刘宏", "AC"),
                new Buddy("崔丁文", "DEV"),
                new Buddy("廖彬", "DEV"),
                new Buddy("季瀚思", "DEV"),
                new Buddy("韩斐", "DEV"),
                new Buddy("闫旭", "DEV"),
                new Buddy("卫坤", "DEV"),
                new Buddy("孟然", "DEV"),
                new Buddy("于晓南", "QA"),
                new Buddy("朱志国", "PM"),
                new Buddy("金中浩", "TA"),
                new Buddy("江泓儒", "XD")
        );
    }

    @Test
    void should_log_lucky_trainee() {
        Buddy luckyBuddy = buddyService.drawLuckyBuddy();
        logger.info("\n\nLucky Buddy is " + luckyBuddy.getName() + "[" + luckyBuddy.getRole() + "], Congratulations!");
    }

    @Test
    void should_log_lucky_trainee_by_multiple_roles() {
        Buddy luckyBuddy = buddyService.drawLuckyBuddiesByRoles("DEV");
        logger.info("\n\nLucky Buddy is " + luckyBuddy.getName() + "[" + luckyBuddy.getRole() + "], Congratulations!");
    }

    @Test
    void should_random_draw_trainee() {
        Buddy buddy = buddyService.drawLuckyBuddy();

        assertThat(buddies).contains(buddy);
    }

    @Test
    void should_random_draw_trainee_by_single_role() {
        Buddy buddy = buddyService.drawLuckyBuddyByRole("AC");

        assertThat(buddy.getRole()).isEqualTo("AC");
        assertThat(buddy.getName()).isEqualTo("刘宏");
    }

    @Test
    void should_random_draw_trainee_by_multiple_roles() {
        Buddy luckyBuddy = buddyService.drawLuckyBuddiesByRoles("AC", "PM");

        List<Buddy> nonLuckyBuddies = buddies.stream()
                .filter(buddy -> !Arrays.asList("AC", "PM").contains(buddy.getRole()))
                .collect(Collectors.toList());

        assertThat(nonLuckyBuddies).doesNotContain(luckyBuddy);
        assertThat(luckyBuddy.getRole()).isIn("AC", "PM");
        assertThat(luckyBuddy.getName()).isIn("刘宏", "朱志国");
    }
}