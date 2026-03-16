package com.event.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {

    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime eventDate;

    private Integer capacity;

    private Long organizerId;
}
