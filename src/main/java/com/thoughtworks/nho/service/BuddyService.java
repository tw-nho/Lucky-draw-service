package com.thoughtworks.nho.service;

import com.thoughtworks.nho.configuration.BuddyConfiguration;
import com.thoughtworks.nho.domain.Buddy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BuddyService {

    @Autowired
    private BuddyConfiguration buddyConfiguration;

    public Buddy drawLuckyBuddy() {
        int luckyIndex = new Random().nextInt(buddyConfiguration.getBuddies().size());

        return buddyConfiguration.getBuddies().get(luckyIndex);
    }

    public Buddy drawLuckyBuddyByRole(String role) {
        List<Buddy> buddies = buddyConfiguration.getBuddies().stream()
                .filter(buddy -> buddy.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());

        return buddies.get(new Random().nextInt(buddies.size()));
    }

    public Buddy drawLuckyBuddiesByRoles(String... roles) {
        List<Buddy> buddies = buddyConfiguration.getBuddies().stream()
                .filter(buddy -> Arrays.asList(roles).contains(buddy.getRole()))
                .collect(Collectors.toList());

        return buddies.get(new Random().nextInt(buddies.size()));
    }
}
