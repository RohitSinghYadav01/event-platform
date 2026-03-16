package com.event.eventservice.service;

import com.event.eventservice.dto.EventDto;

import java.util.List;

public interface EventService {

    public List<EventDto> getAllEvents();

    public EventDto createEvent(EventDto eventDto);

    public EventDto updateEvent(EventDto eventDto);

    public void deleteEvent(EventDto eventDto);
}
