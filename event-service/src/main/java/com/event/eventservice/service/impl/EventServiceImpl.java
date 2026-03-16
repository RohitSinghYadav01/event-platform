package com.event.eventservice.service.impl;

import com.event.eventservice.client.UserClient;
import com.event.eventservice.dto.EventDto;
import com.event.eventservice.entity.Event;
import com.event.eventservice.exception.OrganizerNotFoundException;
import com.event.eventservice.repository.EventRepository;
import com.event.eventservice.service.EventService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserClient userClient;

    @Override
    public List<EventDto> getAllEvents() {

        return eventRepository.findAll().stream()
                .map(event->EventDto.builder()
                        .id(event.getId())
                        .title(event.getTitle())
                        .description(event.getDescription())
                        .location(event.getLocation())
                        .eventDate(event.getEventDate())
                        .capacity(event.getCapacity())
                        .organizerId(event.getOrganizerId())
                        .build()
                ).toList();
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")
    @Override
    public EventDto createEvent(EventDto eventDto) {
        try {
            userClient.getUserById(eventDto.getOrganizerId());
        }catch (Exception e){
            throw new OrganizerNotFoundException("Organizer not found");
        }

        Event event = Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .location(eventDto.getLocation())
                .eventDate(eventDto.getEventDate())
                .capacity(eventDto.getCapacity())
                .organizerId(eventDto.getOrganizerId())
                .build();

        Event savedEvent = eventRepository.save(event);

        return EventDto.builder()
                .id(savedEvent.getId())
                .title(savedEvent.getTitle())
                .description(savedEvent.getDescription())
                .location(savedEvent.getLocation())
                .eventDate(savedEvent.getEventDate())
                .capacity(savedEvent.getCapacity())
                .organizerId(savedEvent.getOrganizerId())
                .build();
    }

    @Override
    public EventDto updateEvent(EventDto eventDto) {
        Event event = Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .location(eventDto.getLocation())
                .eventDate(eventDto.getEventDate())
                .capacity(eventDto.getCapacity())
                .organizerId(eventDto.getOrganizerId())
                .build();

        Event savedEvent = eventRepository.save(event);

        return EventDto.builder()
                .id(savedEvent.getId())
                .title(savedEvent.getTitle())
                .description(savedEvent.getDescription())
                .location(savedEvent.getLocation())
                .eventDate(savedEvent.getEventDate())
                .capacity(savedEvent.getCapacity())
                .organizerId(savedEvent.getOrganizerId())
                .build();
    }

    @Override
    public void deleteEvent(EventDto eventDto) {
        Event event = Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .location(eventDto.getLocation())
                .eventDate(eventDto.getEventDate())
                .capacity(eventDto.getCapacity())
                .organizerId(eventDto.getOrganizerId())
                .build();

        eventRepository.delete(event);
    }


    public EventDto userServiceFallback(EventDto eventDto, Throwable throwable) {
        throw new RuntimeException("User Service unavailable, please try again later");
    }
}
