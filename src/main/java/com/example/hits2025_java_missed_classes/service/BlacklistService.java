//package com.example.hits2025_java_missed_classes.service;
//
//
//import com.example.hits2025_java_missed_classes.model.TokenBlacklist;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class BlacklistService implements TokenBlacklist {
//    private Set<String> blacklist = new HashSet<>();
//
//    @Override
//    public void addToBlacklist(String token) {
//        blacklist.add(token);
//    }
//
//    @Override
//    public boolean isBlacklisted(String token) {
//        return blacklist.contains(token);
//    }
//}