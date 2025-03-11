package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.model.MissRequestType;
import org.springframework.stereotype.Component;

@Component
public class MissRequestTypeMapper {
    public String toRuString(MissRequestType type) {
        return switch (type){
            case FAMILY -> "семейные_обстоятельства";
            case SICK -> "болел";
            case EVENT_TRIP -> "поездка_на_мероприятие";
        };
    }
}