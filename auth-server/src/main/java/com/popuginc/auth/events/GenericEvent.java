package com.popuginc.auth.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class GenericEvent<T>  {

  private final String eventType;

  private final T data;
}
